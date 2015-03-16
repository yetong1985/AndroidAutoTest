package cn.cj.pe.test.utils;

import java.util.ArrayList;

import cn.cj.pe.test.BasicTestCase;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 测试过程中的操作工具类，比如点击、长按等
 * @author Administrator
 *
 */
public class TestUtil {
	
	/**
	 * 点击控件
	 * @param id 控件id
	 * @param seconds 等待时间(秒)
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
	 * 长按
	 * @param id 控件id
	 * @param seconds 等待时间(秒)
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
	 * 在编辑框中输入文本
	 * @param id 控件id
	 * @param content 要输入的文本内容
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
	 * 获取TextView类控件的文本内容
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
	 * 获取TextView类控件的文本内容
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
	 * 点击图片按钮
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
	 * 获取当前页面下指定id的控件集合
	 * @param id 控件id
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
	 * 截图
	 * @param name: 图片名称
	 */
	public static void takescreen(String name) {
		BasicTestCase.solo.sleep(1000);
		BasicTestCase.solo.takeScreenshot(name);
		BasicTestCase.solo.sleep(1000);
	}
}
