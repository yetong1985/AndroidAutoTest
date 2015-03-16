package cn.cj.pe.test.testsuite;

import cn.cj.pe.test.testcase.AddressBookCase;
import cn.cj.pe.test.testcase.AttachmentCase;
import cn.cj.pe.test.testcase.CaiyunCase;
import cn.cj.pe.test.testcase.CalendarCase;
import cn.cj.pe.test.testcase.LoginCase;
import cn.cj.pe.test.testcase.PendingAssignmentCase;
import cn.cj.pe.test.testcase.ReceiveMailCase;
import cn.cj.pe.test.testcase.SettingCase;
import cn.cj.pe.test.testcase.TrendingSquareCase;
import junit.framework.TestSuite;

/**
 * 建立一个测试用例的集合，按指定的顺序执行用例
 * @author yetong
 *
 */
public class MailTestCaseSuite {
	
	public static TestSuite buildTestSuite() {
		TestSuite suite = new TestSuite();
		//登录、发邮件用例
		suite.addTestSuite(LoginCase.class);
		//通讯录用例
		suite.addTestSuite(AddressBookCase.class);
		//附件管理用例
		suite.addTestSuite(AttachmentCase.class);
		//日历 用例
		suite.addTestSuite(CalendarCase.class);
		//彩云网盘用例
		suite.addTestSuite(CaiyunCase.class);
		//代办任务用例
		suite.addTestSuite(PendingAssignmentCase.class);
		//热门广场用例
		suite.addTestSuite(TrendingSquareCase.class);
		//设置、换肤用例
		suite.addTestSuite(SettingCase.class);
		//收邮件-主动刷新邮件用例
		suite.addTestSuite(ReceiveMailCase.class);
		return suite;
	}
	
}
