package cn.cj.pe.test.testcase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cn.cj.pe.test.BasicTestCase;
import cn.cj.pe.test.utils.TestUtil;

/**
 * 日历模块用例
 * @author yetong
 *
 */
public class CalendarCase extends BasicTestCase {
	
	private final String TAG = "CalendarCase";
	//新创建的日历名称
	private String newCalendar = "stone日历";
	//日历说明
	private String instruction = "自动化拨测";
	
	private String title = "自动化测试";
	private String content = "Android自动化测试培训";
	
	private AddressBookCase addressBookCase = new AddressBookCase();
	
	/**
	 * 日历-日期 用例
	 * 测试步骤：点击【日历】按钮
	 * 预期结果：日期显示当前的年月
	 */
	public void test1Date() {
		saveLog("开始执行test1Date");
		goToCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
		//获取当前日期
		String date = sdf.format(new Date());
		if(TestUtil.getTextOfTextView("cx_month_title_textview").equals(date)) {
			Log.d(TAG, "日期显示当前的年月：" + date);
			saveLog("(13)日历-日期显示 用例测试通过");
			assertEquals("日期显示当前的年月", "日期显示当前的年月");
		} else {
			TestUtil.takescreen("日历的日期显示错误");
			saveLog("(13)日历-日期显示 用例测试 不通过");
			assertEquals("日期显示当前的年月", "日期显示的不是当前的年月");
		}
	}
	
	/**
	 * 日历-我的日历 用例
	 * 操作 步骤：在日历页面，点击【我的日历】标签
	 * 预期结果：默认选择当前的日期
	 */
	public void test2MyCalendar() {
		saveLog("开始执行test2MyCalendar");
		goToCalendar();
		solo.clickOnText("我的日历");
		solo.sleep(1000);
		TextView today = (TextView) solo.getView("cx_btn_today");
		//默认选择当前的日期，则cx_btn_today不应该出现
		Log.d(TAG, "today.isShown() : " + today.isShown());
		assertFalse(today.isShown());
		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
		//获取当前日期
		String date = sdf.format(new Date());
		Log.d(TAG, "date: " + date);
		if(TestUtil.getTextOfTextView("cx_hand_textview").equals(date)) {
			saveLog("(14)日历-我的日历 用例测试通过");
			assertEquals("我的日历默认选择当前的日期", "我的日历默认选择当前的日期");
		} else{
			saveLog("(14)日历-我的日历 用例测试不通过");
			assertEquals("我的日历默认选择当前的日期", "我的日历默认没选择当前的日期");
		}
	}
	
	/**
	 * 日历-生日提醒 用例
	 * 操作 步骤：在日历页面，点击【生日提醒】标签
	 * 预期结果：进入生日提醒日历，默认选择当前日期
	 */
	public void test3BirthdayRemind() {
		saveLog("开始执行test3BirthdayRemind");
		goToCalendar();
		solo.clickOnText("生日提醒");
		solo.sleep(1000);
		TextView today = (TextView) solo.getView("cx_btn_today");
		assertFalse(today.isShown());
		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
		//获取当前日期
		String date = sdf.format(new Date());
		Log.d(TAG, "date: " + date);
		if(TestUtil.getTextOfTextView("cx_hand_textview").equals(date)) {
			saveLog("(15)日历-生日提醒 用例测试通过");
			assertEquals("生日提醒默认选择当前的日期", "生日提醒默认选择当前的日期");
		} else{
			saveLog("(15)日历-生日提醒 用例测试不通过");
			assertEquals("生日提醒默认选择当前的日期", "生日提醒默认没选择当前的日期");
		}
		solo.clickOnText("我的日历");
	}
	
