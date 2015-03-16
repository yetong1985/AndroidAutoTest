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
 * �������úͻ���
 * @author Administrator
 *
 */
public class SettingCase extends BasicTestCase {
	
	private final String TAG = "SettingCase";
	private String oldName = "Ҷ��ʦ";
	private String newName = "Ҷ����";
	private String version = "V6.0.1";
	//���洰�ڴ�С
	private DisplayMetrics dm;
	private AddressBookCase addressBookCase = new AddressBookCase();
	//Ҫ�����ʼ��ĵ�ַ
	private String to_accound = "15989228271@139.com";
	//�����ʼ���Դ�˻�
	private String fromAccount = null;
	
	//private Uri SMS_INBOX = Uri.parse("content://sms/");
	//��ѯ������
	private Uri SMS_INBOX = Uri.parse("content://sms/inbox");
	
	/**
	 * ����-�˺�����
	 * ���Բ��裺
	 * 1����������á���ť
	 * 2����������˺Ž�������
	 * 3����������������������µ�����
	 * 4����������桿��ť
	 * Ԥ�ڽ����
	 * 1���޸ĳɹ�
	 * 2������������Ϊ���޸ĵ�����
	 */
	public void test1AccountSetting() {
		saveLog("��ʼִ��test1AccountSetting");
		goToSetting();
		int flag = 0;
		//�����һ���˺�
		TestUtil.clickById("account_name", 3);
		solo.sleep(2000);
		solo.clickOnText("����������");
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
		//������水ť
		solo.clickOnView(solo.getView("hjl_headicon", 1));
		assertTrue(solo.searchText("�޸ĳɹ�", true));
		Log.d(TAG, "�������޸ĳɹ�");
		saveLog("�����������޸ĳɹ�");
		solo.sleep(2000);
		String _name = TestUtil.getTextOfTextView("rightTextView", 6);
		Log.d(TAG, "_name = " + _name);
		if(1 == flag) {
			assertTrue(_name.equals(newName));
		} else if(2 == flag) {
			assertTrue(_name.equals(oldName));
		}
		saveLog("����������Ϊ���޸ĵ�����");
		saveLog("(25)����-�˺����� ����ͨ��");
	}
	
