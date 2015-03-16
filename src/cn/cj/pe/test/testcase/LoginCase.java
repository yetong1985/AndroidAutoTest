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
	//�ռ���
	private String to_accound = "yetong@chinasofti.com";
	//�����ʼ�����
	private String to_subject = "";
	//�����ʼ�����
	private String to_content = "����һ������ʼ�";
	//������
	private String cc_accound = _account + "@139.com";
	
	/**
	 * ���Ե�¼
	 * ���Բ��裺
	 * 1�������ֻ����롢����
	 * 2�������¼
	 * Ԥ�ڽ����
	 * ��ʾ��¼�ɹ��������ռ���ҳ��
	 */
	public void test1Login() {
		saveLog("��ʼִ��testLogin");
		//����ͻ�������
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
		
		Log.d(TAG, "����֮��" + solo.getCurrentActivity().getClass().getName());
		
		//�����˺š�����
		TestUtil.enterEditText("login_name", _account);
		TestUtil.enterEditText("login_password", _password);
		
		TestUtil.clickById("login", 3);
		
		Long loadTime = System.currentTimeMillis();
		
		ImageView loadView = (ImageView) solo.getView("main_view");
		
		while(true) {
			if(! loadView.isShown()) {
				Log.d(TAG, "����ͼ�겻����");
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
		Log.d(TAG, "�б����ʱ��Ϊ��" + loadTime +"����");
		//saveLog("�״ε�¼�ͻ��ˣ������ʼ��б��ʱ��"  + loadTime +"����");
		/*
		solo.sleep(10000);
		Log.d(TAG, "�����¼֮�󣬽���" + solo.getCurrentActivity()
				.getClass().getName());
		Log.d(TAG, "mailList.getChildCount:��" + mailList.getChildCount()); 
		*/
		solo.sleep(10000);
		if("�ռ���".equals(TestUtil.getTextOfTextView(
				"actionbar_sub_title").substring(0, 3))) {
			TestUtil.takescreen("139���䲦��-��¼�ɹ�");
			assertEquals("��¼�ɹ�", "��¼�ɹ�");
			saveLog("(1)139���䲦��-��¼���� ����ͨ��");
		} else {
			TestUtil.takescreen("139���䲦��-��¼ʧ��");
			saveLog("(1)139���䲦��-��¼���� ���Բ�ͨ��");
			assertEquals("��¼�ɹ�", "��¼ʧ��");
		}
		solo.sleep(2000);
	}
	
	/**
	 * ����ֱ�ӷ����ʼ�
	 * ���Բ��裺
	 * 1�������д�ʼ�����ť
	 * 2�������ռ����˺š�����
	 * 3����д�ʼ�����
	 * 4����������Ͱ�ť��
	 * Ԥ�ڽ����
	 * 1���ʼ����ͳɹ�
	 * 2����������ڸղ������ͳɹ����ʼ�
	 */
	public void test2SendMail() {
		saveLog("��ʼִ��testSendMail");
		solo.sleep(5000);
		//���ռ���ҳ����д�ʼ���ť,���±�1��������������˵���ť
		ImageView editMail = (ImageView) solo.getView("hjl_headicon", 1);
		solo.clickLongOnView(editMail);
		solo.sleep(3000);
		//�ȵ�����ͣ��ٵ���ռ��ˣ��������ܰ�MultiAutoCompleteTextView�ؼ�������
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
		solo.sleep(10000);
		//�����ʼ�
		ImageView sendMail = (ImageView) solo.getView("hjl_headicon", 1);
		solo.clickOnView(sendMail);
		solo.sleep(10000);
		
		solo.goBack();
		TestUtil.clickById("actionbar_expansion_view", 3);
		solo.clickOnText("�ѷ���");
		solo.sleep(10000);
		//��ȡ��һ���ʼ���¼������
		if(to_subject.equals(TestUtil.getTextOfTextView("mail_subject"))) {
			Log.d(TAG, TestUtil.getTextOfTextView("mail_subject"));
			Log.d(TAG, "���ͳɹ�");
			TestUtil.takescreen("���ͳɹ�");
			assertEquals("���ͳɹ�", "���ͳɹ�");
			saveLog("(2)139���䲦��-ֱ�ӷ����ʼ����� ����ͨ��");
		} else {
			Log.d(TAG, "����ʧ��");
			TestUtil.takescreen("����ʧ��");
			saveLog("(2)139���䲦��-ֱ�ӷ����ʼ����� ���Բ�ͨ��");
			assertEquals("���ͳɹ�", "���Ͳ��ɹ�");
		}
		solo.sleep(2000);
	}
	
	
	
	/**
	 * ת���ʼ�
	 * ���Բ��裺
	 * 1�����ռ����е��һ���ʼ�
	 * 2�������ת������ť
	 * 3�������ռ����˺�
	 * 4����д�ʼ�����
	 * 5����������Ͱ�ť��
	 * Ԥ�ڽ����
	 * 1���ʼ�����Ϊ:Fwd:+ԭ�ʼ�����
	 * 2���ʼ����ͳɹ�
	 * 3����������ڸղ���ת���ɹ����ʼ�
	 */
	public void test3SendMail_FSW() {
		saveLog("��ʼִ��testSendMail_FSW");
		//��������б�
		TestUtil.clickById("actionbar_expansion_view", 3);
		//����ռ���
		solo.clickOnText("�ռ���");
		solo.sleep(2000);
		//�����һ���ʼ�����
		TestUtil.clickById("mail_subject", 3);
		solo.sleep(3000);
		TextView title = (TextView) solo.getView("title");
		if(title == null) {
			solo.sleep(30000);
			title = (TextView) solo.getView("title");
		}
		//��ȡԭ�ʼ�������
		//String origin_subject = TestUtil.getTextOfTextView("title");
		String origin_subject = title.getText().toString();
		Log.d(TAG, "ԭʼ�ʼ������⣺" + origin_subject);
		//�״ν����ʼ���ҳ���ϻ��ڵ���ʾ�������ȵ����
		solo.clickOnText(origin_subject);
		solo.sleep(3000);
		//���ת����ť
		//TestUtil.clickById("actionbar_forward, 3);
		solo.clickOnView(solo.getView("actionbar_forward"));
		solo.sleep(2000);
		solo.hideSoftKeyboard();
		solo.sleep(5000);
		solo.clickOnText("����/����");
		solo.clickOnText("�ռ���");
		//��ȡ�ռ��������
		MultiAutoCompleteTextView tomail = 
				solo.getView(MultiAutoCompleteTextView.class, 0);
		solo.enterText(tomail, to_accound);
		solo.sleep(2000);
		//��ȡת���ʼ�������
		String fwd_subject = TestUtil.getTextOfTextView("subject");
		Log.d(TAG, "ת���ʼ������⣺" + fwd_subject);
		if(fwd_subject.equals("Fwd: " + origin_subject)) {
			assertEquals("ת���ʼ�����Ϊ:Fwd:+ԭ�ʼ�����", "ת���ʼ�����Ϊ:Fwd:+ԭ�ʼ�����");
		} else {
			TestUtil.takescreen("Fwd�ʼ����ⲻ��");
			saveLog("(3)139���䲦��-ת���ʼ����� ���Բ�ͨ��");
			assertEquals("ת���ʼ�����Ϊ:Fwd:+ԭ�ʼ�����", "ת���ʼ����ⲻ��:Fwd:+ԭ�ʼ�����");
		}
		//�����ʼ�
		ImageView sendMail = (ImageView) solo.getView("hjl_headicon", 1);
		solo.clickOnView(sendMail);
		solo.sleep(1000);
		
		solo.goBack();
		TestUtil.clickById("actionbar_expansion_view", 3);
		solo.clickOnText("�ѷ���");
		//solo.sleep(10000);
		Log.d(TAG, "�ѷ��ͣ�" + TestUtil.getTextOfTextView("mail_subject"));
		
		//���ó�ʱ�����
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
			Log.d(TAG, "���ͳɹ�");
			TestUtil.takescreen("ת���ʼ��ɹ�");
			assertEquals("���ͳɹ�", "���ͳɹ�");
			saveLog("(3)139���䲦��-ת���ʼ����� ����ͨ��");
		} else {
			Log.d(TAG, "����ʧ��");
			TestUtil.takescreen("ת���ʼ��ɹ�ʧ��");
			saveLog("(3)139���䲦��-ת���ʼ����� ���Բ�ͨ��");
			assertEquals("���ͳɹ�", "���Ͳ��ɹ�");
		}
		solo.sleep(2000);
	}
	
	/**
	 * ��Ӹ��������ʼ�
	 * ���Բ��裺
	 * 1�������д�ʼ�����ť
	 * 2�������ռ����˺š�����
	 * 3���������Ӹ�������ť���ֱ������Ƭ�������ļ�
	 * 4����д�ʼ����ݣ���������Ͱ�ť��
	 * Ԥ�ڽ����
	 * 1���ʼ����ͳɹ�
	 * 2����������ڸղ������ͳɹ����ʼ�
	 */
	public void test4SendMail_WithAnnex() {
		saveLog("��ʼִ��testSendMail_WithAnnex");
		//��������б�
		TestUtil.clickById("actionbar_expansion_view", 3);
		//����ռ���
		solo.clickOnText("�ռ���");
		solo.sleep(2000);
		//���ռ���ҳ����д�ʼ���ť,���±�1��������������˵���ť
		ImageView editMail = (ImageView) solo.getView("hjl_headicon", 1);
		solo.clickLongOnView(editMail);
		solo.sleep(3000);
		//�ȵ�����ͣ��ٵ���ռ��ˣ��������ܰ�MultiAutoCompleteTextView�ؼ�������
		solo.clickOnText("����/����");
		solo.clickOnText("�ռ���");
		//��ȡ�ռ��������
		MultiAutoCompleteTextView tomail = 
				solo.getView(MultiAutoCompleteTextView.class, 0);
		solo.enterText(tomail, to_accound);
		//��ȡ������
		MultiAutoCompleteTextView ccmail = 
				solo.getView(MultiAutoCompleteTextView.class, 1);
		solo.enterText(ccmail, cc_accound);
		
		//�����Ӹ�����ť
		TestUtil.clickById("add_attachment", 3);
		solo.sleep(2000);
		//������ͼƬ��ť
		TestUtil.clickById("attach_display_photo", 3);
		solo.sleep(2000);
		//�����һ��ͼƬ�ļ���
		TestUtil.clickById("pic_picture", 3);
		solo.sleep(2000);
		//�����һ��ͼƬ
		TestUtil.clickById("pic_picture", 3);
		solo.sleep(2000);
		//�����ӵ��ʼ���ť
		TestUtil.clickById("checkButton", 3);
		solo.sleep(2000);
		
		to_subject = "�Զ���������Ӹ��������ʼ�" + System.currentTimeMillis();
		//�����ʼ�����
		TestUtil.enterEditText("subject", to_subject);
		
		//�����ʼ�����
		to_content = "����һ��������Ĳ����ʼ�";
		TestUtil.enterEditText("editTextField", to_content);
		
		solo.sleep(10000);
		//�����ʼ�
		ImageView sendMail = (ImageView) solo.getView("hjl_headicon", 1);
		solo.clickOnView(sendMail);
		//solo.sleep(10000);
		
		solo.goBack();
		//TestUtil.clickById("actionbar_expansion_view", 3);
		solo.clickOnView(solo.getView("actionbar_expansion_view"));
		solo.sleep(3000);
		solo.clickOnText("�ѷ���");
		//���ó�ʱ�����
		long timeout = System.currentTimeMillis() + 5*60*1000;
		boolean isSuccess = false;
		String temp;
		while(System.currentTimeMillis() < timeout) {
			temp = TestUtil.getTextOfTextView("mail_subject");
			Log.d(TAG, "temp = " + temp);
			//��ȡ��һ���ʼ���¼�����⣬��������������ȥ�������'...'
			if(to_subject.equals(temp) || (temp.length() > 3 &&
					to_subject.startsWith(temp.substring(0, temp.length()-3)))) {
				Log.d(TAG, TestUtil.getTextOfTextView("mail_subject"));
				isSuccess = true;
				break;
			} 
			solo.sleep(15000);
		}
		if(isSuccess) {
			Log.d(TAG, "���ͳɹ�");
			TestUtil.takescreen("���ͳɹ�");
			assertEquals("���ͳɹ�", "���ͳɹ�");
			saveLog("(4)139���䲦��-��Ӹ��������ʼ����� ����ͨ��");
		} else {
			Log.d(TAG, "����ʧ��");
			TestUtil.takescreen("����ʧ��");
			saveLog("(4)139���䲦��-��Ӹ��������ʼ����� ���Բ�ͨ��");
			assertEquals("���ͳɹ�", "���Ͳ��ɹ�");
		}
		solo.sleep(2000);
	}
	
}
