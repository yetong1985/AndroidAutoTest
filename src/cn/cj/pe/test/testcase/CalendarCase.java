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
 * ����ģ������
 * @author yetong
 *
 */
public class CalendarCase extends BasicTestCase {
	
	private final String TAG = "CalendarCase";
	//�´�������������
	private String newCalendar = "stone����";
	//����˵��
	private String instruction = "�Զ�������";
	
	private String title = "�Զ�������";
	private String content = "Android�Զ���������ѵ";
	
	private AddressBookCase addressBookCase = new AddressBookCase();
	
	/**
	 * ����-���� ����
	 * ���Բ��裺�������������ť
	 * Ԥ�ڽ����������ʾ��ǰ������
	 */
	public void test1Date() {
		saveLog("��ʼִ��test1Date");
		goToCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��");
		//��ȡ��ǰ����
		String date = sdf.format(new Date());
		if(TestUtil.getTextOfTextView("cx_month_title_textview").equals(date)) {
			Log.d(TAG, "������ʾ��ǰ�����£�" + date);
			saveLog("(13)����-������ʾ ��������ͨ��");
			assertEquals("������ʾ��ǰ������", "������ʾ��ǰ������");
		} else {
			TestUtil.takescreen("������������ʾ����");
			saveLog("(13)����-������ʾ �������� ��ͨ��");
			assertEquals("������ʾ��ǰ������", "������ʾ�Ĳ��ǵ�ǰ������");
		}
	}
	
	/**
	 * ����-�ҵ����� ����
	 * ���� ���裺������ҳ�棬������ҵ���������ǩ
	 * Ԥ�ڽ����Ĭ��ѡ��ǰ������
	 */
	public void test2MyCalendar() {
		saveLog("��ʼִ��test2MyCalendar");
		goToCalendar();
		solo.clickOnText("�ҵ�����");
		solo.sleep(1000);
		TextView today = (TextView) solo.getView("cx_btn_today");
		//Ĭ��ѡ��ǰ�����ڣ���cx_btn_today��Ӧ�ó���
		Log.d(TAG, "today.isShown() : " + today.isShown());
		assertFalse(today.isShown());
		SimpleDateFormat sdf = new SimpleDateFormat("MM��dd��");
		//��ȡ��ǰ����
		String date = sdf.format(new Date());
		Log.d(TAG, "date: " + date);
		if(TestUtil.getTextOfTextView("cx_hand_textview").equals(date)) {
			saveLog("(14)����-�ҵ����� ��������ͨ��");
			assertEquals("�ҵ�����Ĭ��ѡ��ǰ������", "�ҵ�����Ĭ��ѡ��ǰ������");
		} else{
			saveLog("(14)����-�ҵ����� �������Բ�ͨ��");
			assertEquals("�ҵ�����Ĭ��ѡ��ǰ������", "�ҵ�����Ĭ��ûѡ��ǰ������");
		}
	}
	
	/**
	 * ����-�������� ����
	 * ���� ���裺������ҳ�棬������������ѡ���ǩ
	 * Ԥ�ڽ����������������������Ĭ��ѡ��ǰ����
	 */
	public void test3BirthdayRemind() {
		saveLog("��ʼִ��test3BirthdayRemind");
		goToCalendar();
		solo.clickOnText("��������");
		solo.sleep(1000);
		TextView today = (TextView) solo.getView("cx_btn_today");
		assertFalse(today.isShown());
		SimpleDateFormat sdf = new SimpleDateFormat("MM��dd��");
		//��ȡ��ǰ����
		String date = sdf.format(new Date());
		Log.d(TAG, "date: " + date);
		if(TestUtil.getTextOfTextView("cx_hand_textview").equals(date)) {
			saveLog("(15)����-�������� ��������ͨ��");
			assertEquals("��������Ĭ��ѡ��ǰ������", "��������Ĭ��ѡ��ǰ������");
		} else{
			saveLog("(15)����-�������� �������Բ�ͨ��");
			assertEquals("��������Ĭ��ѡ��ǰ������", "��������Ĭ��ûѡ��ǰ������");
		}
		solo.clickOnText("�ҵ�����");
	}
	
