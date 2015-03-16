package cn.cj.pe.test.testcase;

import java.util.ArrayList;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import cn.cj.pe.test.BasicTestCase;
import cn.cj.pe.test.utils.TestUtil;

/**
 * 测试热门广场
 * @author yetong
 *
 */
public class TrendingSquareCase extends BasicTestCase {
	
	private final String TAG = "TrendingSquareCase";
	
	/**
	 * 测试热门广场-精彩活动
	 */
	public void testExcitingActivities() {
		saveLog("开始执行testExcitingActivities");
		goToTrendingSquare();
		solo.clickOnText("精彩活动");
		//solo.sleep(5000);
		View progressBarView = solo.getView("bar");
		Log.d(TAG, "progressBarView.isShown: " + progressBarView.isShown());
		boolean isLoadCompleted = false;
		long timeOut = System.currentTimeMillis() + 3*60*1000;
		while(System.currentTimeMillis() < timeOut) {
			if(progressBarView.isShown() == false) {
				Log.d(TAG, "精彩活动页面已加载完毕");
				isLoadCompleted = true;
				break;
			}
		}
		if(!isLoadCompleted) {
			Log.d(TAG, "热门广场页面加载超时");
			TestUtil.takescreen("热门广场页面加载超时");
			saveLog("热门广场页面加载超时，(23)热门广场-精彩活动用例测试不通过");
			assertEquals("热门广场活动", "热门广场没有活动");
		}
		//获取所有的活动标题
		ArrayList<View> list = TestUtil.getViewsById("title");
		Log.d(TAG, "list.size: " + list.size());
		if(list.size() == 1) {
			Log.d(TAG, "热门广场没有活动");
			TestUtil.takescreen("热门广场没有活动");
			assertEquals("热门广场没有活动", "热门广场没有活动");
			saveLog("(23)热门广场-精彩活动用例测试通过");
			return;
		}
		//点击第一条活动
		solo.clickOnView(list.get(1));
		solo.waitForDialogToClose();
		TestUtil.takescreen("跳转到活动链接页面");
		Log.d(TAG, "文本: " + TestUtil
				.getTextOfTextView("hjl_icon_local_contact_title_name"));
		if("精彩活动".equals(TestUtil
				.getTextOfTextView("hjl_icon_local_contact_title_name"))) {
			assertEquals("热门广场活动", "热门广场活动");
			saveLog("(23)热门广场-精彩活动用例测试通过");
		} else {
			saveLog("(23)热门广场-精彩活动用例测试不通过");
			assertEquals("热门广场活动", "热门广场没有活动");
		}
	}
	
	/**
	 * 测试热门广场-咨询公告
	 */
	public void testInformation() {
		saveLog("开始执行testInformation");
		goToTrendingSquare();
		solo.clickOnText("资讯公告");
		//获取“暂无资讯”的TextView
		TextView backgoundtext = (TextView) solo.getView("list_backgound_text");
		Log.d(TAG, "backgoundtext.isShown: " + backgoundtext.isShown());
		//获取所有的资讯公告标题
		ArrayList<View> infoList = TestUtil.getViewsById("title");
		Log.d(TAG, "infoList.size: " + infoList.size());
		//如果没有资讯公告，则需显示“暂无资讯”
		if(infoList.size() == 1) {
			Log.d(TAG, "热门广场暂无资讯");
			TestUtil.takescreen("热门广场暂无资讯");
			assertTrue(backgoundtext.isShown());
			saveLog("(24)热门广场-资讯公告用例测试通过");
			return;
		}
		//点击第一条资讯
		solo.clickOnView(infoList.get(1));
		solo.waitForDialogToClose();
		TestUtil.takescreen("跳转到资讯链接页面");
		if("资讯公告".equals(TestUtil
				.getTextOfTextView("hjl_icon_local_contact_title_name"))) {
			assertEquals("热门广场资讯公告", "热门广场资讯公告");
			saveLog("(24)热门广场-资讯公告用例测试通过");
		} else {
			saveLog("(24)热门广场-资讯公告用例测试不通过");
			assertEquals("热门广场资讯公告", "热门广场没有资讯");
		}
	}
	
	/**
	 * 进入热门广场页面
	 */
	private void goToTrendingSquare() {
		solo.sleep(3000);
		//点击侧栏菜单按钮
		ImageView sideBarMenu = (ImageView) solo.getView("hjl_headicon", 0);
		solo.clickOnView(sideBarMenu);
		solo.clickOnText("热门广场");
		solo.sleep(2000);
	}
}
