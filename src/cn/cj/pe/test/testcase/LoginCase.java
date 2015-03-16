package cn.cj.pe.test.testcase;

import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import cn.cj.pe.test.BasicTestCase;
import cn.cj.pe.test.utils.AppDataCleanManager;
import cn.cj.pe.test.utils.TestUtil;

public class LoginCase extends BasicTestCase {
	
	private final String TAG = "LoginCase";
	static String _account = "15989228271";
	static String _password = "yetong8271";
	//收件人
	private String to_accound = "yetong@chinasofti.com";
	//发送邮件主题
	private String to_subject = "";
	//发送邮件内容
	private String to_content = "这是一封测试邮件";
	//抄送人
	private String cc_accound = _account + "@139.com";
	
	/**
	 * 测试登录
	 * 测试步骤：
	 * 1、输入手机号码、密码
	 * 2、点击登录
	 * 预期结果：
	 * 提示登录成功，进入收件箱页面
	 */
	public void test1Login() {
		saveLog("开始执行testLogin");
		//清除客户端数据
		AppDataCleanManager.cleanApplicationData(
				getInstrumentation().getTargetContext());
		solo.sleep(1000);
		Log.d(TAG, solo.getCurrentActivity().getClass().getName());
		if(! solo.getCurrentActivity().getClass().getName()
				.equals("com.mail139.account.activity.AccountLoginActivity")) {
			for(int i = 0; i < 4; i++) {
				solo.scrollToSide(solo.RIGHT, (float) 0.7);
				solo.sleep(1000);
			}
		}
		
		Log.d(TAG, "滑动之后" + solo.getCurrentActivity().getClass().getName());
		
		//输入账号、密码
		TestUtil.enterEditText("login_name", _account);
		TestUtil.enterEditText("login_password", _password);
		
		TestUtil.clickById("login", 3);
		
		Long loadTime = System.currentTimeMillis();
		
		ImageView loadView = (ImageView) solo.getView("main_view");
		
		while(true) {
			if(! loadView.isShown()) {
				Log.d(TAG, "加载图标不见了");
				break;
			}
		} 
		ListView mailList = (ListView) solo.getView("list");
		while(true) {
			if(mailList.getChildCount() > 3) {
				break;
			}
		}
		loadTime = System.currentTimeMillis() - loadTime;
		Log.d(TAG, "列表加载时间为：" + loadTime +"毫秒");
		//saveLog("首次登录客户端，加载邮件列表耗时："  + loadTime +"毫秒");
		/*
		solo.sleep(10000);
		Log.d(TAG, "点击登录之后，进入" + solo.getCurrentActivity()
				.getClass().getName());
		Log.d(TAG, "mailList.getChildCount:：" + mailList.getChildCount()); 
		*/
		solo.sleep(10000);
		if("收件箱".equals(TestUtil.getTextOfTextView(
				"actionbar_sub_title").substring(0, 3))) {
			TestUtil.takescreen("139邮箱拨测-登录成功");
			assertEquals("登录成功", "登录成功");
			saveLog("(1)139邮箱拨测-登录用例 测试通过");
		} else {
			TestUtil.takescreen("139邮箱拨测-登录失败");
			saveLog("(1)139邮箱拨测-登录用例 测试不通过");
			assertEquals("登录成功", "登录失败");
		}
		solo.sleep(2000);
	}
	
	/**
	 * 测试直接发送邮件
	 * 测试步骤：
	 * 1、点击【写邮件】按钮
	 * 2、输入收件人账号、主题
	 * 3、填写邮件内容
	 * 4、点击【发送按钮】
	 * 预期结果：
	 * 1、邮件发送成功
	 * 2、发件箱存在刚才所发送成功的邮件
	 */
	public void test2SendMail() {
		saveLog("开始执行testSendMail");
		solo.sleep(5000);
		//在收件箱页面点击写邮件按钮,用下标1，否则会点击侧栏菜单按钮
		ImageView editMail = (ImageView) solo.getView("hjl_headicon", 1);
		solo.clickLongOnView(editMail);
		solo.sleep(3000);
		//先点击抄送，再点击收件人，这样才能把MultiAutoCompleteTextView控件调出来
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
		solo.sleep(10000);
		//发送邮件
		ImageView sendMail = (ImageView) solo.getView("hjl_headicon", 1);
		solo.clickOnView(sendMail);
		solo.sleep(10000);
		
		solo.goBack();
		TestUtil.clickById("actionbar_expansion_view", 3);
		solo.clickOnText("已发送");
		solo.sleep(10000);
		//获取第一个邮件记录的主题
		if(to_subject.equals(TestUtil.getTextOfTextView("mail_subject"))) {
			Log.d(TAG, TestUtil.getTextOfTextView("mail_subject"));
			Log.d(TAG, "发送成功");
			TestUtil.takescreen("发送成功");
			assertEquals("发送成功", "发送成功");
			saveLog("(2)139邮箱拨测-直接发送邮件用例 测试通过");
		} else {
			Log.d(TAG, "发送失败");
			TestUtil.takescreen("发送失败");
			saveLog("(2)139邮箱拨测-直接发送邮件用例 测试不通过");
			assertEquals("发送成功", "发送不成功");
		}
		solo.sleep(2000);
	}
	
	
	