	/**
	 * ����-������Ϣ
	 * ���������ʱ���ε���robotium�ܼ���֮���������
	 */
	/*
	public void test4CalendarMsg() {
		
		String other_account = addressBookCase.getOtherAccount();
		//ȡ�ֻ��ţ�Ҳ��һ���ı���֤��
		String validation = other_account.substring(0, other_account.indexOf("@"));
		//�˳���ǰ�˺ţ������µ�¼
		if(!quitAccountAndReLogin(validation, addressBookCase.getOtherAccountPSW())) {
			Log.d(TAG, "��¼ʧ��");
			return;
		}

		//��������ҳ��
		goToCalendar();
		solo.clickOnText("�ҵ�����");
		solo.sleep(5000);
		//������񡿰�ť��ʾ������
		TextView today = (TextView) solo.getView("cx_btn_today");
		if(today.isShown()) {
			solo.clickOnView(today);
			solo.sleep(2000);
		}
		
		//����������ť
		Log.d(TAG, "��������");
		solo.clickOnView(solo.getView("cx_create_schedule"));
		solo.waitForText("�����");
		solo.clickOnText("����");
		String title = "����������Ϣ" + System.currentTimeMillis();
		//��������	
		TestUtil.enterEditText("cx_sche_title", title);
		//��������
		TestUtil.enterEditText("cx_sche_content", "������Ѳμӻ������������Ϣ");
		//��ʼʱ��
		TextView start_time = (TextView) solo.getView("cx_start_date");
		//ѡ������
		if(start_time.getText().toString().equals("����")) {
			solo.clickOnView(start_time);
			solo.sleep(2000);
			solo.clickOnText("ȷ��");
			solo.sleep(2000);
			TestUtil.clickById("cx_end_time", 3);
			solo.clickOnText("ȷ��");
		}
		//��������˺�
		TestUtil.enterEditText("cx_invite_edittext", LoginCase._account+"@139.com");
		//ʹ��Ĭ��ʱ�䣬�������
		TestUtil.clickById("cx_title_btn_right", 3);
		assertTrue(solo.searchText("������ɹ�")); 
		solo.sleep(10000);
		//���ص��ռ���
		solo.goBack();
		//�˳��˺Ų����µ�¼
		if(!quitAccountAndReLogin(LoginCase._account, LoginCase._password)) {
			Log.d(TAG, "��¼ʧ��");
			return;
		}
		
		//��������ҳ��
		goToCalendar();
		solo.sleep(20000);
		
		//��ȡ������Ϣ���Ͻǵ�������ʾ
		String msg_count = TestUtil.getTextOfTextView("cx_messages_count");
		if(msg_count == null) {
			Log.d(TAG, "������Ϣû���������ѣ�����ʧ��");
			Log.d(TAG, "����-������Ϣ �������Բ�ͨ��");
			saveLog("(16)����-������Ϣ �������� ͨ��");
			assertEquals("������Ϣ����ͨ��", "������Ϣ���Բ�ͨ��");
			return;
		}
		Log.d(TAG, "msg_count = " + msg_count);
		Log.d(TAG, "������Ϣ��������" + Integer.parseInt(msg_count)); 
		//���������Ϣ
		solo.clickOnView(solo.getView("cx_btn_news"));
		solo.sleep(5000);
		//��Ϣ�����ʽ�������ģ�18664759987����������������������Ϣ��
		//String validation = addressBookCase.getOtherAccount() + "������������" + title + "��"; 
		//��ȡ���е�������Ϣ
		ListView msgList = (ListView) solo.getView("cx_schedule_listview");
		Log.d(TAG, "msgList.getCount : " + msgList.getCount());
		int count = msgList.getCount();
		//count-1����ֹԽ�磬��ȡ����������ʵ�ʵĶ�1
		for(int i = 0; i < count-1; i++) {
			TextView item = (TextView) solo.getView("cx_share_user", i);
			String share_msg = item.getText().toString();
			Log.d(TAG, "share_msg : " + share_msg);
			if(share_msg.indexOf(title) != -1 && 
					share_msg.indexOf(validation) != -1) {
				Log.d(TAG, "�ҵ�ָ����������Ϣ��");
				solo.clickOnView(item);
				solo.sleep(2000);
				Log.d(TAG, "����-������Ϣ �������� ͨ��");
				saveLog("(16)����-������Ϣ �������� ͨ��");
				assertEquals("������Ϣ����ͨ��", "������Ϣ����ͨ��");
				return;
			}
		}
		Log.d(TAG, "����-������Ϣ �������Բ�ͨ��");
		saveLog("(16)����-������Ϣ �������� ͨ��");
		assertEquals("������Ϣ����ͨ��", "������Ϣ���Բ�ͨ��");
	} */
	
	/**
	 * �˳���ǰ�˺ţ����µ�¼
	 * @param account Ҫ���µ�¼���˺�
	 * @param password Ҫ���µ�¼���˺�����
	 */
	private boolean quitAccountAndReLogin(String account, String password) {
		//��������˵���ť
		solo.clickOnView(solo.getView("hjl_headicon", 0));
		solo.sleep(2000);
		//�������
		solo.clickOnText("����");
		solo.sleep(2000);
		//�����ǰ��¼���˺�
		solo.clickOnView(solo.getView("account_name"));
		solo.sleep(2000);
		//����˳��˺�
		TestUtil.clickById("account_logoff", 2);
		solo.waitForDialogToOpen();
		//���ȷ����ť
		TestUtil.clickById("right", 2);
		solo.waitForDialogToClose();
		
		solo.sleep(2000);
		//�����˺š�����
		TestUtil.enterEditText("login_name", account);
		TestUtil.enterEditText("login_password", password);
		TestUtil.clickById("login", 3);
		if(solo.searchText("��¼�ɹ�")) {
			Log.d(TAG, "��¼�ɹ�");
			solo.sleep(10000);
			return true;
		} else if("�ռ���".equals(TestUtil.getTextOfTextView(
				"actionbar_sub_title").substring(0, 3))) {
			Log.d(TAG, "���뵽�ռ���ҳ�棬��¼�ɹ�");
			solo.sleep(10000);
			return true;
		}
		return false;
	}
	
