package cn.cj.pe.test.testcase;

import android.util.Log;
import android.widget.MultiAutoCompleteTextView;
import cn.cj.pe.test.BasicTestCase;
import cn.cj.pe.test.utils.TestUtil;

public class ReceiveMailCase extends BasicTestCase {

	private final String TAG = "ReceiveMailCase";
	private AddressBookCase addressBookCase = new AddressBookCase();
	
	/**
	 * ���ʼ�-����ˢ���ʼ�
	 * ���Բ��裺
	 * 1��ʹ�������˺ŵ�����������˺����䷢��һ���ʼ�
	 * Ԥ�ڽ����
	 * 1��ʹ�������˺ŵ�����������˺����䷢��һ���ʼ�
	 */
	public void testRefleshMail() {
		String other_account = addressBookCase.getOtherAccount();
		TestUtil.clickById("actionbar_expansion_view", 3);
		//����ռ���
		solo.clickOnText("�ռ���");
		solo.sleep(2000);
		//��������˵���ť
		solo.clickOnView(solo.getView("hjl_headicon", 0));
		solo.sleep(3000);
		//�������󶨵���һ���˻�
		solo.clickOnText(other_account);
		solo.sleep(2000);
		//���ռ���ҳ����д�ʼ���ť,���±�1��������������˵���ť
		solo.clickLongOnView(solo.getView("hjl_headicon", 1));
		solo.sleep(3000);
		
		//�ȵ�����ͣ��ٵ���ռ��ˣ��������ܰ�MultiAutoCompleteTextView�ؼ�������
		solo.clickOnText("����/����");
		solo.clickOnText("�ռ���");
		//��ȡ�ռ��������
		MultiAutoCompleteTextView tomail = 
				solo.getView(MultiAutoCompleteTextView.class, 0);
		solo.enterText(tomail, LoginCase._account + "@139.com");
		String to_subject = "��������ˢ���ʼ�" +  System.currentTimeMillis();
		//�����ʼ�����
		TestUtil.enterEditText("subject", to_subject);
		TestUtil.enterEditText("editTextField", "����ˢ���ʼ�");
		solo.sleep(3000);
		//�����ʼ�
		solo.clickOnView(solo.getView("hjl_headicon", 1));
		//���ص��ռ���ҳ��
		solo.goBack();
		
		//��������˵���ť
		solo.clickOnView(solo.getView("hjl_headicon", 0));
		//�������󶨵Ĳ����˻�
		solo.clickOnText(LoginCase._account);
		//���ó�ʱ�����
		long timeout = System.currentTimeMillis() + 5*60*1000;
		boolean isSuccess = false;
		String temp;
		while(System.currentTimeMillis() < timeout) {
			//��ȡ�ռ����һ���ʼ�
			temp = TestUtil.getTextOfTextView("mail_subject");
			//Log.d(TAG, "temp = " + temp);
			//��ȡ��һ���ʼ���¼�����⣬��������������ȥ�������'...'
			if(to_subject.equals(temp) || 
					to_subject.startsWith(temp.substring(0, temp.length()-3))) {
				Log.d(TAG, TestUtil.getTextOfTextView("mail_subject"));
				isSuccess = true;
				break;
			} 
			//solo.sleep(5000);
		}
		
		if(isSuccess) {
			Log.d(TAG, "�����˺ŵ��ռ�������ˢ�³����ʼ�");
			assertEquals("����ͨ��", "����ͨ��");
			saveLog("(6)���ʼ�-����ˢ���ʼ� ����ͨ��");
		} else {
			Log.d(TAG, "�����˺ŵ��ռ���û������ˢ�³����ʼ��������ǽ����ʼ���ʱ��");
			assertEquals("����ͨ��", "����ʧ��");
			saveLog("(6)���ʼ�-����ˢ���ʼ� ���Բ�ͨ��");
		}
	}
}
