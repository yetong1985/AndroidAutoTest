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
 * ������������ͨѶ¼
 * @author yetong
 *
 */
public class AddressBookCase extends BasicTestCase {
	
	private final String TAG = "AddressBookCase";
	//���洰�ڴ�С
	private DisplayMetrics dm;
	//web��ͨѶ¼��ϵ��
	private String webContact = "webAddress@qq.com";
	//Ҫ��ӵ��˺�
	private String other_Account = "18664759987@139.com";
	private String other_psw = "chinasoft";
	
	/**
	 * �����������
	 */
	public void testAddAccount() {
		saveLog("��ʼִ��testAddAccount");
		solo.sleep(3000);
		//��������˵���ť
		ImageView sideBarMenu = (ImageView) solo.getView("hjl_headicon", 0);
		solo.clickOnView(sideBarMenu);
		solo.sleep(2000);
		if(solo.searchText(other_Account)) {
			Log.d(TAG, "Ҫ��ӵ��˻��Ѵ��ڣ������ȰѸ��˻�ע��");
			cancelTheAddedAcount();
			solo.clickOnView(solo.getView("hjl_headicon", 0));
			solo.sleep(2000);
		}
		//���������䰴ť
		TestUtil.clickById("menu_add_account", 3);
		solo.sleep(2000);
		TestUtil.enterEditText("add_account_email", other_Account);
		TestUtil.enterEditText("add_account_password", other_psw);
		solo.sleep(2000);
		//������水ť
		solo.clickOnView(solo.getView("hjl_headicon", 1));
		//���ó�ʱʱ��Ϊ2����
		long timeout = 120000;
		long now = System.currentTimeMillis();
		while(System.currentTimeMillis() < now + timeout) {
			if(solo.searchText("��¼�ɹ�")) {
				Log.d(TAG, "��ȡ����¼�ɹ�����ʾ");
				saveLog("�ɹ��������");
				break;
			} else if(solo.searchText("����ǩ��")) {
				Log.d(TAG, "�ѳɹ��������");
				saveLog("�ѳɹ��������");
				break;
			} 
		}
		solo.goBack();
		
		if(solo.searchText(other_Account)) {
			Log.d(TAG, "�ҵ�����ӵ������˻���");
			solo.takeScreenshot("�ɹ��������");
			saveLog("(8)������� ��������ͨ��");
			assertEquals("�������ɹ�", "�������ɹ�");
		} else {
			Log.d(TAG, "û�ҵ�����ӵ������˻�");
			solo.takeScreenshot("�������ʧ��");
			saveLog("(8)������� �������Բ�ͨ��");
			assertEquals("�������ɹ�", "�������ʧ��");	
		} 
		
		solo.sleep(3000);
		solo.clickOnText(LoginCase._account);
	}
	/**
	 * ����ͨѶ¼-ȫ��
	 */
	public void testAllAddressBook() {
		saveLog("��ʼִ��testAllAddressBook");
		setReadingPhoneContacts();
		goToAddressBook();
		//���ȫ��
		solo.clickOnText("ȫ��");
		
		dm = new DisplayMetrics();
		//��ȡ��ǰ���ڴ�С
		solo.getCurrentActivity().getWindowManager()
		    .getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;
		int height = dm.heightPixels;
		solo.drag((width / 2), (width / 2), (height / 2),
				(height - 20), 50);
		//���ó�ʱʱ��Ϊ5����
		long timeout = 300000;
		long now = System.currentTimeMillis();
		while(System.currentTimeMillis() < now + timeout) {
			if(solo.searchText("139����ͨѶ¼,�ֻ���ϵ��ͬ�����")) {
				Log.d(TAG, "139����ͨѶ¼,�ֻ���ϵ��ͬ�����");
				saveLog("139����ͨѶ¼,�ֻ���ϵ��ͬ�����");
				break;
			}
		}
		if(solo.searchText(webContact)) {
			assertEquals("ͨѶ¼ȫ��", "ͨѶ¼ȫ��");
			saveLog("(9)ͨѶ¼-ȫ�� ��������ͨ��");
		} else {
			saveLog("(9)ͨѶ¼-ȫ�� �������Բ�ͨ��");
			assertEquals("ͨѶ¼ȫ��", "ͨѶ¼ûͬ��");
		}
		
	}
	
	/**
	 * ����ͨѶ¼-����
	 */
	public void testGroupAddressBook() {
		saveLog("��ʼִ��testGroupAddressBook");
		goToAddressBook();
		//�������
		solo.clickOnText("����");
		solo.sleep(2000);
		ArrayList<View> groups = TestUtil.getViewsById("group_name");
		Log.d(TAG, "groups.size: " + groups.size());
		if(groups.size() > 0 && solo.searchText("������ϵ��")) {
			TestUtil.takescreen("ͨѶ¼����");
			assertEquals("ͨѶ¼����", "ͨѶ¼����");
			saveLog("(10)ͨѶ¼-���� ��������ͨ��");
		} else {
			TestUtil.takescreen("ͨѶ¼����ʧ��");
			saveLog("(10)ͨѶ¼-���� �������Բ�ͨ��");
			assertEquals("ͨѶ¼����", "ͨѶ¼����ʧ��");
		}
	}
	
	/**
	 * ��������ͨѶҳ��
	 */
	private void goToAddressBook() {
		solo.sleep(3000);
		//��������˵���ť
		ImageView sideBarMenu = (ImageView) solo.getView("hjl_headicon", 0);
		solo.clickOnView(sideBarMenu);
		solo.clickOnText("ͨѶ¼");
		solo.sleep(2000);
	}
	
	/**
	 * ���ÿ��������ȡ�ֻ���ϵ�˰�ť
	 */
	private void setReadingPhoneContacts() {
		solo.sleep(3000);
		//��������˵���ť
		ImageView sideBarMenu = (ImageView) solo.getView("hjl_headicon", 0);
		solo.clickOnView(sideBarMenu);
		solo.clickOnText("����");
		solo.sleep(2000);
		solo.clickOnText("�����ȡ�ֻ���ϵ��");
		if(solo.searchText("��ͨѶ¼�п�ˢ��ͬ���ֻ���ϵ��")) {
			Log.d(TAG, "�ѿ��������ȡ�ֻ���ϵ�˰�ť");
			saveLog("�ѿ��������ȡ�ֻ���ϵ�˰�ť");
		} else {
			Log.d(TAG, "��δ���������ȡ�ֻ���ϵ�˰�ť�����ڴ򿪿���");
			saveLog("��δ���������ȡ�ֻ���ϵ�˰�ť�����ڴ򿪿���");
			solo.clickOnText("�����ȡ�ֻ���ϵ��");
			solo.sleep(2000);
		}
		solo.goBack();
	}
	
	/*
	 * ע������ӵ��˻�
	 */
	private void cancelTheAddedAcount() {
		solo.sleep(5000);
		solo.clickOnText("����");
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
		//solo.clickOnText("ע���ʺ�");
		TestUtil.clickById("account_logoff", 3);
		solo.waitForDialogToOpen();
		solo.clickOnText("ȷ��");
		solo.waitForDialogToClose();
		if(solo.searchText("�ɹ�ע��")) {
			Log.d(TAG, "�ɹ�ע��" + other_Account);
			saveLog("�ɹ�ע��" + other_Account);
		} else {
			Log.d(TAG, "ע��" + other_Account +"ʧ��");
			saveLog("ע��" + other_Account +"ʧ��");
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