	/**
	 * 日历-日历消息
	 * 这个用例暂时屏蔽掉，robotium跑几次之后会有问题
	 */
	/*
	public void test4CalendarMsg() {
		
		String other_account = addressBookCase.getOtherAccount();
		//取手机号，也是一个文本验证点
		String validation = other_account.substring(0, other_account.indexOf("@"));
		//退出当前账号，并重新登录
		if(!quitAccountAndReLogin(validation, addressBookCase.getOtherAccountPSW())) {
			Log.d(TAG, "登录失败");
			return;
		}

		//进入日历页面
		goToCalendar();
		solo.clickOnText("我的日历");
		solo.sleep(5000);
		//如果【今】按钮显示，则点击
		TextView today = (TextView) solo.getView("cx_btn_today");
		if(today.isShown()) {
			solo.clickOnView(today);
			solo.sleep(2000);
		}
		
		//点击创建活动按钮
		Log.d(TAG, "点击创建活动");
		solo.clickOnView(solo.getView("cx_create_schedule"));
		solo.waitForText("创建活动");
		solo.clickOnText("主题");
		String title = "测试日历消息" + System.currentTimeMillis();
		//输入活动主题	
		TestUtil.enterEditText("cx_sche_title", title);
		//输入活动内容
		TestUtil.enterEditText("cx_sche_content", "邀请好友参加活动会生成日历消息");
		//开始时间
		TextView start_time = (TextView) solo.getView("cx_start_date");
		//选择日期
		if(start_time.getText().toString().equals("日期")) {
			solo.clickOnView(start_time);
			solo.sleep(2000);
			solo.clickOnText("确定");
			solo.sleep(2000);
			TestUtil.clickById("cx_end_time", 3);
			solo.clickOnText("确定");
		}
		//邀请测试账号
		TestUtil.enterEditText("cx_invite_edittext", LoginCase._account+"@139.com");
		//使用默认时间，点击保存
		TestUtil.clickById("cx_title_btn_right", 3);
		assertTrue(solo.searchText("创建活动成功")); 
		solo.sleep(10000);
		//返回到收件箱
		solo.goBack();
		//退出账号并重新登录
		if(!quitAccountAndReLogin(LoginCase._account, LoginCase._password)) {
			Log.d(TAG, "登录失败");
			return;
		}
		
		//进入日历页面
		goToCalendar();
		solo.sleep(20000);
		
		//获取日历消息右上角的气泡提示
		String msg_count = TestUtil.getTextOfTextView("cx_messages_count");
		if(msg_count == null) {
			Log.d(TAG, "日历消息没有气泡提醒，测试失败");
			Log.d(TAG, "日历-日历消息 用例测试不通过");
			saveLog("(16)日历-日历消息 用例测试 通过");
			assertEquals("日历消息测试通过", "日历消息测试不通过");
			return;
		}
		Log.d(TAG, "msg_count = " + msg_count);
		Log.d(TAG, "日历消息的数量：" + Integer.parseInt(msg_count)); 
		//点击日历消息
		solo.clickOnView(solo.getView("cx_btn_news"));
		solo.sleep(5000);
		//消息标题格式是这样的：18664759987邀请您参与活动【共享日历消息】
		//String validation = addressBookCase.getOtherAccount() + "邀请您参与活动【" + title + "】"; 
		//获取所有的日历消息
		ListView msgList = (ListView) solo.getView("cx_schedule_listview");
		Log.d(TAG, "msgList.getCount : " + msgList.getCount());
		int count = msgList.getCount();
		//count-1，防止越界，获取到的数量比实际的多1
		for(int i = 0; i < count-1; i++) {
			TextView item = (TextView) solo.getView("cx_share_user", i);
			String share_msg = item.getText().toString();
			Log.d(TAG, "share_msg : " + share_msg);
			if(share_msg.indexOf(title) != -1 && 
					share_msg.indexOf(validation) != -1) {
				Log.d(TAG, "找到指定的日历消息了");
				solo.clickOnView(item);
				solo.sleep(2000);
				Log.d(TAG, "日历-日历消息 用例测试 通过");
				saveLog("(16)日历-日历消息 用例测试 通过");
				assertEquals("日历消息测试通过", "日历消息测试通过");
				return;
			}
		}
		Log.d(TAG, "日历-日历消息 用例测试不通过");
		saveLog("(16)日历-日历消息 用例测试 通过");
		assertEquals("日历消息测试通过", "日历消息测试不通过");
	} */
	
	/**
	 * 退出当前账号，重新登录
	 * @param account 要重新登录的账号
	 * @param password 要重新登录的账号密码
	 */
	private boolean quitAccountAndReLogin(String account, String password) {
		//点击侧栏菜单按钮
		solo.clickOnView(solo.getView("hjl_headicon", 0));
		solo.sleep(2000);
		//点击设置
		solo.clickOnText("设置");
		solo.sleep(2000);
		//点击当前登录的账号
		solo.clickOnView(solo.getView("account_name"));
		solo.sleep(2000);
		//点击退出账号
		TestUtil.clickById("account_logoff", 2);
		solo.waitForDialogToOpen();
		//点击确定按钮
		TestUtil.clickById("right", 2);
		solo.waitForDialogToClose();
		
		solo.sleep(2000);
		//输入账号、密码
		TestUtil.enterEditText("login_name", account);
		TestUtil.enterEditText("login_password", password);
		TestUtil.clickById("login", 3);
		if(solo.searchText("登录成功")) {
			Log.d(TAG, "登录成功");
			solo.sleep(10000);
			return true;
		} else if("收件箱".equals(TestUtil.getTextOfTextView(
				"actionbar_sub_title").substring(0, 3))) {
			Log.d(TAG, "进入到收件箱页面，登录成功");
			solo.sleep(10000);
			return true;
		}
		return false;
	}
	
