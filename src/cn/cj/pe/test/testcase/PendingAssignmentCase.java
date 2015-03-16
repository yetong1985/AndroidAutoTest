package cn.cj.pe.test.testcase;

import android.util.Log;
import android.widget.ImageView;
import cn.cj.pe.test.BasicTestCase;
import cn.cj.pe.test.utils.TestUtil;

/**
 * 待办任务测试用例
 * @author yetong
 *
 */
public class PendingAssignmentCase extends BasicTestCase {
	
	private final String TAG = "PendingAssignmentCase";
	
	/**
	 * 待办任务-未完成
	 */
	public void test1Unaccomplished() {
		Log.d(TAG, "执行test1Unaccomplished");
		saveLog("开始执行testUnaccomplished");
		TestUtil.clickById("actionbar_expansion_view", 3);
		solo.clickOnText("收件箱");
		solo.sleep(2000);
		String mail_subject = TestUtil.getTextOfTextView("mail_subject");
		Log.d(TAG, "mail_subject : " + mail_subject);
		//点击第一封邮件
		solo.clickOnText(mail_subject);
		solo.sleep(3000);
		//获取原邮件的主题
		String origin_subject = TestUtil.getTextOfTextView("title");
		Log.d(TAG, "原始邮件的主题：" + origin_subject);
		solo.clickOnText(origin_subject);
		solo.sleep(2000);
		//点击右上角更多按钮
		TestUtil.clickById("actionbar_more", 3);;
		if(solo.searchText("标记完成")) {
			Log.d(TAG, "已标记任务，现在要点击标记完成，改变状态为未标记");
			solo.clickOnText("标记完成");
			solo.waitForDialogToClose();
			solo.sleep(5000);
			TestUtil.clickById("actionbar_more", 3);
			solo.sleep(2000);
		} 
		assertTrue(solo.searchText("标记任务"));
		//标记任务为待办任务
		solo.clickOnText("标记任务");
		solo.goBack();
		goToAssignment();
		solo.clickOnText("未完成");
		solo.sleep(3000);
		/*
		 * 如果是未读邮件，一进去收件箱时，获取到的邮件主题，跟已读的邮件可能会不完全一样
		 * 比如存在未读邮件，进入收件箱，获取到邮件主题；然后点击邮件进入邮件详情，再返回到收件箱，这时获取到的邮件主题跟之前获取到的可能不完全一致
		 * 第一次获取到的是：自动化拨测添加附件发送邮件142…
		 * 第二次获取到的是：自动化拨测添加附件发送邮件1425…
		 */
		if(mail_subject.equals(TestUtil.getTextOfTextView("mail_subject"))) {
			Log.d(TAG, "未完成标签下显示已标记的任务");
			saveLog("未完成标签下显示已标记的任务");
			assertEquals("标记成功", "标记成功");
			saveLog("(21)待办任务-未完成 用例测试 通过");
			return;
		} 
		solo.clickOnText(TestUtil.getTextOfTextView("mail_subject"));
		solo.sleep(3000);
		if(origin_subject.equals(TestUtil.getTextOfTextView("title"))) {
			Log.d(TAG, "未完成标签下显示已标记的任务");
			saveLog("未完成标签下显示已标记的任务");
			assertEquals("标记成功", "标记成功");
			saveLog("(21)待办任务-未完成 用例测试 通过");
		} else {
			Log.d(TAG, "未完成标签下没有已标记的任务");
			Log.d(TAG, TestUtil.getTextOfTextView("mail_subject"));
			saveLog("未完成标签下显示已标记的任务");
			saveLog("(21)待办任务-未完成 用例测试 不通过");
			assertEquals("标记成功", "标记失败");
		}
	}
	
	/**
	 * 待办任务-已完成
	 */
	public void test2Accomplished() {
		Log.d(TAG, "执行test2Accomplished");
		saveLog("开始执行testUnaccomplished");
		TestUtil.clickById("actionbar_expansion_view", 3);
		solo.clickOnText("收件箱");
		solo.sleep(2000);
		String mail_subject = TestUtil.getTextOfTextView("mail_subject");
		Log.d(TAG, "mail_subject : " + mail_subject);
		//点击第一封邮件
		solo.clickOnText(mail_subject);
		solo.sleep(2000);
		//获取原邮件的主题
		String origin_subject = TestUtil.getTextOfTextView("title");
		Log.d(TAG, "原始邮件的主题：" + origin_subject);
		solo.clickOnText(origin_subject);
		solo.sleep(2000);
		//点击右上角更多按钮
		TestUtil.clickById("actionbar_more", 3);
		if(solo.searchText("标记任务")) {
			Log.d(TAG, "该任务还未完成，现在要点击标记任务，改变状态为已完成");
			solo.clickOnText("标记任务");
			solo.waitForDialogToClose();
			TestUtil.clickById("actionbar_more", 3);
		} 
		assertTrue(solo.searchText("标记完成"));
		//标记任务为待办任务
		solo.clickOnText("标记完成");
		solo.goBack();
		goToAssignment();
		solo.clickOnText("已完成");
		solo.sleep(3000);
		if(mail_subject.equals(TestUtil.getTextOfTextView("mail_subject"))) {
			Log.d(TAG, "已完成标签下显示已标记完成的任务");
			saveLog("已完成标签下显示已标记完成的任务");
			assertEquals("标记完成", "标记完成");
			saveLog("(22)待办任务-已完成 用例测试 通过");
			return;
		} 
		solo.clickOnText(TestUtil.getTextOfTextView("mail_subject"));
		solo.sleep(3000);
		if(origin_subject.equals(TestUtil.getTextOfTextView("title"))) {
			Log.d(TAG, "已完成标签下显示已标记完成的任务");
			saveLog("已完成标签下显示已标记完成的任务");
			assertEquals("标记完成", "标记完成");
			saveLog("(22)待办任务-已完成 用例测试 通过");
		} else {
			Log.d(TAG, "已完成标签下没有已标记完成的任务");
			Log.d(TAG, TestUtil.getTextOfTextView("mail_subject"));
			saveLog("已完成标签下没有已标记完成的任务");
			saveLog("(22)待办任务-已完成 用例测试 不通过");
			assertEquals("标记完成", "标记失败");
		}
		solo.sleep(3000);
		solo.clickOnText("未完成");
	}
	
	/**
	 * 进入待办任务页面
	 */
	private void goToAssignment() {
		solo.sleep(3000);
		//点击侧栏菜单按钮
		ImageView sideBarMenu = (ImageView) solo.getView("hjl_headicon", 0);
		solo.clickOnView(sideBarMenu);
		solo.clickOnText("待办任务");
		solo.sleep(2000);
	}
	
}
