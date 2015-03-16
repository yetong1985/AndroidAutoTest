package cn.cj.pe.test.testcase;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import cn.cj.pe.test.BasicTestCase;
import cn.cj.pe.test.utils.TestUtil;

/**
 * 测试设置和换肤
 * @author Administrator
 *
 */
public class SettingCase extends BasicTestCase {
	
	private final String TAG = "SettingCase";
	private String oldName = "叶大师";
	private String newName = "叶主任";
	private String version = "V6.0.1";
	//保存窗口大小
	private DisplayMetrics dm;
	private AddressBookCase addressBookCase = new AddressBookCase();
	//要发送邮件的地址
	private String to_accound = "15989228271@139.com";
	//发送邮件的源账户
	private String fromAccount = null;
	
	//private Uri SMS_INBOX = Uri.parse("content://sms/");
	//查询收信箱
	private Uri SMS_INBOX = Uri.parse("content://sms/inbox");
	
	/**
	 * 设置-账号设置
	 * 测试步骤：
	 * 1、点击【设置】按钮
	 * 2、点击邮箱账号进行设置
	 * 3、点击发件人姓名，输入新的姓名
	 * 4、点击【保存】按钮
	 * 预期结果：
	 * 1、修改成功
	 * 2、发件人姓名为新修改的名称
	 */
	public void test1AccountSetting() {
		saveLog("开始执行test1AccountSetting");
		goToSetting();
		int flag = 0;
		//点击第一个账号
		TestUtil.clickById("account_name", 3);
		solo.sleep(2000);
		solo.clickOnText("发件人姓名");
		solo.sleep(1000);
		String tmpName = TestUtil.getTextOfTextView("import_text");
		if(tmpName.equals(oldName)) {
			flag = 1;
			TestUtil.enterEditText("import_text", newName);
		} else {
			flag = 2;
			TestUtil.enterEditText("import_text", oldName);
		}
		solo.sleep(1000);
		//点击保存按钮
		solo.clickOnView(solo.getView("hjl_headicon", 1));
		assertTrue(solo.searchText("修改成功", true));
		Log.d(TAG, "发件人修改成功");
		saveLog("发件人名称修改成功");
		solo.sleep(2000);
		String _name = TestUtil.getTextOfTextView("rightTextView", 6);
		Log.d(TAG, "_name = " + _name);
		if(1 == flag) {
			assertTrue(_name.equals(newName));
		} else if(2 == flag) {
			assertTrue(_name.equals(oldName));
		}
		saveLog("发件人姓名为新修改的名称");
		saveLog("(25)设置-账号设置 测试通过");
	}
	
