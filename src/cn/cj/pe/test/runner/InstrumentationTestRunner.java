package cn.cj.pe.test.runner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestSuite;

import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import cn.cj.pe.test.testcase.CaiyunCase;
import cn.cj.pe.test.testcase.LoginCase;
import cn.cj.pe.test.testsuite.MailTestCaseSuite;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.test.suitebuilder.TestSuiteBuilder;
import android.util.Log;

/**
 * ��дInstrumentationTestRunner�����Junit��ʽ�Ĳ��Ա���
 * @author yetong
 *
 */
public class InstrumentationTestRunner
    extends android.test.InstrumentationTestRunner {

	private final String TAG = "MailTestRunner";
    private Writer mWriter;
    //serializer���Ǵ��л����������л������ɲ���ֻ�Ǽ򵥵İѶ��󱣴��ڴ洢���ϣ�
    //������ʹ���������д������ʹ�����Ŀ������������һ�����ݡ�
    private XmlSerializer mTestSuiteSerializer;
    //���Կ�ʼ��ʱ��
    private long mTestStarted;
    private static final String JUNIT_XML_REPORT = "mail139-report.xml";
    File resultFile = null;
    private Set<String> testsuiteSet = new HashSet<String>();  
    //ÿһ��testsuite��ʼִ��ʱ�����ʼ���������
    boolean isTestSuiteStart = true;
	
    /**
     * ��ȡ��������
     */
	@Override
	public TestSuite getAllTests() {
		Log.d(TAG, "getAllTests");
		TestSuite suite = new TestSuite();
		suite.addTest(MailTestCaseSuite.buildTestSuite());
		// TODO Auto-generated method stub
		return suite;
	} 

	
	/**
	 * Ĭ����onCreateʱ����onStart����InstrumentationTestRunner����ʱ����һЩ׼������
	 */
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		Log.d(TAG, "onStart");
		File resultDir = new File(getTestResultDir(getTargetContext()));
		if(!resultDir.exists()) {
			resultDir.mkdir();
		}
		//��ָ��·���´����ļ�
		resultFile = new File(getTestResultDir(getTargetContext()), JUNIT_XML_REPORT);
		try {
			startJUnitOutput(new FileWriter(resultFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.d(TAG, "onStart�쳣");
			e.printStackTrace();
		}
		//�������û���ķ���
		super.onStart();
	}

	/*
	@Override
	public TestSuite getTestSuite() {
		Log.d(TAG, "getTestSuite");
		// TODO Auto-generated method stub
		return buildTestSuite();
	}
	
	private TestSuite buildTestSuite() {
	    TestSuiteBuilder builder = new TestSuiteBuilder(getClass().getName(), 
	    		getTargetContext().getClassLoader());
	    builder.includeAllPackagesUnderHere();
	    TestSuite suite = builder.build();
	    suite.addTestSuite(LoginCase.class);
	    suite.addTestSuite(CaiyunCase.class);
	    return suite;
	 }
	 */

	/**
	 * ��ʼ��Junit����Ŀ�ͷ��ʽ���������ģ�
	 * <?xml version='1.0' encoding='utf-8' standalone='yes' ?><testsuites><testsuite>
	 * @param writer
	 */
    private void startJUnitOutput(Writer writer) {
    	Log.d(TAG, "startJUnitOutput");
        try {
            mWriter = writer;
            mTestSuiteSerializer = newSerializer(mWriter);
            /*
             * startDocument(String encoding, Boolean standalone)encoding������뷽ʽ 
             * encoding  �����ʽ
             * standalone  ������ʾ���ļ��Ƿ���������ⲿ���ļ��� ��ֵ�� ��true�� ��ʾû�к����ⲿ�����ļ���
             * ��ֵ�� ��false�� ���ʾ�к����ⲿ�����ļ���Ĭ��ֵ�� ��yes����
             * ���磺<?xml version='1.0' encoding='utf-8' standalone='yes' ?>
             */
            mTestSuiteSerializer.startDocument("utf-8", true);
            /*
             * startTag (String namespace, String name)�����namespace����Ψһ��ʶxml��ǩ 
             * XML �����ռ����Ա�������ĳ��Ԫ�صĿ�ʼ��ǩ֮�У���ʹ�����µ��﷨�� 
             * xmlns:namespace-prefix="namespaceURI" 
             * ��һ�������ռ䱻������ĳ��Ԫ�صĿ�ʼ��ǩ��ʱ�����д�����ͬǰ׺����Ԫ�ض�����ͬһ�������ռ�������� 
             * ע�ͣ����ڱ�ʾ�����ռ�ĵ�ַ���ᱻ���������ڲ�����Ϣ����Ωһ�������Ǹ��������ռ�һ��Ωһ�����ơ�
             * �������ܶ๫˾��������Ϊָ����ʹ�������ռ�ָ��ĳ��ʵ�����ҳ�������ҳ�������й������ռ����Ϣ�� 
             * 
             * ��Junit�����У�
             * <?xml version='1.0' encoding='utf-8' standalone='yes' ?><testsuites>
             * ������<testsuite name="ĳ��testsuite">
             */ 
            Log.d(TAG, "����<testsuites>��ǩ");
            mTestSuiteSerializer.startTag(null, "testsuites");
        } catch (Exception e) {
        	Log.d(TAG, "startJUnitOutput�쳣");
            throw new RuntimeException(e);
        }
    }
    
    private XmlSerializer newSerializer(Writer writer) {
        try {
        	Log.d(TAG, "newSerializer");
            XmlPullParserFactory pf = XmlPullParserFactory.newInstance();
            //��ȡXmlSerializerʵ��
            XmlSerializer serializer = pf.newSerializer();
            //�������������
            serializer.setOutput(writer);
            return serializer;
        } catch (Exception e) {
        	Log.d(TAG, "newSerializer�쳣");
            throw new RuntimeException(e);
        }       
    }
    
    /**
     * ÿ��ִ�������Ŀ�ʼ�ͽ������ʱ���ã����Ի�ȡ�����Ե�״̬�ͽ�����Զ����ռ�����ķ�ʽ
     * Provide a status report about the application
     * resultCode - Current success/failure of instrumentation.
     * results - Any results to send back to the code that started the instrumentation.
     */
	@Override
	public void sendStatus(int resultCode, Bundle results) {
		// TODO Auto-generated method stub
		//
		Log.d(TAG, "sendStatus");
		switch(resultCode) {
		//���Կ�ʼ  The test is starting.
		case REPORT_VALUE_RESULT_START:
			recordTestStart(results);
			break;
		case REPORT_VALUE_RESULT_ERROR:
		case REPORT_VALUE_RESULT_FAILURE:
		case REPORT_VALUE_RESULT_OK:
			recordTestResult(resultCode, results);
			break;
		default:
			break;	
		}
		super.sendStatus(resultCode, results);
	}
	
	/**
	 * ��ʼ��¼���Խ��
	 * @param results
	 */
    private void recordTestStart(Bundle results) {
        mTestStarted = System.currentTimeMillis();
    }
    
    private void recordTestResult(int resultCode, Bundle results) {
    	try {
        	Log.d(TAG, "recordTestResult");
            float time = (System.currentTimeMillis() - mTestStarted) / 1000.0f;
            //��ȡ������������
            String className = results.getString(REPORT_KEY_NAME_CLASS);
            String testMethod = results.getString(REPORT_KEY_NAME_TEST);
            //a stack trace describing an error or failure.
            String stack = results.getString(REPORT_KEY_STACK);
            int current = results.getInt(REPORT_KEY_NUM_CURRENT);
            int total = results.getInt(REPORT_KEY_NUM_TOTAL);
            Log.d(TAG, "isTestSuiteStart = " + isTestSuiteStart);
            //ÿ��testsuite��ʼʱ
            if(!testsuiteSet.contains(className)) {
				//������ǵ�һ�������������</testsuite>��ǩ
				if(current != 1) {
					mTestSuiteSerializer.endTag(null, "testsuite");
				}
				mTestSuiteSerializer.startTag(null, "testsuite");
				Log.d(TAG, "����<testsuite>��ǩ");
	            /*
	             * ����testsuite�Ľڵ����ԣ����磺
	             * <testsuite name="cn.com.fetion.test.testcase.testSendFreeMessage">
	             */
	            mTestSuiteSerializer.attribute(null, "name", className);
	            testsuiteSet.add(className);
	            isTestSuiteStart = false;
            }
            /*
             * ����
             * <testcase classname="cn.com.fetion.test.testcase.testSendFreeMessage" 
             * name="testSendMessageCase1" time="86.040" /></testsuite>
             */
            mTestSuiteSerializer.startTag(null, "testcase");
            Log.d(TAG, "����<testcase>��ǩ");
            //Ϊtestcase��ǩ�������
            mTestSuiteSerializer.attribute(null, "name", testMethod);
            mTestSuiteSerializer.attribute(null, "time", 
        			String.format("%.3f", time));
            mTestSuiteSerializer.attribute(null, "classname", className);
            //������������Բ�ͨ��
            if (resultCode != REPORT_VALUE_RESULT_OK) {
            	//�������error�������error��ǩ
            	if(resultCode == REPORT_VALUE_RESULT_ERROR) {
            		mTestSuiteSerializer.startTag(null, "error");
            		Log.d(TAG, "����<error>��ǩ");
            	} else {
            		mTestSuiteSerializer.startTag(null, "failure");
            		Log.d(TAG, "����<failure>��ǩ");
            	}
            	// ��ȡ����ʧ��ԭ�����ϸ����
            	String message = stack.substring(0, stack.indexOf('\n'));
            	Log.d(TAG, "message = " + message);
            	//ʧ��ԭ��ĸ���
                String reason = "";
                //����ԭ���ʼ�����ĵط�
                int index = message.indexOf(':');
                if(index > 0) {
                	reason = message.substring(0, index);
                	Log.d(TAG, "reason = " + reason);
                }
                mTestSuiteSerializer.attribute(null, "type", reason);
                mTestSuiteSerializer.attribute(null, "message", message);
                //�ڵ������
                mTestSuiteSerializer.text(stack);
                
            	if(resultCode == REPORT_VALUE_RESULT_ERROR) {
            		mTestSuiteSerializer.endTag(null, "error");
            		Log.d(TAG, "����</error>��ǩ");
            	} else {
            		mTestSuiteSerializer.endTag(null, "failure");
            		Log.d(TAG, "����</failure>��ǩ");
            	}   
            }
            Log.d(TAG, "����</testcase>��ǩ");
            mTestSuiteSerializer.endTag(null, "testcase");    
            //��������ʱ��Ϊ���һ��testsuite���Ͻ�����ǩ
            if (current == total) {
            	mTestSuiteSerializer.endTag(null, "testsuite");
            	Log.d(TAG, "����</testsuite>��ǩ");
            	mTestSuiteSerializer.flush();
            }      
    		
    	} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
	
    /**
     * testrunnerִ�н���
     */
	@Override
	public void finish(int resultCode, Bundle results) {
		// TODO Auto-generated method stub
		Log.d(TAG, "finish");
		endTestSuites();
		super.finish(resultCode, results);
	}
	
    private void endTestSuites() {
    	Log.d(TAG, "endTestSuites");
        try {
            mTestSuiteSerializer.endTag(null, "testsuites");
            mTestSuiteSerializer.endDocument();
            mTestSuiteSerializer.flush();
            mWriter.flush();
            mWriter.close();
        } catch (IOException e) {
        	Log.d(TAG, "finish IOException");
            throw new RuntimeException(e);
        }
    }


	/**
	 * ��ȡ��Ų��Խ����Ŀ¼
	 * @param context
	 * @return
	 */
	private String getTestResultDir(Context context) {
		String packageName =  "/robotium";
		String filepath = context.getCacheDir().getPath() + packageName;
		// ����ֻ�������SD�������Ҿ���SD������Ȩ��
		if(Environment.getExternalStorageState()
				.equals(Environment.MEDIA_MOUNTED)) {
			filepath = Environment.getExternalStorageDirectory().getAbsolutePath()
					+ packageName;
		}
		return filepath;
	}
	
}