	/**
	 * ����-����ͬ�� ����
	 * ���Բ��裺
	 * 1�����������������Ͻǡ����ࡿ��ť
	 * 2�����������ͬ������ť
	 * Ԥ�ڽ����
	 * 1�������˵�ѡ��
	 * 2��ͬ��web��������Ϣ
	 */
	public void test5SyncCalendar() {
		saveLog("��ʼִ��test5SyncCalendar");
		goToCalendar();
		//������ϽǸ��ఴť
		TestUtil.clickById("cx_btn_more", 3);
		solo.waitForDialogToOpen();
		if(solo.searchText("����ͬ��")) {
			solo.clickOnText("����ͬ��");
			if(solo.searchText("����ͬ���ɹ�")) {
				Log.d(TAG, "����ͬ���ɹ�");
				TestUtil.takescreen("����ͬ���ɹ�");
				saveLog("����ͬ���ɹ�");
				saveLog("(17)����-����ͬ�� �������� ͨ��");
				assertEquals("����ͬ���ɹ�", "����ͬ���ɹ�");
			} else {
				Log.d(TAG, "����ͬ��ʧ��");
				TestUtil.takescreen("����ͬ��ʧ��");
				saveLog("����ͬ��ʧ��");
				saveLog("(17)����-����ͬ�� �������� ��ͨ��");
				assertEquals("����ͬ���ɹ�", "����ͬ��ʧ��");
			}
		} else {
			Log.d(TAG, "û���ҵ� ����ͬ����ť");
			saveLog("û���ҵ� ����ͬ����ť");
			saveLog("(17)����-����ͬ�� �������� ��ͨ��");
			assertEquals("����ͬ��", "û���ҵ� ����ͬ����ť");
			return;
		}
	}
	
	/**
	 * ����-��������
	 * ���Բ��裺
	 * 1�����������������Ͻǡ����ࡿ��ť
	 * 2�������������������ť
	 * 3�����������������
	 * 4�������������ݵ�������桿��ť
	 * Ԥ�ڽ����
	 * 1�����Դ�������
	 * 2�������õ��������ҵ���������ʾ
	 * 3��������������ɾ��
	 */
	public void test6ManageCalendar() {
		saveLog("��ʼִ��test6ManageCalendar");
		goToCalendar();
		//������ϽǸ��ఴť
		TestUtil.clickById("cx_btn_more", 3);
		solo.waitForDialogToOpen();
		if(! solo.searchText("��������")) {
			Log.d(TAG, "û���ҵ� ��������������ť");
			saveLog("û���ҵ� ����������ť");
			saveLog("(18)����-�������� �������� ��ͨ��");
			assertEquals("��������", "û���ҵ� ����������ť");
			return;
		} 
		solo.clickOnText("��������");
		solo.sleep(2000);

		if(solo.searchText(newCalendar)) {
			Log.d(TAG, "�Ѵ�����Ϊ'" + newCalendar + "'��������Ҫ��ɾ����");
			solo.clickOnText(newCalendar);	
			assertTrue(delCalendar(newCalendar));
		}
		
		//�����������
		TestUtil.clickById("cx_create_label", 3);
		solo.sleep(3000);
		assertEquals("��������", 
				TestUtil.getTextOfTextView("cx_title_textview"));

		//������������
		TestUtil.enterEditText("cx_label_name", newCalendar);
		TestUtil.enterEditText("cx_label_instruction", instruction);
		solo.sleep(2000);
		//������水ť
		TestUtil.clickById("cx_title_btn_right", 3);
		if(!solo.searchText("���������ɹ�")) {
			Log.d(TAG, "��������ʧ��");
			saveLog("��������ʧ��");
			saveLog("(18)����-�������� �������� ��ͨ��");
			assertEquals("���������ɹ�", "��������ʧ��");
			return;
		}
		Log.d(TAG, "���������ɹ�");
		//�ڹ�������ҳ�ҵ��½�������
		if(TestUtil.getTextOfTextView("cx_title_textview").equals("��������")
				&& solo.searchText(newCalendar)) {
			Log.d(TAG, "���������" + newCalendar);
			solo.clickOnText(newCalendar);
			solo.sleep(2000);
			assertEquals(instruction, 
					TestUtil.getTextOfTextView("cx_label_instruction"));
			saveLog("(18)����-�������� �������� ͨ��");
		} else {
			Log.d(TAG, "�ڹ�������ҳû�ҵ��½�������");
			saveLog("�ڹ�������ҳû�ҵ��½�����������������ʧ��");
			saveLog("(18)����-�������� �������� ��ͨ��");
			assertEquals("���������ɹ�", "��������ʧ��");
			return;
		} 
	}
	