	/**
	 * 日历-立即同步 用例
	 * 测试步骤：
	 * 1、在日历界面点击右上角【更多】按钮
	 * 2、点击【立即同步】按钮
	 * 预期结果：
	 * 1、弹出菜单选项
	 * 2、同步web端日历信息
	 */
	public void test5SyncCalendar() {
		saveLog("开始执行test5SyncCalendar");
		goToCalendar();
		//点击右上角更多按钮
		TestUtil.clickById("cx_btn_more", 3);
		solo.waitForDialogToOpen();
		if(solo.searchText("立即同步")) {
			solo.clickOnText("立即同步");
			if(solo.searchText("日历同步成功")) {
				Log.d(TAG, "日历同步成功");
				TestUtil.takescreen("日历同步成功");
				saveLog("日历同步成功");
				saveLog("(17)日历-立即同步 用例测试 通过");
				assertEquals("日历同步成功", "日历同步成功");
			} else {
				Log.d(TAG, "日历同步失败");
				TestUtil.takescreen("日历同步失败");
				saveLog("日历同步失败");
				saveLog("(17)日历-立即同步 用例测试 不通过");
				assertEquals("日历同步成功", "日历同步失败");
			}
		} else {
			Log.d(TAG, "没有找到 立即同步按钮");
			saveLog("没有找到 立即同步按钮");
			saveLog("(17)日历-立即同步 用例测试 不通过");
			assertEquals("立即同步", "没有找到 立即同步按钮");
			return;
		}
	}
	
	/**
	 * 日历-管理日历
	 * 测试步骤：
	 * 1、在日历界面点击右上角【更多】按钮
	 * 2、点击【管理日历】按钮
	 * 3、点击【创建日历】
	 * 4、输入日历内容点击【保存】按钮
	 * 预期结果：
	 * 1、可以创建日历
	 * 2、创建好的日历在我的日历中显示
	 * 3、创建的日历可删除
	 */
	public void test6ManageCalendar() {
		saveLog("开始执行test6ManageCalendar");
		goToCalendar();
		//点击右上角更多按钮
		TestUtil.clickById("cx_btn_more", 3);
		solo.waitForDialogToOpen();
		if(! solo.searchText("管理日历")) {
			Log.d(TAG, "没有找到 立即管理日历按钮");
			saveLog("没有找到 管理日历按钮");
			saveLog("(18)日历-管理日历 用例测试 不通过");
			assertEquals("管理日历", "没有找到 管理日历按钮");
			return;
		} 
		solo.clickOnText("管理日历");
		solo.sleep(2000);

		if(solo.searchText(newCalendar)) {
			Log.d(TAG, "已存在名为'" + newCalendar + "'的日历，要先删除掉");
			solo.clickOnText(newCalendar);	
			assertTrue(delCalendar(newCalendar));
		}
		
		//点击创建日历
		TestUtil.clickById("cx_create_label", 3);
		solo.sleep(3000);
		assertEquals("创建日历", 
				TestUtil.getTextOfTextView("cx_title_textview"));

		//输入日历名称
		TestUtil.enterEditText("cx_label_name", newCalendar);
		TestUtil.enterEditText("cx_label_instruction", instruction);
		solo.sleep(2000);
		//点击保存按钮
		TestUtil.clickById("cx_title_btn_right", 3);
		if(!solo.searchText("创建日历成功")) {
			Log.d(TAG, "创建日历失败");
			saveLog("创建日历失败");
			saveLog("(18)日历-管理日历 用例测试 不通过");
			assertEquals("创建日历成功", "创建日历失败");
			return;
		}
		Log.d(TAG, "创建日历成功");
		//在管理日历页找到新建的日历
		if(TestUtil.getTextOfTextView("cx_title_textview").equals("管理日历")
				&& solo.searchText(newCalendar)) {
			Log.d(TAG, "点击日历：" + newCalendar);
			solo.clickOnText(newCalendar);
			solo.sleep(2000);
			assertEquals(instruction, 
					TestUtil.getTextOfTextView("cx_label_instruction"));
			saveLog("(18)日历-管理日历 用例测试 通过");
		} else {
			Log.d(TAG, "在管理日历页没找到新建的日历");
			saveLog("在管理日历页没找到新建的日历，创建日历失败");
			saveLog("(18)日历-管理日历 用例测试 不通过");
			assertEquals("创建日历成功", "创建日历失败");
			return;
		} 
	}
	
