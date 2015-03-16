package cn.cj.pe.test.testcase;

import java.util.ArrayList;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cn.cj.pe.test.BasicTestCase;
import cn.cj.pe.test.utils.TestUtil;

/**
 * 测试添加邮箱和通讯录
 * @author yetong
 *
 */
public class AddressBookCase extends BasicTestCase {
	
	private final String TAG = "AddressBookCase";
	//保存窗口大小
	private DisplayMetrics dm;
	//web端通讯录联系人
	private String webContact = "webAddress@qq.com";
	//要添加的账号
	private String other_Account = "18664759987@139.com";
	private String other_psw = "chinasoft";
	
	/**
	 * 测试添加邮箱
	 */
	public void testAddAccount() {
		saveLog("开始执行testAddAccount");
		solo.sleep(3000);
		//点击侧栏菜单按钮
		ImageView sideBarMenu = (ImageView) solo.getView("hjl_headicon", 0);
		solo.clickOnView(sideBarMenu);
		solo.sleep(2000);
		if(solo.searchText(other_Account)) {
			Log.d(TAG, "要添加的账户已存在，必须先把该账户注销");
			cancelTheAddedAcount();
			solo.clickOnView(solo.getView("hjl_headicon", 0));
			solo.sleep(2000);
		}
		//点击添加邮箱按钮
		TestUtil.clickById("menu_add_account", 3);
		solo.sleep(2000);
		TestUtil.enterEditText("add_account_email", other_Account);
		TestUtil.enterEditText("add_account_password", other_psw);
		solo.sleep(2000);
		//点击保存按钮
		solo.clickOnView(solo.getView("hjl_headicon", 1));
		//设置超时时间为2分钟
		long timeout = 120000;
		long now = System.currentTimeMillis();
		while(System.currentTimeMillis() < now + timeout) {
			if(solo.searchText("登录成功")) {
				Log.d(TAG, "获取到登录成功的提示");
				saveLog("成功添加邮箱");
				break;
			} else if(solo.searchText("设置签名")) {
				Log.d(TAG, "已成功添加邮箱");
				saveLog("已成功添加邮箱");
				break;
			} 
		}
		solo.goBack();
		
		if(solo.searchText(other_Account)) {
			Log.d(TAG, "找到新添加的邮箱账户了");
			solo.takeScreenshot("成功添加邮箱");
			saveLog("(8)添加邮箱 用例测试通过");
			assertEquals("添加邮箱成功", "添加邮箱成功");
		} else {
			Log.d(TAG, "没找到新添加的邮箱账户");
			solo.takeScreenshot("添加邮箱失败");
			saveLog("(8)添加邮箱 用例测试不通过");
			assertEquals("添加邮箱成功", "添加邮箱失败");	
		} 
		
		solo.sleep(3000);
		solo.clickOnText(LoginCase._account);
	}
	/**
	 * 测试通讯录-全部
	 */
	public void testAllAddressBook() {
		saveLog("开始执行testAllAddressBook");
		setReadingPhoneContacts();
		goToAddressBook();
		//点击全部
		solo.clickOnText("全部");
		
		dm = new DisplayMetrics();
		//获取当前窗口大小
		solo.getCurrentActivity().getWindowManager()
		    .getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;
		int height = dm.heightPixels;
		solo.drag((width / 2), (width / 2), (height / 2),
				(height - 20), 50);
		//设置超时时间为5分钟
		long timeout = 300000;
		long now = System.currentTimeMillis();
		while(System.currentTimeMillis() < now + timeout) {
			if(solo.searchText("139邮箱通讯录,手机联系人同步完成")) {
				Log.d(TAG, "139邮箱通讯录,手机联系人同步完成");
				saveLog("139邮箱通讯录,手机联系人同步完成");
				break;
			}
		}
		if(solo.searchText(webContact)) {
			assertEquals("通讯录全部", "通讯录全部");
			saveLog("(9)通讯录-全部 用例测试通过");
		} else {
			saveLog("(9)通讯录-全部 用例测试不通过");
			assertEquals("通讯录全部", "通讯录没同步");
		}
		
	}
	
	/**
	 * 测试通讯录-分组
	 */
	public void testGroupAddressBook() {
		saveLog("开始执行testGroupAddressBook");
		goToAddressBook();
		//点击分组
		solo.clickOnText("分组");
		solo.sleep(2000);
		ArrayList<View> groups = TestUtil.getViewsById("group_name");
		Log.d(TAG, "groups.size: " + groups.size());
		if(groups.size() > 0 && solo.searchText("读信联系人")) {
			TestUtil.takescreen("通讯录分组");
			assertEquals("通讯录分组", "通讯录分组");
			saveLog("(10)通讯录-分组 用例测试通过");
		} else {
			TestUtil.takescreen("通讯录分组失败");
			saveLog("(10)通讯录-分组 用例测试不通过");
			assertEquals("通讯录分组", "通讯录分组失败");
		}
	}
	
	/**
	 * 进入热门通讯页面
	 */
	private void goToAddressBook() {
		solo.sleep(3000);
		//点击侧栏菜单按钮
		ImageView sideBarMenu = (ImageView) solo.getView("hjl_headicon", 0);
		solo.clickOnView(sideBarMenu);
		solo.clickOnText("通讯录");
		solo.sleep(2000);
	}
	
	/**
	 * 设置开启允许读取手机联系人按钮
	 */
	private void setReadingPhoneContacts() {
		solo.sleep(3000);
		//点击侧栏菜单按钮
		ImageView sideBarMenu = (ImageView) solo.getView("hjl_headicon", 0);
		solo.clickOnView(sideBarMenu);
		solo.clickOnText("设置");
		solo.sleep(2000);
		solo.clickOnText("允许读取手机联系人");
		if(solo.searchText("在通讯录中可刷新同步手机联系人")) {
			Log.d(TAG, "已开启允许读取手机联系人按钮");
			saveLog("已开启允许读取手机联系人按钮");
		} else {
			Log.d(TAG, "还未开启允许读取手机联系人按钮，现在打开开关");
			saveLog("还未开启允许读取手机联系人按钮，现在打开开关");
			solo.clickOnText("允许读取手机联系人");
			solo.sleep(2000);
		}
		solo.goBack();
	}
	
	/*
	 * 注销新添加的账户
	 */
	private void cancelTheAddedAcount() {
		solo.sleep(5000);
		solo.clickOnText("设置");
		solo.sleep(2000);
		ArrayList<View> accountList = TestUtil.getViewsById("account_name");
		Log.d(TAG, "accountList.size: " + accountList.size());
		for(View view : accountList) {
			if(view instanceof TextView) {
				if(((TextView) view).getText().equals(other_Account)) {
					solo.clickOnView(view);
					break;
				}
			}
		}
		//solo.clickOnText("注销帐号");
		TestUtil.clickById("account_logoff", 3);
		solo.waitForDialogToOpen();
		solo.clickOnText("确定");
		solo.waitForDialogToClose();
		if(solo.searchText("成功注销")) {
			Log.d(TAG, "成功注销" + other_Account);
			saveLog("成功注销" + other_Account);
		} else {
			Log.d(TAG, "注销" + other_Account +"失败");
			saveLog("注销" + other_Account +"失败");
		}
		solo.sleep(2000);
	}
	
	public String getOtherAccount() {
		return other_Account;
	}
	
	public String getOtherAccountPSW() {
		return other_psw;
	}
}
