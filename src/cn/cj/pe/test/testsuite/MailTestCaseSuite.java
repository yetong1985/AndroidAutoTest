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
 * ����һ�����������ļ��ϣ���ָ����˳��ִ������
 * @author yetong
 *
 */
public class MailTestCaseSuite {
	
	public static TestSuite buildTestSuite() {
		TestSuite suite = new TestSuite();
		//��¼�����ʼ�����
		suite.addTestSuite(LoginCase.class);
		//ͨѶ¼����
		suite.addTestSuite(AddressBookCase.class);
		//������������
		suite.addTestSuite(AttachmentCase.class);
		//���� ����
		suite.addTestSuite(CalendarCase.class);
		//������������
		suite.addTestSuite(CaiyunCase.class);
		//������������
		suite.addTestSuite(PendingAssignmentCase.class);
		//���Ź㳡����
		suite.addTestSuite(TrendingSquareCase.class);
		//���á���������
		suite.addTestSuite(SettingCase.class);
		//���ʼ�-����ˢ���ʼ�����
		suite.addTestSuite(ReceiveMailCase.class);
		return suite;
	}
	
}