	/**
	 * 设置-邮件设置
	 * 预置条件：
	 * 【邮件到达短信通知】按钮默认为关闭状态
	 * 测试步骤：
	 * 1、在邮件设置中点击【提醒设置】按钮
	 * 2、打开【邮件到达短信通知】按钮
	 * 3、点击返回按钮
	 * 预期结果：
	 * 新邮件到达后手机收到短信通知
	 */
	public void test2MailSetting() {
		saveLog("开始执行test2MailSetting");
		goToSetting();
		solo.clickOnText("提醒设置");
		solo.sleep(2000);
		//点击打开【邮件到达短信通知】
		TestUtil.clickById("is_notify_sms", 2);
		solo.sleep(5000);
		solo.goBack();
		solo.goBack();
		//点击侧栏菜单按钮
		solo.clickOnView(solo.getView("hjl_headicon", 0));
		solo.sleep(3000);
		//获取绑定的第二个账户
		String otherAccount = addressBookCase.getOtherAccount();
		Log.d(TAG, "otherAccount = " + otherAccount);

		//点击侧栏菜单按钮
		//solo.clickOnView( solo.getView("hjl_headicon", 0));
		if(solo.searchText(otherAccount)) {
			solo.clickOnText(otherAccount);
			Log.d(TAG, "点击" + otherAccount);
			fromAccount = otherAccount;
		} else {
			solo.clickOnText(to_accound);
			Log.d(TAG, "点击" + to_accound);
			fromAccount = to_accound;
		}
		
		//截取源账户的手机号码，用于验证短信
		String fromNum = fromAccount.substring(0, fromAccount.indexOf("@"));
		Log.d(TAG, "fromNum = " + fromNum);
		
		solo.sleep(3000);
		String to_subject = "测试邮件到达短信通知" +  System.currentTimeMillis();
		String to_content = "如果打开【邮件到达短信通知】按钮，邮件到达会发送短信通知";
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
		//输入邮件主题
		TestUtil.enterEditText("subject", to_subject);
		TestUtil.enterEditText("editTextField", to_content);
		solo.sleep(3000);
		//发送邮件的时间
		long sendTime = System.currentTimeMillis();
		//发送邮件
		ImageView sendMail = (ImageView) solo.getView("hjl_headicon", 1);
		solo.clickOnView(sendMail);
		
		solo.goBack();
		TestUtil.clickById("actionbar_expansion_view", 3);
		solo.clickOnText("已发送");
		//设置超时三分钟
		long timeout = System.currentTimeMillis() + 3*60*1000;
		boolean isSuccess = false;
		String temp;
		while(System.currentTimeMillis() < timeout) {
			temp = TestUtil.getTextOfTextView("mail_subject");
			Log.d(TAG, "temp = " + temp);
			//获取第一个邮件记录的主题，如果主题过长，则去除主题的'...'
			if(to_subject.equals(temp) || 
					to_subject.startsWith(temp.substring(0, temp.length()-3))) {
				Log.d(TAG, TestUtil.getTextOfTextView("mail_subject"));
				isSuccess = true;
				break;
			} 
			solo.sleep(5000);
		}
		if(isSuccess == false) {
			Log.d(TAG, "邮件发送失败，退出该测试用例");
			assertEquals("测试通过", "测试失败");
			saveLog("(26)设置-邮件设置 测试不通过");
			return;
		} 
		
		boolean flag = false;
		timeout = System.currentTimeMillis() + 3*60*1000;
		while(System.currentTimeMillis() < timeout) {
			Cursor cursor = getSmsFromPhone(sendTime);
			if(cursor == null) {
				Log.d(TAG, "还没有收到邮件到达的短信");
				continue;
			} 
			//向下移动一下光标  
			while(cursor.moveToNext()) {
				
				Log.d(TAG, "address所在列: " + cursor.getColumnIndex("address"));
				//获取发件人地址
				String number = cursor.getString(cursor.getColumnIndex("address"));
				Log.d(TAG, "body所在列: " + cursor.getColumnIndex("body"));
				//获取短信内容
				String body = cursor.getString(cursor.getColumnIndex("body"));
				Log.d(TAG, "发信人地址：" + number);
				Log.d(TAG, "短信内容：" + body);
				/*
				//匹配邮件短信开始的【】里面的手机号码，也就是发信人手机号
				Pattern pattern = Pattern.compile("\\[([0-9]+)\\]");
				Matcher matcher = pattern.matcher(body);
				//提取短信中的发信人手机号码
				String verify_num = matcher.group(1);
				Log.d(TAG, "verify_num = " + verify_num);				
				
				//匹配邮件主题
				pattern = Pattern.compile("\\](.+)");
				matcher = pattern.matcher(body);
				//提取短信中的邮件主题
				String verify_subject = matcher.group(1);
				Log.d(TAG, "verify_subject = " + verify_subject);
				*/
				
				if(body.startsWith("[" + fromNum + "]主题：" + to_subject)) {
					Log.d(TAG, "收到邮件到达的短信了");
				//	assertTrue("短信中的主题不符", body.indexOf(to_subject) > 0);
					assertEquals("测试通过", "测试通过");
					Log.d(TAG, "(26)设置-邮件设置 测试通过");
					saveLog("(26)设置-邮件设置 测试通过");
					flag = true;
					break;
				}
			}

			if(flag) {
				//关闭游标
				cursor.close();
				break;
			}
			solo.sleep(10000);
		}
		
		goToSetting();
		solo.clickOnText("提醒设置");
		solo.sleep(2000);
		//点击关闭【邮件到达短信通知】
		TestUtil.clickById("is_notify_sms", 2);
		solo.sleep(3000);
		
		if(!flag) {
			assertEquals("测试通过", "测试失败");
			saveLog("(26)设置-邮件设置 测试不通过");
		}
		
	}
	