	/**
	 * ����-�����
	 * ���Բ��裺
	 * 1�����ҵ�����ҳ�����½����ť
	 * 2���������ݵ������
	 * Ԥ�ڽ����
	 * 1����ʾ��������ɹ�
	 * 2��������ҳ�������б�ʶ
	 */
	public void test7CreateActivity() {
		saveLog("��ʼִ��test7CreateActivity");
		goToCalendar();
		solo.clickOnText("�ҵ�����");
		if(solo.searchText("����ͬ���ɹ�")) {
			Log.d(TAG, "����ͬ���ɹ�");
		} else {
			if(solo.waitForText("����ͬ���ɹ�", 1, 120000, false, true)) {
				Log.d(TAG, "�ȴ�����ͬ���ɹ�");
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
		//�����Ѿ����ڣ�������ɾ��
		if(list.size() > 0
				&& title.equals(((TextView) list.get(0)).getText())) {
			Log.d(TAG, title + " ��Ѿ����ڣ��Ȱ���ɾ��");
			solo.clickOnView(list.get(0));
			solo.sleep(5000);
			//���ɾ����ť
			TestUtil.clickById("cx_btn_del", 3);
			//���ȷ��
			TestUtil.clickById("cx_right", 3);
			if(solo.searchText("ɾ���ɹ�")) {
				Log.d(TAG, "ɾ����ɹ�");
			} else {
				Log.d(TAG, "ɾ���ʧ��");
			}
			solo.sleep(6000);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("MM��dd��");
		//��ȡ��ǰ����
		String date = sdf.format(new Date());
		//����������ť
		Log.d(TAG, "��������");
		TestUtil.clickById("cx_create_schedule", 3);
		solo.waitForText("�����");
		solo.clickOnText("����");
		//��������
		list = TestUtil.getViewsById("cx_sche_title");
		Log.d(TAG, "list.size: " + list.size());
		solo.enterText((EditText) list.get(0), title);

		//��������
		TestUtil.enterEditText("cx_sche_content", content);
		//��ʼʱ��
		TextView start_time = (TextView) solo.getView("cx_start_date");
		//ѡ������
		if(start_time.getText().toString().equals("����")) {
			solo.clickOnView(start_time);
			solo.sleep(2000);
			solo.clickOnText("ȷ��");
			solo.sleep(2000);
			TestUtil.clickById("cx_end_time", 3);
			solo.clickOnText("ȷ��");
		}
		//ʹ��Ĭ��ʱ�䣬�������
		TestUtil.clickById("cx_title_btn_right", 3);
		assertTrue(solo.searchText("������ɹ�")); 
		list = TestUtil.getViewsById("cx_sche_title");
		Log.d(TAG, "����⣺ " + ((TextView) list.get(0)).getText());
		if(((TextView) list.get(0)).getText().equals(title)
				&& TestUtil.getTextOfTextView("cx_hand_textview").equals(date)) {
			assertEquals("������ҳ�������б�ʶ", "������ҳ�������б�ʶ");
			saveLog("(19)����-����� �������� ͨ��");
			TestUtil.takescreen("����-�����");
		} else {
			assertEquals("������ҳ�������б�ʶ", "������ҳ������û�б�ʶ");
			saveLog("(19)����-����� �������� ��ͨ��");
			TestUtil.takescreen("����-����� ʧ��");
		}
	}
	
	/**
	 * ��������ҳ��
	 */
	private void goToCalendar() {
		solo.sleep(3000);
		//��������˵���ť
		ImageView sideBarMenu = (ImageView) solo.getView("hjl_headicon", 0);
		solo.clickOnView(sideBarMenu);
		solo.clickOnText("����");
		solo.sleep(2000);
	}
	
	private boolean delCalendar(String calendarName) {
		solo.clickOnText(calendarName);
		solo.sleep(2000);
		//����������ҳ���ɾ����ť
		TestUtil.clickById("cx_title_btn_right", 3);
		solo.waitForDialogToOpen();
		//���ȷ����ť
		TestUtil.clickById("cx_right", 3);
		solo.sleep(2000);
		if(solo.searchText(calendarName)) {
			Log.d(TAG, "ɾ������ʧ��");
			return false;
		}
		return true;
	}
}
