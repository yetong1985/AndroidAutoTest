package cn.cj.pe.test.testcase;

import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import cn.cj.pe.test.BasicTestCase;
import cn.cj.pe.test.utils.TestUtil;

/**
 * 附件管理测试用例
 * @author yetong
 *
 */
public class AttachmentCase extends BasicTestCase {

	private final String TAG = "AttachmentCase";
	//搜索关键词
	private String keyWord = "139";
	
	/**
	 * 测试附件管理-搜索附件
	 */
	public void test1SearchAttachment() {
		saveLog("开始执行testSearchAttachment");
		goToAttachmentManagement();
		//点击全部
		//solo.clickOnText("全部");
		solo.clickOnView(solo.getView("show_contact_all", 1));
		solo.sleep(2000);
		//点击搜索
		solo.clickOnText("搜索");
		//在当前页面的第一个编辑框中输入关键字
		solo.enterText(0, keyWord);
		solo.sleep(2000);
		/*
		ListView attachmentList = (ListView) solo.getView("listView");
		Log.d(TAG, "attachmentList.getChildCount: " 
		    + attachmentList.getChildCount());
		*/
		if(solo.searchText("无匹配结果")) {
			Log.d(TAG, "无匹配结果");
			assertEquals("无匹配结果", "无匹配结果");
			saveLog("(11)附件管理-搜索附件 用例测试通过");
			return;
		}
		if(solo.searchText(keyWord)) {
			Log.d(TAG, "找到所指定的附件了");
			TestUtil.takescreen("显示搜索结果-匹配关键字");
			assertEquals("显示搜索结果", "显示搜索结果");
			saveLog("(11)附件管理-搜索附件 用例测试通过");
			return;
		}
		saveLog("(11)附件管理-搜索附件 用例测试不通过");
		assertEquals("显示搜索结果", "不显示搜索结果");
	}
	
	/**
	 * 测试附件管理-下载附件
	 */
	public void test2DownloadAttachment() {
		saveLog("开始执行testDownloadAttachment");
		goToAttachmentManagement();
		//点击全部
		solo.clickOnView(solo.getView("show_contact_all", 1));
		solo.sleep(2000);
		//获取第一个附件的名字
		TextView attachName = (TextView) solo.getView("name");
		if(attachName == null) {
			Log.d(TAG, "没有附件");
			TestUtil.takescreen("没有附件");
			return;
		}
		Log.d(TAG, "name.getText: " + attachName.getText());
		String fileName = attachName.getText().toString();
		//点击第一个文件的下载按钮
		solo.clickOnView(solo.getView("control"));
		solo.sleep(2000);
		if (TestUtil.getTextOfTextView("action_download_txt").equals("打开")) {
			Log.d(TAG, fileName + "已经下载了，现在要把它删除掉");
			if(delAttachment(fileName) == false) {
				Log.d(TAG, "已下载的附件删除失败");
				return;
			}
		} 
		//点击第一个附件的下载按钮
		TestUtil.clickLongById("action_download", 3);
		ProgressBar pb = (ProgressBar) solo.getView("progressBar");
		long timeNow = System.currentTimeMillis();
		boolean isDownloadSuccess = false;
		//设置超时两分钟
		long timeOut = 120000;
		while(System.currentTimeMillis() < timeNow + timeOut) {
			if(!pb.isShown()) {
				Log.d(TAG, "进度条消失了");
				isDownloadSuccess = true;
				break;
			}
		}
		if(!isDownloadSuccess) {
			Log.d(TAG, "进度条还在，下载附件失败");
			TestUtil.takescreen("附件下载超时");
			saveLog("(12)附件管理-下载附件 用例测试不通过");
			assertEquals("附件下载成功", "附件下载失败");
		}
		solo.sleep(10000);
		if(TestUtil.getTextOfTextView("action_download_txt").equals("打开")) {
			Log.d(TAG, "附件下载成功，附件的下载按钮变为打开按钮");
			TestUtil.takescreen("附件下载成功");
			assertEquals("附件下载成功", "附件下载成功");
			saveLog("(12)附件管理-下载附件 用例测试通过");
		} else {
			Log.d(TAG, "附件下载失败，附件的下载按钮没有变为打开按钮");
			TestUtil.takescreen("附件下载失败");
			saveLog("(12)附件管理-下载附件 用例测试不通过");
			assertEquals("附件下载成功", "附件下载失败");
		}
	}
	
	/**
	 * 进入附件管理页面页面
	 */
	private void goToAttachmentManagement() {
		solo.sleep(3000);
		//点击侧栏菜单按钮
		ImageView sideBarMenu = (ImageView) solo.getView("hjl_headicon", 0);
		solo.clickOnView(sideBarMenu);
		solo.clickOnText("附件管理");
		solo.sleep(2000);
	}
	
	/**
	 * 删除附件文件
	 * @param fileName: 要删除的附件名字
	 */
	private boolean delAttachment(String fileName) {
		solo.clickOnText("已下载");
		if(solo.searchText(fileName)) {
			solo.clickOnView(solo.getView("control"));
			solo.sleep(2000);
			solo.clickOnText("删除");
			solo.waitForDialogToOpen();
			//solo.clickOnText("确定");
			TestUtil.clickById("right", 3);
			solo.waitForDialogToClose();
			if(solo.searchText("该文件已删除")
					|| !solo.searchText(fileName)) {
				Log.d(TAG, "已删除文件：" + fileName);
				//点击全部
				solo.clickOnView(solo.getView("show_contact_all", 1));
				solo.sleep(2000);
				//点击第一个文件的下载按钮
				solo.clickOnView(solo.getView("control"));
				solo.sleep(2000);
				return true;
			}
		}
		return false;
	}
}
