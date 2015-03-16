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
 * ���Ϊ������࣬�����Ĳ�������ֻҪ�̳�������༴�ɣ���ȥ�ظ��ĳ�ʼ������
 * @author Administrator
 *
 */
abstract public class BasicTestCase extends ActivityInstrumentationTestCase2 {
	
	private final String TAG = "BasicTestCase";
	// �������İ���
	private static final String TARGET_PACKAGE_ID = "cn.cj.pe";
	// ����APK�������
	private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = 
			"com.mail139.about.LaunchActivity"; 
	// ����һ��Class���������÷�����������������
	private static Class launcherActivityClass;
	
	private PowerManager.WakeLock wakeScreenObject = null;
	
	// �������log���ļ�
	String FILE_NAME = "/Android_139PE.log";
	// ��־�����ڸ�ʽ����ȷ������
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
	 * ��дsetUp����ʼ�����ԣ������쳣���񣬽�ͼ����
	 */
	protected void setUp() throws Exception {
		Log.i(TAG, "����setUp()");
		try {
		//	super.setUp();
			init();
		} catch(Throwable e) {
			solo.takeScreenshot(this.getClass().getSimpleName());
			throw new SetUpException(e);
		}
	}

	protected void tearDown() throws Exception {
		Log.i(TAG, "����tearDown()");
		// һ��Ҫ�ͷ���, �ͷ�wakeLock���ص�
		if(wakeScreenObject != null) {
			Log.i(TAG, "�ͷ�wakeLock���ص�");
			wakeScreenObject.release();
			wakeScreenObject = null;
		}
		//Log.i(TAG, "����");
		// ����
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
		Log.i(TAG, "����runTest()");
		// TODO Auto-generated method stub
		try {
			super.runTest();
		} catch (Throwable th) {
			solo.takeScreenshot(this.getClass().getSimpleName());
			throw new RunTestException(th);
		}
	}

	/*
	 * runBare() ���Ȼ����� setup()���������� runTest(), ��� tearDown()��
	 * runBare() ������е��쳣���׳���
	 * ��setUp��runTest��tearDown�׳����Զ����쳣������ͳһ���в���Ȼ��ȷ��tearDown������ִ�У������ͷ���Դ
	 * (non-Javadoc)
	 * @see junit.framework.TestCase#runBare()
	 */
	
	@Override
	public void runBare() throws Throwable {
		Log.i(TAG, "����runBare()");
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
		Log.i(TAG, "����init()");
		solo = new Solo(getInstrumentation(), getActivity());
		netUtil = new NetworkUtil(getInstrumentation().getTargetContext());
	}
	
	/**
	 * �Զ���setUpʱ�������쳣
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
	 * �Զ���tearDownʱ�������쳣
	 * @author Administrator
	 *
	 */
	class TearDownException extends Exception {

		TearDownException(Throwable e) {
			super("Error in BasicTestCase.tearDown()", e);
		}
	}
	
	/**
	 * �Զ���runTest()ʱ�������쳣
	 * @author Administrator
	 *
	 */
	class RunTestException extends Exception {

		RunTestException(Throwable e) {
			super("Error in BasicTestCase.runTest()", e);
		}
	}
	
	/*
	 * дlog�ķ���
	 */
	public void saveLog(String content) {
		try {
			// ����
			content = content + "\r" + "\n";
			File targetFile;
			// ����ֻ�������SD�������Ҿ���SD������Ȩ��
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				// ��ȡSD����Ŀ¼
				File sdCardDir = Environment.getExternalStorageDirectory();
				targetFile = new File(sdCardDir.getCanonicalPath() + FILE_NAME);
			} else {
				// ����ʹ洢��/dataĿ¼����
				targetFile = new File(Environment.getDataDirectory()
						+ FILE_NAME);
			}
			// ��ָ���ļ�������дģʽ��RandomAccessFile����
			RandomAccessFile raf = new RandomAccessFile(targetFile, "rw");
			// ���ļ���¼ָ���Ƶ����
			raf.seek(targetFile.length());
			/*
			 * ����ļ����� writeBytes(String s)���������Ĳ�������ֽ�д���ı������Ի�������룬 write(byte[]
			 * b)�����ǽ�ÿ�����ֽںϳ�һ�����Ĳ�дʱ�ı������Բ���������롣 ���ǻ����Ե���writeUTF(String
			 * str)�����������롣
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