	/**
	 * 转发邮件
	 * 测试步骤：
	 * 1、在收件箱中点击一封邮件
	 * 2、点击【转发】按钮
	 * 3、输入收件人账号
	 * 4、填写邮件内容
	 * 5、点击【发送按钮】
	 * 预期结果：
	 * 1、邮件主题为:Fwd:+原邮件主题
	 * 2、邮件发送成功
	 * 3、发件箱存在刚才所转发成功的邮件
	 */
	public void test3SendMail_FSW() {
		saveLog("开始执行testSendMail_FSW");
		//点击下拉列表
		TestUtil.clickById("actionbar_expansion_view", 3);
		//点击收件箱
		solo.clickOnText("收件箱");
		solo.sleep(2000);
		//点击第一封邮件主题
		TestUtil.clickById("mail_subject", 3);
		solo.sleep(3000);
		TextView title = (TextView) solo.getView("title");
		if(title == null) {
			solo.sleep(30000);
			title = (TextView) solo.getView("title");
		}
		//获取原邮件的主题
		//String origin_subject = TestUtil.getTextOfTextView("title");
		String origin_subject = title.getText().toString();
		Log.d(TAG, "原始邮件的主题：" + origin_subject);
		//首次进入邮件，页面上会遮挡提示，必须先点击掉
		solo.clickOnText(origin_subject);
		solo.sleep(3000);
		//点击转发按钮
		//TestUtil.clickById("actionbar_forward, 3);
		solo.clickOnView(solo.getView("actionbar_forward"));
		solo.sleep(2000);
		solo.hideSoftKeyboard();
		solo.sleep(5000);
		solo.clickOnText("抄送/密送");
		solo.clickOnText("收件人");
		//获取收件人输入框
		MultiAutoCompleteTextView tomail = 
				solo.getView(MultiAutoCompleteTextView.class, 0);
		solo.enterText(tomail, to_accound);
		solo.sleep(2000);
		//获取转发邮件的主题
		String fwd_subject = TestUtil.getTextOfTextView("subject");
		Log.d(TAG, "转发邮件的主题：" + fwd_subject);
		if(fwd_subject.equals("Fwd: " + origin_subject)) {
			assertEquals("转发邮件主题为:Fwd:+原邮件主题", "转发邮件主题为:Fwd:+原邮件主题");
		} else {
			TestUtil.takescreen("Fwd邮件主题不对");
			saveLog("(3)139邮箱拨测-转发邮件用例 测试不通过");
			assertEquals("转发邮件主题为:Fwd:+原邮件主题", "转发邮件主题不是:Fwd:+原邮件主题");
		}
		//发送邮件
		ImageView sendMail = (ImageView) solo.getView("hjl_headicon", 1);
		solo.clickOnView(sendMail);
		solo.sleep(1000);
		
		solo.goBack();
		TestUtil.clickById("actionbar_expansion_view", 3);
		solo.clickOnText("已发送");
		//solo.sleep(10000);
		Log.d(TAG, "已发送：" + TestUtil.getTextOfTextView("mail_subject"));
		
		//设置超时五分钟
		long timeout = System.currentTimeMillis() + 5*60*1000;
		boolean isSuccess = false;
		String temp;
		while(System.currentTimeMillis() < timeout) {
			temp = TestUtil.getTextOfTextView("mail_subject");
//			Log.d(TAG, "temp = " + temp);
			if(fwd_subject.equals(temp)
					|| (temp.length() > 3 
							&&fwd_subject.startsWith(temp.substring(0, temp.length()-3)))) {
				Log.d(TAG, TestUtil.getTextOfTextView("mail_subject"));
				isSuccess = true;
				break;
			}
		}
		if(isSuccess) {
			Log.d(TAG, "发送成功");
			TestUtil.takescreen("转发邮件成功");
			assertEquals("发送成功", "发送成功");
			saveLog("(3)139邮箱拨测-转发邮件用例 测试通过");
		} else {
			Log.d(TAG, "发送失败");
			TestUtil.takescreen("转发邮件成功失败");
			saveLog("(3)139邮箱拨测-转发邮件用例 测试不通过");
			assertEquals("发送成功", "发送不成功");
		}
		solo.sleep(2000);
	}
	
