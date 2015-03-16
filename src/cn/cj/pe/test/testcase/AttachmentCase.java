package cn.cj.pe.test.testcase;

import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import cn.cj.pe.test.BasicTestCase;
import cn.cj.pe.test.utils.TestUtil;

/**
 * ���������������
 * @author yetong
 *
 */
public class AttachmentCase extends BasicTestCase {

	private final String TAG = "AttachmentCase";
	//�����ؼ���
	private String keyWord = "139";
	
	/**
	 * ���Ը�������-��������
	 */
	public void test1SearchAttachment() {
		saveLog("��ʼִ��testSearchAttachment");
		goToAttachmentManagement();
		//���ȫ��
		//solo.clickOnText("ȫ��");
		solo.clickOnView(solo.getView("show_contact_all", 1));
		solo.sleep(2000);
		//�������
		solo.clickOnText("����");
		//�ڵ�ǰҳ��ĵ�һ���༭��������ؼ���
		solo.enterText(0, keyWord);
		solo.sleep(2000);
		/*
		ListView attachmentList = (ListView) solo.getView("listView");
		Log.d(TAG, "attachmentList.getChildCount: " 
		    + attachmentList.getChildCount());
		*/
		if(solo.searchText("��ƥ����")) {
			Log.d(TAG, "��ƥ����");
			assertEquals("��ƥ����", "��ƥ����");
			saveLog("(11)��������-�������� ��������ͨ��");
			return;
		}
		if(solo.searchText(keyWord)) {
			Log.d(TAG, "�ҵ���ָ���ĸ�����");
			TestUtil.takescreen("��ʾ�������-ƥ��ؼ���");
			assertEquals("��ʾ�������", "��ʾ�������");
			saveLog("(11)��������-�������� ��������ͨ��");
			return;
		}
		saveLog("(11)��������-�������� �������Բ�ͨ��");
		assertEquals("��ʾ�������", "����ʾ�������");
	}
	
	/**
	 * ���Ը�������-���ظ���
	 */
	public void test2DownloadAttachment() {
		saveLog("��ʼִ��testDownloadAttachment");
		goToAttachmentManagement();
		//���ȫ��
		solo.clickOnView(solo.getView("show_contact_all", 1));
		solo.sleep(2000);
		//��ȡ��һ������������
		TextView attachName = (TextView) solo.getView("name");
		if(attachName == null) {
			Log.d(TAG, "û�и���");
			TestUtil.takescreen("û�и���");
			return;
		}
		Log.d(TAG, "name.getText: " + attachName.getText());
		String fileName = attachName.getText().toString();
		//�����һ���ļ������ذ�ť
		solo.clickOnView(solo.getView("control"));
		solo.sleep(2000);
		if (TestUtil.getTextOfTextView("action_download_txt").equals("��")) {
			Log.d(TAG, fileName + "�Ѿ������ˣ�����Ҫ����ɾ����");
			if(delAttachment(fileName) == false) {
				Log.d(TAG, "�����صĸ���ɾ��ʧ��");
				return;
			}
		} 
		//�����һ�����������ذ�ť
		TestUtil.clickLongById("action_download", 3);
		ProgressBar pb = (ProgressBar) solo.getView("progressBar");
		long timeNow = System.currentTimeMillis();
		boolean isDownloadSuccess = false;
		//���ó�ʱ������
		long timeOut = 120000;
		while(System.currentTimeMillis() < timeNow + timeOut) {
			if(!pb.isShown()) {
				Log.d(TAG, "��������ʧ��");
				isDownloadSuccess = true;
				break;
			}
		}
		if(!isDownloadSuccess) {
			Log.d(TAG, "���������ڣ����ظ���ʧ��");
			TestUtil.takescreen("�������س�ʱ");
			saveLog("(12)��������-���ظ��� �������Բ�ͨ��");
			assertEquals("�������سɹ�", "��������ʧ��");
		}
		solo.sleep(10000);
		if(TestUtil.getTextOfTextView("action_download_txt").equals("��")) {
			Log.d(TAG, "�������سɹ������������ذ�ť��Ϊ�򿪰�ť");
			TestUtil.takescreen("�������سɹ�");
			assertEquals("�������سɹ�", "�������سɹ�");
			saveLog("(12)��������-���ظ��� ��������ͨ��");
		} else {
			Log.d(TAG, "��������ʧ�ܣ����������ذ�ťû�б�Ϊ�򿪰�ť");
			TestUtil.takescreen("��������ʧ��");
			saveLog("(12)��������-���ظ��� �������Բ�ͨ��");
			assertEquals("�������سɹ�", "��������ʧ��");
		}
	}
	
	/**
	 * ���븽������ҳ��ҳ��
	 */
	private void goToAttachmentManagement() {
		solo.sleep(3000);
		//��������˵���ť
		ImageView sideBarMenu = (ImageView) solo.getView("hjl_headicon", 0);
		solo.clickOnView(sideBarMenu);
		solo.clickOnText("��������");
		solo.sleep(2000);
	}
	
	/**
	 * ɾ�������ļ�
	 * @param fileName: Ҫɾ���ĸ�������
	 */
	private boolean delAttachment(String fileName) {
		solo.clickOnText("������");
		if(solo.searchText(fileName)) {
			solo.clickOnView(solo.getView("control"));
			solo.sleep(2000);
			solo.clickOnText("ɾ��");
			solo.waitForDialogToOpen();
			//solo.clickOnText("ȷ��");
			TestUtil.clickById("right", 3);
			solo.waitForDialogToClose();
			if(solo.searchText("���ļ���ɾ��")
					|| !solo.searchText(fileName)) {
				Log.d(TAG, "��ɾ���ļ���" + fileName);
				//���ȫ��
				solo.clickOnView(solo.getView("show_contact_all", 1));
				solo.sleep(2000);
				//�����һ���ļ������ذ�ť
				solo.clickOnView(solo.getView("control"));
				solo.sleep(2000);
				return true;
			}
		}
		return false;
	}
}
