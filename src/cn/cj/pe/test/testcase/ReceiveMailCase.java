package cn.cj.pe.test.testcase;

import android.util.Log;
import android.widget.MultiAutoCompleteTextView;
import cn.cj.pe.test.BasicTestCase;
import cn.cj.pe.test.utils.TestUtil;

public class ReceiveMailCase extends BasicTestCase {

	private final String TAG = "ReceiveMailCase";
	private AddressBookCase addressBookCase = new AddressBookCase();
	
	/**
	 * 收邮件-主动刷新邮件
	 * 测试步骤：
	 * 1、使用其他账号的邮箱向测试账号邮箱发送一封邮件
	 * 预期结果：
	 * 1、使用其他账号的邮箱向测试账号邮箱发送一封邮件
	 */
	public void testRefleshMail() {
		String other_account = addressBookCase.getOtherAccount();
		TestUtil.clickById("actionbar_expansion_view", 3);
		//点击收件箱
		solo.clickOnText("收件箱");
		solo.sleep(2000);
		//点击侧栏菜单按钮
		solo.clickOnView(solo.getView("hjl_headicon", 0));
		solo.sleep(3000);
		//点击邮箱绑定的另一个账户
		solo.clickOnText(other_account);
		solo.sleep(2000);
		//在收件箱页面点击写邮件按钮,用下标1，否则会点击侧栏菜单按钮
		solo.clickLongOnView(solo.getView("hjl_headicon", 1));
		solo.sleep(3000);
		
		//先点击抄送，再点击收件人，这样才能把MultiAutoCompleteTextView控件调出来
		solo.clickOnText("抄送/密送");
		solo.clickOnText("收件人");
		//获取收件人输入框
		MultiAutoCompleteTextView tomail = 
				solo.getView(MultiAutoCompleteTextView.class, 0);
		solo.enterText(tomail, LoginCase._account + "@139.com");
		String to_subject = "测试主动刷新邮件" +  System.currentTimeMillis();
		//输入邮件主题
		TestUtil.enterEditText("subject", to_subject);
		TestUtil.enterEditText("editTextField", "主动刷新邮件");
		solo.sleep(3000);
		//发送邮件
		solo.clickOnView(solo.getView("hjl_headicon", 1));
		//返回到收件箱页面
		solo.goBack();
		
		//点击侧栏菜单按钮
		solo.clickOnView(solo.getView("hjl_headicon", 0));
		//点击邮箱绑定的测试账户
		solo.clickOnText(LoginCase._account);
		//设置超时五分钟
		long timeout = System.currentTimeMillis() + 5*60*1000;
		boolean isSuccess = false;
		String temp;
		while(System.currentTimeMillis() < timeout) {
			//获取收件箱第一封邮件
			temp = TestUtil.getTextOfTextView("mail_subject");
			//Log.d(TAG, "temp = " + temp);
			//获取第一个邮件记录的主题，如果主题过长，则去除主题的'...'
			if(to_subject.equals(temp) || 
					to_subject.startsWith(temp.substring(0, temp.length()-3))) {
				Log.d(TAG, TestUtil.getTextOfTextView("mail_subject"));
				isSuccess = true;
				break;
			} 
			//solo.sleep(5000);
		}
		
		if(isSuccess) {
			Log.d(TAG, "测试账号的收件箱主动刷新出新邮件");
			assertEquals("测试通过", "测试通过");
			saveLog("(6)收邮件-主动刷新邮件 测试通过");
		} else {
			Log.d(TAG, "测试账号的收件箱没有主动刷新出新邮件，可能是接收邮件超时了");
			assertEquals("测试通过", "测试失败");
			saveLog("(6)收邮件-主动刷新邮件 测试不通过");
		}
	}
}