	/**
	 * ����-�ʼ�����
	 * Ԥ��������
	 * ���ʼ��������֪ͨ����ťĬ��Ϊ�ر�״̬
	 * ���Բ��裺
	 * 1�����ʼ������е�����������á���ť
	 * 2���򿪡��ʼ��������֪ͨ����ť
	 * 3��������ذ�ť
	 * Ԥ�ڽ����
	 * ���ʼ�������ֻ��յ�����֪ͨ
	 */
	public void test2MailSetting() {
		saveLog("��ʼִ��test2MailSetting");
		goToSetting();
		solo.clickOnText("��������");
		solo.sleep(2000);
		//����򿪡��ʼ��������֪ͨ��
		TestUtil.clickById("is_notify_sms", 2);
		solo.sleep(5000);
		solo.goBack();
		solo.goBack();
		//��������˵���ť
		solo.clickOnView(solo.getView("hjl_headicon", 0));
		solo.sleep(3000);
		//��ȡ�󶨵ĵڶ����˻�
		String otherAccount = addressBookCase.getOtherAccount();
		Log.d(TAG, "otherAccount = " + otherAccount);

		//��������˵���ť
		//solo.clickOnView( solo.getView("hjl_headicon", 0));
		if(solo.searchText(otherAccount)) {
			solo.clickOnText(otherAccount);
			Log.d(TAG, "���" + otherAccount);
			fromAccount = otherAccount;
		} else {
			solo.clickOnText(to_accound);
			Log.d(TAG, "���" + to_accound);
			fromAccount = to_accound;
		}
		
		//��ȡԴ�˻����ֻ����룬������֤����
		String fromNum = fromAccount.substring(0, fromAccount.indexOf("@"));
		Log.d(TAG, "fromNum = " + fromNum);
		
		solo.sleep(3000);
		String to_subject = "�����ʼ��������֪ͨ" +  System.currentTimeMillis();
		String to_content = "����򿪡��ʼ��������֪ͨ����ť���ʼ�����ᷢ�Ͷ���֪ͨ";
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
		//�����ʼ�����
		TestUtil.enterEditText("subject", to_subject);
		TestUtil.enterEditText("editTextField", to_content);
		solo.sleep(3000);
		//�����ʼ���ʱ��
		long sendTime = System.currentTimeMillis();
		//�����ʼ�
		ImageView sendMail = (ImageView) solo.getView("hjl_headicon", 1);
		solo.clickOnView(sendMail);
		
		solo.goBack();
		TestUtil.clickById("actionbar_expansion_view", 3);
		solo.clickOnText("�ѷ���");
		//���ó�ʱ������
		long timeout = System.currentTimeMillis() + 3*60*1000;
		boolean isSuccess = false;
		String temp;
		while(System.currentTimeMillis() < timeout) {
			temp = TestUtil.getTextOfTextView("mail_subject");
			Log.d(TAG, "temp = " + temp);
			//��ȡ��һ���ʼ���¼�����⣬��������������ȥ�������'...'
			if(to_subject.equals(temp) || 
					to_subject.startsWith(temp.substring(0, temp.length()-3))) {
				Log.d(TAG, TestUtil.getTextOfTextView("mail_subject"));
				isSuccess = true;
				break;
			} 
			solo.sleep(5000);
		}
		if(isSuccess == false) {
			Log.d(TAG, "�ʼ�����ʧ�ܣ��˳��ò�������");
			assertEquals("����ͨ��", "����ʧ��");
			saveLog("(26)����-�ʼ����� ���Բ�ͨ��");
			return;
		} 
		
		boolean flag = false;
		timeout = System.currentTimeMillis() + 3*60*1000;
		while(System.currentTimeMillis() < timeout) {
			Cursor cursor = getSmsFromPhone(sendTime);
			if(cursor == null) {
				Log.d(TAG, "��û���յ��ʼ�����Ķ���");
				continue;
			} 
			//�����ƶ�һ�¹��  
			while(cursor.moveToNext()) {
				
				Log.d(TAG, "address������: " + cursor.getColumnIndex("address"));
				//��ȡ�����˵�ַ
				String number = cursor.getString(cursor.getColumnIndex("address"));
				Log.d(TAG, "body������: " + cursor.getColumnIndex("body"));
				//��ȡ��������
				String body = cursor.getString(cursor.getColumnIndex("body"));
				Log.d(TAG, "�����˵�ַ��" + number);
				Log.d(TAG, "�������ݣ�" + body);
				/*
				//ƥ���ʼ����ſ�ʼ�ġ���������ֻ����룬Ҳ���Ƿ������ֻ���
				Pattern pattern = Pattern.compile("\\[([0-9]+)\\]");
				Matcher matcher = pattern.matcher(body);
				//��ȡ�����еķ������ֻ�����
				String verify_num = matcher.group(1);
				Log.d(TAG, "verify_num = " + verify_num);				
				
				//ƥ���ʼ�����
				pattern = Pattern.compile("\\](.+)");
				matcher = pattern.matcher(body);
				//��ȡ�����е��ʼ�����
				String verify_subject = matcher.group(1);
				Log.d(TAG, "verify_subject = " + verify_subject);
				*/
				
				if(body.startsWith("[" + fromNum + "]���⣺" + to_subject)) {
					Log.d(TAG, "�յ��ʼ�����Ķ�����");
				//	assertTrue("�����е����ⲻ��", body.indexOf(to_subject) > 0);
					assertEquals("����ͨ��", "����ͨ��");
					Log.d(TAG, "(26)����-�ʼ����� ����ͨ��");
					saveLog("(26)����-�ʼ����� ����ͨ��");
					flag = true;
					break;
				}
			}

			if(flag) {
				//�ر��α�
				cursor.close();
				break;
			}
			solo.sleep(10000);
		}
		
		goToSetting();
		solo.clickOnText("��������");
		solo.sleep(2000);
		//����رա��ʼ��������֪ͨ��
		TestUtil.clickById("is_notify_sms", 2);
		solo.sleep(3000);
		
		if(!flag) {
			assertEquals("����ͨ��", "����ʧ��");
			saveLog("(26)����-�ʼ����� ���Բ�ͨ��");
		}
		
	}
	
