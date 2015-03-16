package cn.cj.pe.test;

import java.io.File;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import cn.cj.pe.test.utils.NetworkUtil;
import com.robotium.solo.Solo;

import android.app.Activity;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.Settings.SettingNotFoundException;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

/**
 * 设计为抽象基类，后续的测试用例只要继承这个基类即可，免去重复的初始化工作
 * @author Administrator
 *
 */
abstract public class BasicTestCase extends ActivityInstrumentationTestCase2 {
	
	private final String TAG = "BasicTestCase";
	// 被测对象的包名
	private static final String TARGET_PACKAGE_ID = "cn.cj.pe";
	// 被测APK的入口类
	private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = 
			"com.mail139.about.LaunchActivity"; 
	// 声明一个Class变量，利用反射加载启动被测程序
	private static Class launcherActivityClass;
	
	private PowerManager.WakeLock wakeScreenObject = null;
	
	// 保存测试log的文件
	String FILE_NAME = "/Android_139PE.log";
	// 日志的日期格式，精确到毫秒
	public SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
	public static Solo solo;
	NetworkUtil netUtil;
	
	public Activity currentActivity;
	public String activityName;
	
	static {
		try {
			launcherActivityClass = Class
					.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public BasicTestCase() {
		super(TARGET_PACKAGE_ID, launcherActivityClass);
	}

	/**
	 * 复写setUp，初始化测试，进行异常捕获，截图处理
	 */
	protected void setUp() throws Exception {
		Log.i(TAG, "调用setUp()");
		try {
		//	super.setUp();
			init();
		} catch(Throwable e) {
			solo.takeScreenshot(this.getClass().getSimpleName());
			throw new SetUpException(e);
		}
	}

	protected void tearDown() throws Exception {
		Log.i(TAG, "调用tearDown()");
		// 一定要释放锁, 释放wakeLock，关灯
		if(wakeScreenObject != null) {
			Log.i(TAG, "释放wakeLock，关灯");
			wakeScreenObject.release();
			wakeScreenObject = null;
		}
		//Log.i(TAG, "锁屏");
		// 锁屏
		//WakeLockUtil.lock();
		
		try {
			solo.finishOpenedActivities();
//			super.tearDown();
		} catch(Throwable e) {
			solo.takeScreenshot(this.getClass().getSimpleName());
			throw new TearDownException(e);
		}
		
		Activity myActivity = getActivity();
		if(myActivity != null) {
			myActivity.finish();
		}
	}
	
	

	@Override
	protected void runTest() throws Throwable {
		Log.i(TAG, "调用runTest()");
		// TODO Auto-generated method stub
		try {
			super.runTest();
		} catch (Throwable th) {
			solo.takeScreenshot(this.getClass().getSimpleName());
			throw new RunTestException(th);
		}
	}

	/*
	 * runBare() 首先会运行 setup()，接着运行 runTest(), 最后 tearDown()。
	 * runBare() 会把所有的异常都抛出来
	 * 在setUp、runTest、tearDown抛出了自定义异常，这里统一进行捕获然后，确保tearDown方法被执行，用来释放资源
	 * (non-Javadoc)
	 * @see junit.framework.TestCase#runBare()
	 */
	
	@Override
	public void runBare() throws Throwable {
		Log.i(TAG, "调用runBare()");
		// TODO Auto-generated method stub
		try {
			super.runBare();
		} catch (SetUpException smte) {
			this.tearDown();
			throw smte;
		} catch (RunTestException rte) {
			this.tearDown();
			throw rte;
		} catch (TearDownException tde) {
			this.tearDown();
			throw tde;
		}
	} 

	private void init() throws SettingNotFoundException {
		Log.i(TAG, "调用init()");
		solo = new Solo(getInstrumentation(), getActivity());
		netUtil = new NetworkUtil(getInstrumentation().getTargetContext());
	}
	
	/**
	 * 自定义setUp时发生的异常
	 * @author Administrator
	 *
	 */
	class SetUpException extends Exception {
	//	private static final long serialVersionUID = 1L;

		SetUpException(Throwable e) {
			super("Error in BasicTestCase.setUp()!", e);
		}
	}
	
	/**
	 * 自定义tearDown时发生的异常
	 * @author Administrator
	 *
	 */
	class TearDownException extends Exception {

		TearDownException(Throwable e) {
			super("Error in BasicTestCase.tearDown()", e);
		}
	}
	
	/**
	 * 自定义runTest()时发生的异常
	 * @author Administrator
	 *
	 */
	class RunTestException extends Exception {

		RunTestException(Throwable e) {
			super("Error in BasicTestCase.runTest()", e);
		}
	}
	
	/*
	 * 写log的方法
	 */
	public void saveLog(String content) {
		try {
			// 换行
			content = content + "\r" + "\n";
			File targetFile;
			// 如果手机插入了SD卡，而且具有SD卡访问权限
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				// 获取SD卡的目录
				File sdCardDir = Environment.getExternalStorageDirectory();
				targetFile = new File(sdCardDir.getCanonicalPath() + FILE_NAME);
			} else {
				// 否则就存储在/data目录下面
				targetFile = new File(Environment.getDataDirectory()
						+ FILE_NAME);
			}
			// 以指定文件创建读写模式的RandomAccessFile对象
			RandomAccessFile raf = new RandomAccessFile(targetFile, "rw");
			// 将文件记录指针移到最后
			raf.seek(targetFile.length());
			/*
			 * 输出文件内容 writeBytes(String s)方法将中文拆成两个字节写进文本，所以会造成乱码， write(byte[]
			 * b)方法是将每两个字节合成一个中文并写时文本，所以不会出现乱码。 我们还可以调用writeUTF(String
			 * str)方法避免乱码。
			 */
			raf.write((formatter.format(new Date()) + " ").getBytes());
			raf.write(content.getBytes());

			raf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	public void test1() {
		AppDataCleanManager.cleanApplicationData(
				getInstrumentation().getTargetContext());
		solo.sleep(1000);
	}
	*/
	
}
