package cn.cj.pe.test.testcase;

import android.util.Log;
import android.widget.ImageView;
import cn.cj.pe.test.BasicTestCase;
import cn.cj.pe.test.utils.TestUtil;

/**
 * ���������������
 * @author yetong
 *
 */
public class PendingAssignmentCase extends BasicTestCase {
	
	private final String TAG = "PendingAssignmentCase";
	
	/**
	 * ��������-δ���
	 */
	public void test1Unaccomplished() {
		Log.d(TAG, "ִ��test1Unaccomplished");
		saveLog("��ʼִ��testUnaccomplished");
		TestUtil.clickById("actionbar_expansion_view", 3);
		solo.clickOnText("�ռ���");
		solo.sleep(2000);
		String mail_subject = TestUtil.getTextOfTextView("mail_subject");
		Log.d(TAG, "mail_subject : " + mail_subject);
		//�����һ���ʼ�
		solo.clickOnText(mail_subject);
		solo.sleep(3000);
		//��ȡԭ�ʼ�������
		String origin_subject = TestUtil.getTextOfTextView("title");
		Log.d(TAG, "ԭʼ�ʼ������⣺" + origin_subject);
		solo.clickOnText(origin_subject);
		solo.sleep(2000);
		//������ϽǸ��ఴť
		TestUtil.clickById("actionbar_more", 3);;
		if(solo.searchText("������")) {
			Log.d(TAG, "�ѱ����������Ҫ��������ɣ��ı�״̬Ϊδ���");
			solo.clickOnText("������");
			solo.waitForDialogToClose();
			solo.sleep(5000);
			TestUtil.clickById("actionbar_more", 3);
			solo.sleep(2000);
		} 
		assertTrue(solo.searchText("�������"));
		//�������Ϊ��������
		solo.clickOnText("�������");
		solo.goBack();
		goToAssignment();
		solo.clickOnText("δ���");
		solo.sleep(3000);
		/*
		 * �����δ���ʼ���һ��ȥ�ռ���ʱ����ȡ�����ʼ����⣬���Ѷ����ʼ����ܻ᲻��ȫһ��
		 * �������δ���ʼ��������ռ��䣬��ȡ���ʼ����⣻Ȼ�����ʼ������ʼ����飬�ٷ��ص��ռ��䣬��ʱ��ȡ�����ʼ������֮ǰ��ȡ���Ŀ��ܲ���ȫһ��
		 * ��һ�λ�ȡ�����ǣ��Զ���������Ӹ��������ʼ�142��
		 * �ڶ��λ�ȡ�����ǣ��Զ���������Ӹ��������ʼ�1425��
		 */
		if(mail_subject.equals(TestUtil.getTextOfTextView("mail_subject"))) {
			Log.d(TAG, "δ��ɱ�ǩ����ʾ�ѱ�ǵ�����");
			saveLog("δ��ɱ�ǩ����ʾ�ѱ�ǵ�����");
			assertEquals("��ǳɹ�", "��ǳɹ�");
			saveLog("(21)��������-δ��� �������� ͨ��");
			return;
		} 
		solo.clickOnText(TestUtil.getTextOfTextView("mail_subject"));
		solo.sleep(3000);
		if(origin_subject.equals(TestUtil.getTextOfTextView("title"))) {
			Log.d(TAG, "δ��ɱ�ǩ����ʾ�ѱ�ǵ�����");
			saveLog("δ��ɱ�ǩ����ʾ�ѱ�ǵ�����");
			assertEquals("��ǳɹ�", "��ǳɹ�");
			saveLog("(21)��������-δ��� �������� ͨ��");
		} else {
			Log.d(TAG, "δ��ɱ�ǩ��û���ѱ�ǵ�����");
			Log.d(TAG, TestUtil.getTextOfTextView("mail_subject"));
			saveLog("δ��ɱ�ǩ����ʾ�ѱ�ǵ�����");
			saveLog("(21)��������-δ��� �������� ��ͨ��");
			assertEquals("��ǳɹ�", "���ʧ��");
		}
	}
	
	/**
	 * ��������-�����
	 */
	public void test2Accomplished() {
		Log.d(TAG, "ִ��test2Accomplished");
		saveLog("��ʼִ��testUnaccomplished");
		TestUtil.clickById("actionbar_expansion_view", 3);
		solo.clickOnText("�ռ���");
		solo.sleep(2000);
		String mail_subject = TestUtil.getTextOfTextView("mail_subject");
		Log.d(TAG, "mail_subject : " + mail_subject);
		//�����һ���ʼ�
		solo.clickOnText(mail_subject);
		solo.sleep(2000);
		//��ȡԭ�ʼ�������
		String origin_subject = TestUtil.getTextOfTextView("title");
		Log.d(TAG, "ԭʼ�ʼ������⣺" + origin_subject);
		solo.clickOnText(origin_subject);
		solo.sleep(2000);
		//������ϽǸ��ఴť
		TestUtil.clickById("actionbar_more", 3);
		if(solo.searchText("�������")) {
			Log.d(TAG, "������δ��ɣ�����Ҫ���������񣬸ı�״̬Ϊ�����");
			solo.clickOnText("�������");
			solo.waitForDialogToClose();
			TestUtil.clickById("actionbar_more", 3);
		} 
		assertTrue(solo.searchText("������"));
		//�������Ϊ��������
		solo.clickOnText("������");
		solo.goBack();
		goToAssignment();
		solo.clickOnText("�����");
		solo.sleep(3000);
		if(mail_subject.equals(TestUtil.getTextOfTextView("mail_subject"))) {
			Log.d(TAG, "����ɱ�ǩ����ʾ�ѱ����ɵ�����");
			saveLog("����ɱ�ǩ����ʾ�ѱ����ɵ�����");
			assertEquals("������", "������");
			saveLog("(22)��������-����� �������� ͨ��");
			return;
		} 
		solo.clickOnText(TestUtil.getTextOfTextView("mail_subject"));
		solo.sleep(3000);
		if(origin_subject.equals(TestUtil.getTextOfTextView("title"))) {
			Log.d(TAG, "����ɱ�ǩ����ʾ�ѱ����ɵ�����");
			saveLog("����ɱ�ǩ����ʾ�ѱ����ɵ�����");
			assertEquals("������", "������");
			saveLog("(22)��������-����� �������� ͨ��");
		} else {
			Log.d(TAG, "����ɱ�ǩ��û���ѱ����ɵ�����");
			Log.d(TAG, TestUtil.getTextOfTextView("mail_subject"));
			saveLog("����ɱ�ǩ��û���ѱ����ɵ�����");
			saveLog("(22)��������-����� �������� ��ͨ��");
			assertEquals("������", "���ʧ��");
		}
		solo.sleep(3000);
		solo.clickOnText("δ���");
	}
	
	/**
	 * �����������ҳ��
	 */
	private void goToAssignment() {
		solo.sleep(3000);
		//��������˵���ť
		ImageView sideBarMenu = (ImageView) solo.getView("hjl_headicon", 0);
		solo.clickOnView(sideBarMenu);
		solo.clickOnText("��������");
		solo.sleep(2000);
	}
	
}