	/**
	 * 日历-创建活动
	 * 测试步骤：
	 * 1、在我的日历页面点击新建活动按钮
	 * 2、输入活动内容点击保存
	 * 预期结果：
	 * 1、提示：创建活动成功
	 * 2、在日历页面活动日期有标识
	 */
	public void test7CreateActivity() {
		saveLog("开始执行test7CreateActivity");
		goToCalendar();
		solo.clickOnText("我的日历");
		if(solo.searchText("日历同步成功")) {
			Log.d(TAG, "日历同步成功");
		} else {
			if(solo.waitForText("日历同步成功", 1, 120000, false, true)) {
				Log.d(TAG, "等待日历同步成功");
			}
		}
		solo.sleep(10000);
		TextView today = (TextView) solo.getView("cx_btn_today");
		if(today.isShown()) {
			solo.clickOnView(today);
			solo.sleep(2000);
		}
		ArrayList<View> list = TestUtil.getViewsById("cx_sche_title");
		Log.d(TAG, "list.size: " + list.size());
		//如果活动已经存在，则需先删除
		if(list.size() > 0
				&& title.equals(((TextView) list.get(0)).getText())) {
			Log.d(TAG, title + " 活动已经存在，先把它删除");
			solo.clickOnView(list.get(0));
			solo.sleep(5000);
			//点击删除按钮
			TestUtil.clickById("cx_btn_del", 3);
			//点击确定
			TestUtil.clickById("cx_right", 3);
			if(solo.searchText("删除成功")) {
				Log.d(TAG, "删除活动成功");
			} else {
				Log.d(TAG, "删除活动失败");
			}
			solo.sleep(6000);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
		//获取当前日期
		String date = sdf.format(new Date());
		//点击创建活动按钮
		Log.d(TAG, "点击创建活动");
		TestUtil.clickById("cx_create_schedule", 3);
		solo.waitForText("创建活动");
		solo.clickOnText("主题");
		//输入活动主题
		list = TestUtil.getViewsById("cx_sche_title");
		Log.d(TAG, "list.size: " + list.size());
		solo.enterText((EditText) list.get(0), title);

		//输入活动内容
		TestUtil.enterEditText("cx_sche_content", content);
		//开始时间
		TextView start_time = (TextView) solo.getView("cx_start_date");
		//选择日期
		if(start_time.getText().toString().equals("日期")) {
			solo.clickOnView(start_time);
			solo.sleep(2000);
			solo.clickOnText("确定");
			solo.sleep(2000);
			TestUtil.clickById("cx_end_time", 3);
			solo.clickOnText("确定");
		}
		//使用默认时间，点击保存
		TestUtil.clickById("cx_title_btn_right", 3);
		assertTrue(solo.searchText("创建活动成功")); 
		list = TestUtil.getViewsById("cx_sche_title");
		Log.d(TAG, "活动标题： " + ((TextView) list.get(0)).getText());
		if(((TextView) list.get(0)).getText().equals(title)
				&& TestUtil.getTextOfTextView("cx_hand_textview").equals(date)) {
			assertEquals("在日历页面活动日期有标识", "在日历页面活动日期有标识");
			saveLog("(19)日历-创建活动 用例测试 通过");
			TestUtil.takescreen("日历-创建活动");
		} else {
			assertEquals("在日历页面活动日期有标识", "在日历页面活动日期没有标识");
			saveLog("(19)日历-创建活动 用例测试 不通过");
			TestUtil.takescreen("日历-创建活动 失败");
		}
	}
	
	/**
	 * 进入日历页面
	 */
	private void goToCalendar() {
		solo.sleep(3000);
		//点击侧栏菜单按钮
		ImageView sideBarMenu = (ImageView) solo.getView("hjl_headicon", 0);
		solo.clickOnView(sideBarMenu);
		solo.clickOnText("日历");
		solo.sleep(2000);
	}
	
	private boolean delCalendar(String calendarName) {
		solo.clickOnText(calendarName);
		solo.sleep(2000);
		//在日历详情页点击删除按钮
		TestUtil.clickById("cx_title_btn_right", 3);
		solo.waitForDialogToOpen();
		//点击确定按钮
		TestUtil.clickById("cx_right", 3);
		solo.sleep(2000);
		if(solo.searchText(calendarName)) {
			Log.d(TAG, "删除日历失败");
			return false;
		}
		return true;
	}
}
