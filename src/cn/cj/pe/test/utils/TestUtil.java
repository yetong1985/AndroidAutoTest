package cn.cj.pe.test.utils;

import java.util.ArrayList;

import cn.cj.pe.test.BasicTestCase;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * ���Թ����еĲ��������࣬��������������
 * @author Administrator
 *
 */
public class TestUtil {
	
	/**
	 * ����ؼ�
	 * @param id �ؼ�id
	 * @param seconds �ȴ�ʱ��(��)
	 */
	public static void clickById(String id, int seconds) {
		try {
			View view = BasicTestCase.solo.getView(id);
			if (null != view && view.isShown()) {
				BasicTestCase.solo.clickOnView(view);
				BasicTestCase.solo.sleep(1000);
			} else {
				BasicTestCase.solo.sleep(seconds * 1000);
				for (View v : BasicTestCase.solo.getCurrentViews()) {
					if (v.getId() == BasicTestCase.solo
							.getCurrentActivity()
							.getResources()
							.getIdentifier(
									id,
									"id",
									BasicTestCase.solo
									        .getCurrentActivity()
											.getPackageName())) {
						BasicTestCase.solo.clickOnView(v);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ����
	 * @param id �ؼ�id
	 * @param seconds �ȴ�ʱ��(��)
	 */
	public static void clickLongById(String id, int seconds) {
		try {
			View view = BasicTestCase.solo.getView(id);
			if (null != view && view.isShown()) {
				BasicTestCase.solo.clickLongOnView(view, seconds);
				BasicTestCase.solo.sleep(1000);
			} else {
				BasicTestCase.solo.sleep(seconds * 1000);
				for (View v : BasicTestCase.solo.getCurrentViews()) {
					if (v.getId() == BasicTestCase.solo
							.getCurrentActivity()
							.getResources()
							.getIdentifier(
									id,
									"id",
									BasicTestCase.solo.getCurrentActivity()
											.getPackageName())) {
						BasicTestCase.solo.clickLongOnView(view);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �ڱ༭���������ı�
	 * @param id �ؼ�id
	 * @param content Ҫ������ı�����
	 */
	public static void enterEditText(String id, String content) {
		EditText editText = (EditText) BasicTestCase.solo.getView(id);
		if (null != editText && editText.isShown()) {
			BasicTestCase.solo.clickOnView(editText);
			BasicTestCase.solo.clearEditText(editText);
			BasicTestCase.solo.enterText(editText, content);
			BasicTestCase.solo.sleep(1000);
		}
	}
	
	/**
	 * ��ȡTextView��ؼ����ı�����
	 * @param id
	 */
	public static String getTextOfTextView(String id) {
		/*
		View view = BasicTestCase.solo.getView(id);
		if(view instanceof TextView) {
			return ((TextView) view).getText().toString();
		}
		return null;
		*/
		return getTextOfTextView(id, 0);
	}
	
	/**
	 * ��ȡTextView��ؼ����ı�����
	 * @param id
	 */
	public static String getTextOfTextView(String id, int index) {
		View view = BasicTestCase.solo.getView(id, index);
		if(view instanceof TextView) {
			return ((TextView) view).getText().toString();
		}
		return null;
	}
	
	/**
	 * ���ͼƬ��ť
	 * @param index
	 */
	public static void clickImageButton(int index) {
		View view = BasicTestCase.solo.getImageButton(index);
		if (null != view && view.isShown()) {
			BasicTestCase.solo.clickOnView(view);
			BasicTestCase.solo.sleep(1000);
		}
	}	
	
	/**
	 * ��ȡ��ǰҳ����ָ��id�Ŀؼ�����
	 * @param id �ؼ�id
	 * @return
	 */
	public static ArrayList<View> getViewsById(String id) {
		ArrayList<View> views = new ArrayList<View>();
		for (View view : BasicTestCase.solo.getCurrentViews()) {
			if (view.getId() == BasicTestCase.solo
					.getCurrentActivity()
					.getResources()
					.getIdentifier(
							id,
							"id",
							BasicTestCase.solo.getCurrentActivity()
									.getPackageName())
					&& view.isShown()) {
				views.add(view);
			}
		}
		return views;
	}
	
	public static void takescreen() {
		BasicTestCase.solo.sleep(1000);
		BasicTestCase.solo.takeScreenshot();
		BasicTestCase.solo.sleep(1000);
	}
	
	/**
	 * ��ͼ
	 * @param name: ͼƬ����
	 */
	public static void takescreen(String name) {
		BasicTestCase.solo.sleep(1000);
		BasicTestCase.solo.takeScreenshot(name);
		BasicTestCase.solo.sleep(1000);
	}
}
