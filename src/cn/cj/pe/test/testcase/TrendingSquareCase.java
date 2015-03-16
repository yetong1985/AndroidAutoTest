package cn.cj.pe.test.testcase;

import java.util.ArrayList;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import cn.cj.pe.test.BasicTestCase;
import cn.cj.pe.test.utils.TestUtil;

/**
 * �������Ź㳡
 * @author yetong
 *
 */
public class TrendingSquareCase extends BasicTestCase {
	
	private final String TAG = "TrendingSquareCase";
	
	/**
	 * �������Ź㳡-���ʻ
	 */
	public void testExcitingActivities() {
		saveLog("��ʼִ��testExcitingActivities");
		goToTrendingSquare();
		solo.clickOnText("���ʻ");
		//solo.sleep(5000);
		View progressBarView = solo.getView("bar");
		Log.d(TAG, "progressBarView.isShown: " + progressBarView.isShown());
		boolean isLoadCompleted = false;
		long timeOut = System.currentTimeMillis() + 3*60*1000;
		while(System.currentTimeMillis() < timeOut) {
			if(progressBarView.isShown() == false) {
				Log.d(TAG, "���ʻҳ���Ѽ������");
				isLoadCompleted = true;
				break;
			}
		}
		if(!isLoadCompleted) {
			Log.d(TAG, "���Ź㳡ҳ����س�ʱ");
			TestUtil.takescreen("���Ź㳡ҳ����س�ʱ");
			saveLog("���Ź㳡ҳ����س�ʱ��(23)���Ź㳡-���ʻ�������Բ�ͨ��");
			assertEquals("���Ź㳡�", "���Ź㳡û�л");
		}
		//��ȡ���еĻ����
		ArrayList<View> list = TestUtil.getViewsById("title");
		Log.d(TAG, "list.size: " + list.size());
		if(list.size() == 1) {
			Log.d(TAG, "���Ź㳡û�л");
			TestUtil.takescreen("���Ź㳡û�л");
			assertEquals("���Ź㳡û�л", "���Ź㳡û�л");
			saveLog("(23)���Ź㳡-���ʻ��������ͨ��");
			return;
		}
		//�����һ���
		solo.clickOnView(list.get(1));
		solo.waitForDialogToClose();
		TestUtil.takescreen("��ת�������ҳ��");
		Log.d(TAG, "�ı�: " + TestUtil
				.getTextOfTextView("hjl_icon_local_contact_title_name"));
		if("���ʻ".equals(TestUtil
				.getTextOfTextView("hjl_icon_local_contact_title_name"))) {
			assertEquals("���Ź㳡�", "���Ź㳡�");
			saveLog("(23)���Ź㳡-���ʻ��������ͨ��");
		} else {
			saveLog("(23)���Ź㳡-���ʻ�������Բ�ͨ��");
			assertEquals("���Ź㳡�", "���Ź㳡û�л");
		}
	}
	
	/**
	 * �������Ź㳡-��ѯ����
	 */
	public void testInformation() {
		saveLog("��ʼִ��testInformation");
		goToTrendingSquare();
		solo.clickOnText("��Ѷ����");
		//��ȡ��������Ѷ����TextView
		TextView backgoundtext = (TextView) solo.getView("list_backgound_text");
		Log.d(TAG, "backgoundtext.isShown: " + backgoundtext.isShown());
		//��ȡ���е���Ѷ�������
		ArrayList<View> infoList = TestUtil.getViewsById("title");
		Log.d(TAG, "infoList.size: " + infoList.size());
		//���û����Ѷ���棬������ʾ��������Ѷ��
		if(infoList.size() == 1) {
			Log.d(TAG, "���Ź㳡������Ѷ");
			TestUtil.takescreen("���Ź㳡������Ѷ");
			assertTrue(backgoundtext.isShown());
			saveLog("(24)���Ź㳡-��Ѷ������������ͨ��");
			return;
		}
		//�����һ����Ѷ
		solo.clickOnView(infoList.get(1));
		solo.waitForDialogToClose();
		TestUtil.takescreen("��ת����Ѷ����ҳ��");
		if("��Ѷ����".equals(TestUtil
				.getTextOfTextView("hjl_icon_local_contact_title_name"))) {
			assertEquals("���Ź㳡��Ѷ����", "���Ź㳡��Ѷ����");
			saveLog("(24)���Ź㳡-��Ѷ������������ͨ��");
		} else {
			saveLog("(24)���Ź㳡-��Ѷ�����������Բ�ͨ��");
			assertEquals("���Ź㳡��Ѷ����", "���Ź㳡û����Ѷ");
		}
	}
	
	/**
	 * �������Ź㳡ҳ��
	 */
	private void goToTrendingSquare() {
		solo.sleep(3000);
		//��������˵���ť
		ImageView sideBarMenu = (ImageView) solo.getView("hjl_headicon", 0);
		solo.clickOnView(sideBarMenu);
		solo.clickOnText("���Ź㳡");
		solo.sleep(2000);
	}
}
