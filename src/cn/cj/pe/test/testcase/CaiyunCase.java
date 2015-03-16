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
 * �������̲�������
 * @author yetong
 *
 */
public class CaiyunCase extends BasicTestCase {

	private final String TAG = "CaiyunCase";
	private String fileName = "1080";
	private String to_accound = "18664759987@139.com";
	private String to_subject = "���Ͳ��Ƹ���";
	private String to_content = "���ǴӲ��Ƹ������͵��ʼ�";
	
	/**
	 * �������̲���
	 */
	public void testCaiyun() {
		saveLog("��ʼִ��testCaiyun");
		goToCaiyun();

		if(solo.searchText("���ڼ���", true)) {
			Log.d(TAG, "�������ڼ���");
			solo.sleep(60000);
		}
		//��ȡ"�����ļ�"�ı����ؼ�
		TextView bg = (TextView) solo.getView("list_backgound_text", 1);
		Log.d(TAG, "bg.getText: " + bg.getText().toString());
		Log.d(TAG, "bg.isShown: " + bg.isShown());
		
		if(bg.isShown()) {
			Log.d(TAG, "��������ҳ����ʾ�����ļ�������ʧ��");
			saveLog("(20)���� �������� ��ͨ��");
			assertEquals("�������̼��سɹ�", "��������ҳ����ʾ�����ļ�");
			return;
		}
		if(!solo.searchText(fileName)) {
			Log.d(TAG, "��������ҳ��û�ҵ����ļ�");
			saveLog("(20)���� �������� ��ͨ��");
			assertEquals("�������̼��سɹ�", "��������ҳ����ʾ�����ļ�");
			return;
		} 
		TestUtil.clickById("cloud_arrow", 3);
		if(TestUtil.getTextOfTextView("cloud_down_or_open_text").equals("����")) {
			Log.d(TAG, "�ҵ����ذ�ť");
			TestUtil.clickById("cloud_down_or_open_text", 3);
			//���ؽ���������
			LinearLayout layout = (LinearLayout) solo.getView("cloud_bar_ll");
			//�Ƿ��Ѿ�����
			boolean isDownload = false;
			long timeOut = System.currentTimeMillis() + 120000;
			while(System.currentTimeMillis() < timeOut) {
				if(!layout.isShown()) {
					Log.d(TAG, "�������");
					isDownload = true;
					break;
				}
			}
			if(isDownload) {
				Log.d(TAG, "�����ļ����سɹ�");
				assertTrue(TestUtil.getTextOfTextView("cloud_down_or_open_text")
						.equals("��"));
				Log.d(TAG, "���غ�ɴ�");
				saveLog("�����ļ����سɹ�, ���غ�ɴ�");
			} else {
				Log.d(TAG, "�����ļ����س�ʱ");
				saveLog("�����ļ����س�ʱ");
				assertEquals("���سɹ�", "���س�ʱ");
			}
		} 
		//�������
		solo.clickOnText("����");
		solo.sleep(2000);
		TextView attachment_count = (TextView) solo.getView("attachment_count");
		Log.d(TAG, "����������" + attachment_count.getText().toString());
	//	assertTrue(attachment_count.getText().toString().equals("1"));
	//	Log.d(TAG, "����������ȷ");
		solo.clickOnText("����/����");
		solo.clickOnText("�ռ���");
		//��ȡ�ռ��������
		MultiAutoCompleteTextView tomail = 
				solo.getView(MultiAutoCompleteTextView.class, 0);
		solo.enterText(tomail, to_accound);
		to_subject = "�Զ��������ʼ�" + System.currentTimeMillis();
		//�����ʼ�����
		TestUtil.enterEditText("subject", to_subject);
		
		TestUtil.enterEditText("editTextField", to_content);
		solo.sleep(5000);
		//�����ʼ�
		ImageView sendMail = (ImageView) solo.getView("hjl_headicon", 1);
		solo.clickLongOnView(sendMail);
		solo.sleep(10000);
		assertEquals("��������",
				TestUtil.getTextOfTextView("hjl_icon_local_contact_title_name"));
		Log.d(TAG, "�ļ�������������");
		saveLog("(20)���� �������� ͨ��");
	}
	
	/**
	 * �����������ҳ��
	 */
	private void goToCaiyun() {
		solo.sleep(3000);
		//��������˵���ť
		ImageView sideBarMenu = (ImageView) solo.getView("hjl_headicon", 0);
		solo.clickOnView(sideBarMenu);
		solo.clickOnText("����");
		solo.sleep(2000);
	}
}
