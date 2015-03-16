package cn.cj.pe.test.testcase;

import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;
import cn.cj.pe.test.BasicTestCase;
import cn.cj.pe.test.utils.TestUtil;

/**
 * 彩云网盘测试用例
 * @author yetong
 *
 */
public class CaiyunCase extends BasicTestCase {

	private final String TAG = "CaiyunCase";
	private String fileName = "1080";
	private String to_accound = "18664759987@139.com";
	private String to_subject = "发送彩云附件";
	private String to_content = "这是从彩云附件发送的邮件";
	
	/**
	 * 彩云网盘测试
	 */
	public void testCaiyun() {
		saveLog("开始执行testCaiyun");
		goToCaiyun();

		if(solo.searchText("正在加载", true)) {
			Log.d(TAG, "彩云正在加载");
			solo.sleep(60000);
		}
		//获取"暂无文件"的背景控件
		TextView bg = (TextView) solo.getView("list_backgound_text", 1);
		Log.d(TAG, "bg.getText: " + bg.getText().toString());
		Log.d(TAG, "bg.isShown: " + bg.isShown());
		
		if(bg.isShown()) {
			Log.d(TAG, "彩云网盘页面显示暂无文件，测试失败");
			saveLog("(20)彩云 用例测试 不通过");
			assertEquals("彩云网盘加载成功", "彩云网盘页面显示暂无文件");
			return;
		}
		if(!solo.searchText(fileName)) {
			Log.d(TAG, "彩云网盘页面没找到该文件");
			saveLog("(20)彩云 用例测试 不通过");
			assertEquals("彩云网盘加载成功", "彩云网盘页面显示暂无文件");
			return;
		} 
		TestUtil.clickById("cloud_arrow", 3);
		if(TestUtil.getTextOfTextView("cloud_down_or_open_text").equals("下载")) {
			Log.d(TAG, "找到下载按钮");
			TestUtil.clickById("cloud_down_or_open_text", 3);
			//下载进度条布局
			LinearLayout layout = (LinearLayout) solo.getView("cloud_bar_ll");
			//是否已经下载
			boolean isDownload = false;
			long timeOut = System.currentTimeMillis() + 120000;
			while(System.currentTimeMillis() < timeOut) {
				if(!layout.isShown()) {
					Log.d(TAG, "下载完毕");
					isDownload = true;
					break;
				}
			}
			if(isDownload) {
				Log.d(TAG, "彩云文件下载成功");
				assertTrue(TestUtil.getTextOfTextView("cloud_down_or_open_text")
						.equals("打开"));
				Log.d(TAG, "下载后可打开");
				saveLog("彩云文件下载成功, 下载后可打开");
			} else {
				Log.d(TAG, "彩云文件下载超时");
				saveLog("彩云文件下载超时");
				assertEquals("下载成功", "下载超时");
			}
		} 
		//点击发送
		solo.clickOnText("发送");
		solo.sleep(2000);
		TextView attachment_count = (TextView) solo.getView("attachment_count");
		Log.d(TAG, "附件数量：" + attachment_count.getText().toString());
	//	assertTrue(attachment_count.getText().toString().equals("1"));
	//	Log.d(TAG, "附件数量正确");
		solo.clickOnText("抄送/密送");
		solo.clickOnText("收件人");
		//获取收件人输入框
		MultiAutoCompleteTextView tomail = 
				solo.getView(MultiAutoCompleteTextView.class, 0);
		solo.enterText(tomail, to_accound);
		to_subject = "自动化拨测邮件" + System.currentTimeMillis();
		//输入邮件主题
		TestUtil.enterEditText("subject", to_subject);
		
		TestUtil.enterEditText("editTextField", to_content);
		solo.sleep(5000);
		//发送邮件
		ImageView sendMail = (ImageView) solo.getView("hjl_headicon", 1);
		solo.clickLongOnView(sendMail);
		solo.sleep(10000);
		assertEquals("彩云网盘",
				TestUtil.getTextOfTextView("hjl_icon_local_contact_title_name"));
		Log.d(TAG, "文件可做附件发送");
		saveLog("(20)彩云 用例测试 通过");
	}
	
	/**
	 * 进入彩云网盘页面
	 */
	private void goToCaiyun() {
		solo.sleep(3000);
		//点击侧栏菜单按钮
		ImageView sideBarMenu = (ImageView) solo.getView("hjl_headicon", 0);
		solo.clickOnView(sideBarMenu);
		solo.clickOnText("彩云");
		solo.sleep(2000);
	}
}