	/**
	 * 添加附件发送邮件
	 * 测试步骤：
	 * 1、点击【写邮件】按钮
	 * 2、输入收件人账号、主题
	 * 3、点击【添加附件】按钮，分别添加照片、本地文件
	 * 4、填写邮件内容，点击【发送按钮】
	 * 预期结果：
	 * 1、邮件发送成功
	 * 2、发件箱存在刚才所发送成功的邮件
	 */
	public void test4SendMail_WithAnnex() {
		saveLog("开始执行testSendMail_WithAnnex");
		//点击下拉列表
		TestUtil.clickById("actionbar_expansion_view", 3);
		//点击收件箱
		solo.clickOnText("收件箱");
		solo.sleep(2000);
		//在收件箱页面点击写邮件按钮,用下标1，否则会点击侧栏菜单按钮
		ImageView editMail = (ImageView) solo.getView("hjl_headicon", 1);
		solo.clickLongOnView(editMail);
		solo.sleep(3000);
		//先点击抄送，再点击收件人，这样才能把MultiAutoCompleteTextView控件调出来
		solo.clickOnText("抄送/密送");
		solo.clickOnText("收件人");
		//获取收件人输入框
		MultiAutoCompleteTextView tomail = 
				solo.getView(MultiAutoCompleteTextView.class, 0);
		solo.enterText(tomail, to_accound);
		//获取抄送人
		MultiAutoCompleteTextView ccmail = 
				solo.getView(MultiAutoCompleteTextView.class, 1);
		solo.enterText(ccmail, cc_accound);
		
		//点击添加附件按钮
		TestUtil.clickById("add_attachment", 3);
		solo.sleep(2000);
		//点击添加图片按钮
		TestUtil.clickById("attach_display_photo", 3);
		solo.sleep(2000);
		//点击第一个图片文件夹
		TestUtil.clickById("pic_picture", 3);
		solo.sleep(2000);
		//点击第一张图片
		TestUtil.clickById("pic_picture", 3);
		solo.sleep(2000);
		//点击添加到邮件按钮
		TestUtil.clickById("checkButton", 3);
		solo.sleep(2000);
		
		to_subject = "自动化拨测添加附件发送邮件" + System.currentTimeMillis();
		//输入邮件主题
		TestUtil.enterEditText("subject", to_subject);
		
		//输入邮件内容
		to_content = "这是一封带附件的测试邮件";
		TestUtil.enterEditText("editTextField", to_content);
		
		solo.sleep(10000);
		//发送邮件
		ImageView sendMail = (ImageView) solo.getView("hjl_headicon", 1);
		solo.clickOnView(sendMail);
		//solo.sleep(10000);
		
		solo.goBack();
		//TestUtil.clickById("actionbar_expansion_view", 3);
		solo.clickOnView(solo.getView("actionbar_expansion_view"));
		solo.sleep(3000);
		solo.clickOnText("已发送");
		//设置超时五分钟
		long timeout = System.currentTimeMillis() + 5*60*1000;
		boolean isSuccess = false;
		String temp;
		while(System.currentTimeMillis() < timeout) {
			temp = TestUtil.getTextOfTextView("mail_subject");
			Log.d(TAG, "temp = " + temp);
			//获取第一个邮件记录的主题，如果主题过长，则去除主题的'...'
			if(to_subject.equals(temp) || (temp.length() > 3 &&
					to_subject.startsWith(temp.substring(0, temp.length()-3)))) {
				Log.d(TAG, TestUtil.getTextOfTextView("mail_subject"));
				isSuccess = true;
				break;
			} 
			solo.sleep(15000);
		}
		if(isSuccess) {
			Log.d(TAG, "发送成功");
			TestUtil.takescreen("发送成功");
			assertEquals("发送成功", "发送成功");
			saveLog("(4)139邮箱拨测-添加附件发送邮件用例 测试通过");
		} else {
			Log.d(TAG, "发送失败");
			TestUtil.takescreen("发送失败");
			saveLog("(4)139邮箱拨测-添加附件发送邮件用例 测试不通过");
			assertEquals("发送成功", "发送不成功");
		}
		solo.sleep(2000);
	}
	
}