	/**
	 * ����-��������
	 * Ԥ���������ѿ��������ȡ�ֻ���ϵ�˿���
	 * ���Բ��裺
	 * 1���ڹ��������йرա������ȡ�ֻ���ϵ�ˡ�
	 * 2������ͨѶ¼ҳ�棬�����ȫ������ǩ������ˢ��
	 * Ԥ�ڽ����
	 * 1���رճɹ�����ʾ����ͨѶ¼�н�ˢ��ͬ��139������ϵ�ˡ�
	 * 2����ʾ��139����ͨѶ¼ͬ�����
	 */
	public void test3FunctionSetting() {
		saveLog("��ʼִ��test3FunctionSetting");
		goToSetting();
		//Ҫ����˻�����ʶ���������
		solo.clickOnText("�����ȡ�ֻ���ϵ��");
		if(solo.searchText("��ͨѶ¼�н�ˢ��ͬ��139������ϵ��")) {
			Log.d(TAG, "�رճɹ�����ʾ����ͨѶ¼�н�ˢ��ͬ��139������ϵ�ˡ�");
			Log.d(TAG, "�رճɹ�����ʾ����ͨѶ¼�н�ˢ��ͬ��139������ϵ�ˡ�");
			saveLog("�رճɹ�����ʾ����ͨѶ¼�н�ˢ��ͬ��139������ϵ�ˡ�");
			solo.sleep(2000);
		} else {
			Log.d(TAG, "�������ȡ�ֻ���ϵ�ˡ������Ѿ����������ڹر���");
			saveLog("�ڹ��������йرա������ȡ�ֻ���ϵ�ˡ�");
			solo.clickOnText("�����ȡ�ֻ���ϵ��");
			assertTrue(solo.searchText("��ͨѶ¼�н�ˢ��ͬ��139������ϵ��"));
			saveLog("�رճɹ�����ʾ����ͨѶ¼�н�ˢ��ͬ��139������ϵ�ˡ�");
		}
		
		solo.goBack();
		//��������˵���ť
		ImageView sideBarMenu = (ImageView) solo.getView("hjl_headicon", 0);
		solo.clickOnView(sideBarMenu);
		solo.clickOnText("ͨѶ¼");
		solo.sleep(2000);
		
		//���ȫ��
		solo.clickOnText("ȫ��");
		
		dm = new DisplayMetrics();
		boolean isSuccess = false;
		//��ȡ��ǰ���ڴ�С
		solo.getCurrentActivity().getWindowManager()
		    .getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;
		int height = dm.heightPixels;
		solo.drag((width / 2), (width / 2), (height / 2),
				(height - 20), 50);
		//���ó�ʱʱ��Ϊ2����
		long timeout = 120000;
		long now = System.currentTimeMillis();
		while(System.currentTimeMillis() < now + timeout) {
			if(solo.searchText("139����ͨѶ¼ͬ�����")) {
				Log.d(TAG, "139����ͨѶ¼ͬ�����");
				saveLog("139����ͨѶ¼,�ֻ���ϵ��ͬ�����");
				isSuccess = true;
				break;
			}
		}
		assertTrue(isSuccess);
		saveLog("��ʾ��139����ͨѶ¼ͬ�����");
		saveLog("(27)����-�������� ����ͨ��");
	}
	
	/**
	 * ����-����
	 * ���Բ��裺��������ڡ���ť
	 * Ԥ�ڽ������ʾ139���估��ǰ�İ汾��
	 */
	public void test4OtherSetting() {
		saveLog("��ʼִ��test4OtherSetting");
		goToSetting();
		solo.clickOnText("����");
		solo.sleep(3000);
		if(!solo.searchText("139����")) {
			solo.sleep(30000);
		}
		assertTrue(solo.searchText("139����", true));
		saveLog("��ʾ139����");
		assertEquals(version, TestUtil.getTextOfTextView("version"));
		saveLog("����ǰ�İ汾��");
		saveLog("(28)����-���� ����ͨ��");
	}
	
	/**
	 * ����
	 * ���Բ��裺�������������ť��ѡ��һ����ɫ
	 * Ԥ�ڽ��������ı�����ʾΪ��ѡ�����ɫ
	 */
	public void test5ChangeSkin() {
		saveLog("��ʼִ��test5ChangeSkin");
		ImageView sideBarMenu = (ImageView) solo.getView("hjl_headicon", 0);
		solo.clickOnView(sideBarMenu);
		TestUtil.clickById("skin_menu_click", 3);
		//ѡ����ɫ����
		TestUtil.clickById("skin_blue", 3);
		TestUtil.takescreen("��ɫ����");
		solo.sleep(2000);
		TestUtil.clickById("skin_menu_click", 3);
		//ѡ����ɫ����
		TestUtil.clickById("skin_red", 3);
		TestUtil.takescreen("��ɫ����");
		saveLog("(29)����-���� ����ͨ��");
		solo.sleep(2000);
	}
	
	/**
	 * ��������ҳ��
	 */
	private void goToSetting() {
		solo.sleep(3000);
		//��������˵���ť
		ImageView sideBarMenu = (ImageView) solo.getView("hjl_headicon", 0);
		solo.clickOnView(sideBarMenu);
		solo.clickOnText("����");
		solo.sleep(2000);
	}
	
	/**
	 * ��ȡ�ֻ�����sinceTimeʱ���Ķ�������
	 * @param sinceTime
	 */
	private Cursor getSmsFromPhone(long sinceTime) {
		ContentResolver cr = getActivity().getContentResolver();
		String[] projection = new String[] { "address", "body" };
		Log.d(TAG, "��ѯ����");
		//139�·��Ķ������� ��10658139���� ��ͷ ���˺ŷ��͵�
		String where = " address like '10658139%' AND date > " + sinceTime;
		//��ʱ�併���ȡ���
		Cursor cur = cr.query(SMS_INBOX, projection, where, null, "date desc");
		Log.d(TAG, "cur: " +cur.toString());
		return cur;
	}
}