	/**
	 * 设置-功能设置
	 * 预置条件：已开启允许读取手机联系人开关
	 * 测试步骤：
	 * 1、在功能设置中关闭【允许读取手机联系人】
	 * 2、进入通讯录页面，点击【全部】标签，下拉刷新
	 * 预期结果：
	 * 1、关闭成功，提示【在通讯录中仅刷新同步139邮箱联系人】
	 * 2、提示：139邮箱通讯录同步完成
	 */
	public void test3FunctionSetting() {
		saveLog("开始执行test3FunctionSetting");
		goToSetting();
		//要添加账户才能识别这个操作
		solo.clickOnText("允许读取手机联系人");
		if(solo.searchText("在通讯录中仅刷新同步139邮箱联系人")) {
			Log.d(TAG, "关闭成功，提示【在通讯录中仅刷新同步139邮箱联系人】");
			Log.d(TAG, "关闭成功，提示【在通讯录中仅刷新同步139邮箱联系人】");
			saveLog("关闭成功，提示【在通讯录中仅刷新同步139邮箱联系人】");
			solo.sleep(2000);
		} else {
			Log.d(TAG, "【允许读取手机联系人】开关已经开启，现在关闭它");
			saveLog("在功能设置中关闭【允许读取手机联系人】");
			solo.clickOnText("允许读取手机联系人");
			assertTrue(solo.searchText("在通讯录中仅刷新同步139邮箱联系人"));
			saveLog("关闭成功，提示【在通讯录中仅刷新同步139邮箱联系人】");
		}
		
		solo.goBack();
		//点击侧栏菜单按钮
		ImageView sideBarMenu = (ImageView) solo.getView("hjl_headicon", 0);
		solo.clickOnView(sideBarMenu);
		solo.clickOnText("通讯录");
		solo.sleep(2000);
		
		//点击全部
		solo.clickOnText("全部");
		
		dm = new DisplayMetrics();
		boolean isSuccess = false;
		//获取当前窗口大小
		solo.getCurrentActivity().getWindowManager()
		    .getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;
		int height = dm.heightPixels;
		solo.drag((width / 2), (width / 2), (height / 2),
				(height - 20), 50);
		//设置超时时间为2分钟
		long timeout = 120000;
		long now = System.currentTimeMillis();
		while(System.currentTimeMillis() < now + timeout) {
			if(solo.searchText("139邮箱通讯录同步完成")) {
				Log.d(TAG, "139邮箱通讯录同步完成");
				saveLog("139邮箱通讯录,手机联系人同步完成");
				isSuccess = true;
				break;
			}
		}
		assertTrue(isSuccess);
		saveLog("提示：139邮箱通讯录同步完成");
		saveLog("(27)设置-功能设置 测试通过");
	}
	
	/**
	 * 设置-其他
	 * 测试步骤：点击【关于】按钮
	 * 预期结果：显示139邮箱及当前的版本号
	 */
	public void test4OtherSetting() {
		saveLog("开始执行test4OtherSetting");
		goToSetting();
		solo.clickOnText("关于");
		solo.sleep(3000);
		if(!solo.searchText("139邮箱")) {
			solo.sleep(30000);
		}
		assertTrue(solo.searchText("139邮箱", true));
		saveLog("显示139邮箱");
		assertEquals(version, TestUtil.getTextOfTextView("version"));
		saveLog("及当前的版本号");
		saveLog("(28)设置-其他 测试通过");
	}
	
	/**
	 * 换肤
	 * 测试步骤：点击【换肤】按钮，选择一种颜色
	 * 预期结果：邮箱的背景显示为所选择的颜色
	 */
	public void test5ChangeSkin() {
		saveLog("开始执行test5ChangeSkin");
		ImageView sideBarMenu = (ImageView) solo.getView("hjl_headicon", 0);
		solo.clickOnView(sideBarMenu);
		TestUtil.clickById("skin_menu_click", 3);
		//选择蓝色背景
		TestUtil.clickById("skin_blue", 3);
		TestUtil.takescreen("蓝色背景");
		solo.sleep(2000);
		TestUtil.clickById("skin_menu_click", 3);
		//选择蓝色背景
		TestUtil.clickById("skin_red", 3);
		TestUtil.takescreen("红色背景");
		saveLog("(29)设置-其他 测试通过");
		solo.sleep(2000);
	}
	
	/**
	 * 进入设置页面
	 */
	private void goToSetting() {
		solo.sleep(3000);
		//点击侧栏菜单按钮
		ImageView sideBarMenu = (ImageView) solo.getView("hjl_headicon", 0);
		solo.clickOnView(sideBarMenu);
		solo.clickOnText("设置");
		solo.sleep(2000);
	}
	
	/**
	 * 获取手机上自sinceTime时间后的短信内容
	 * @param sinceTime
	 */
	private Cursor getSmsFromPhone(long sinceTime) {
		ContentResolver cr = getActivity().getContentResolver();
		String[] projection = new String[] { "address", "body" };
		Log.d(TAG, "查询短信");
		//139下发的短信是由 以10658139号码 开头 的账号发送的
		String where = " address like '10658139%' AND date > " + sinceTime;
		//按时间降序获取结果
		Cursor cur = cr.query(SMS_INBOX, projection, where, null, "date desc");
		Log.d(TAG, "cur: " +cur.toString());
		return cur;
	}
}
