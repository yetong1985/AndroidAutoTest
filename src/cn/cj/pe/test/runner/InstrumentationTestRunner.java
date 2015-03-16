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
 * 重写InstrumentationTestRunner，输出Junit格式的测试报告
 * @author yetong
 *
 */
public class InstrumentationTestRunner
    extends android.test.InstrumentationTestRunner {

	private final String TAG = "MailTestRunner";
    private Writer mWriter;
    //serializer就是串行化，又名序列化。它可并不只是简单的把对象保存在存储器上，
    //它可以使我们在流中传输对象，使对象变的可以像基本数据一样传递。
    private XmlSerializer mTestSuiteSerializer;
    //测试开始的时间
    private long mTestStarted;
    private static final String JUNIT_XML_REPORT = "mail139-report.xml";
    File resultFile = null;
    private Set<String> testsuiteSet = new HashSet<String>();  
    //每一个testsuite开始执行时都会初始化这个变量
    boolean isTestSuiteStart = true;
	
    /**
     * 获取测试用例
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
	 * 默认在onCreate时调用onStart，在InstrumentationTestRunner启动时候做一些准备工作
	 */
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		Log.d(TAG, "onStart");
		File resultDir = new File(getTestResultDir(getTargetContext()));
		if(!resultDir.exists()) {
			resultDir.mkdir();
		}
		//在指定路径下创建文件
		resultFile = new File(getTestResultDir(getTargetContext()), JUNIT_XML_REPORT);
		try {
			startJUnitOutput(new FileWriter(resultFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.d(TAG, "onStart异常");
			e.printStackTrace();
		}
		//必须引用基类的方法
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
	 * 初始化Junit报告的开头格式，像这样的：
	 * <?xml version='1.0' encoding='utf-8' standalone='yes' ?><testsuites><testsuite>
	 * @param writer
	 */
    private void startJUnitOutput(Writer writer) {
    	Log.d(TAG, "startJUnitOutput");
        try {
            mWriter = writer;
            mTestSuiteSerializer = newSerializer(mWriter);
            /*
             * startDocument(String encoding, Boolean standalone)encoding代表编码方式 
             * encoding  编码格式
             * standalone  用来表示该文件是否呼叫其它外部的文件。 若值是 ”true” 表示没有呼叫外部规则文件，
             * 若值是 ”false” 则表示有呼叫外部规则文件。默认值是 “yes”。
             * 比如：<?xml version='1.0' encoding='utf-8' standalone='yes' ?>
             */
            mTestSuiteSerializer.startDocument("utf-8", true);
            /*
             * startTag (String namespace, String name)这里的namespace用于唯一标识xml标签 
             * XML 命名空间属性被放置于某个元素的开始标签之中，并使用以下的语法： 
             * xmlns:namespace-prefix="namespaceURI" 
             * 当一个命名空间被定义在某个元素的开始标签中时，所有带有相同前缀的子元素都会与同一个命名空间相关联。 
             * 注释：用于标示命名空间的地址不会被解析器用于查找信息。其惟一的作用是赋予命名空间一个惟一的名称。
             * 不过，很多公司常常会作为指针来使用命名空间指向某个实存的网页，这个网页包含着有关命名空间的信息。 
             * 
             * 在Junit报告中：
             * <?xml version='1.0' encoding='utf-8' standalone='yes' ?><testsuites>
             * 接着是<testsuite name="某个testsuite">
             */ 
            Log.d(TAG, "打上<testsuites>标签");
            mTestSuiteSerializer.startTag(null, "testsuites");
        } catch (Exception e) {
        	Log.d(TAG, "startJUnitOutput异常");
            throw new RuntimeException(e);
        }
    }
    
    private XmlSerializer newSerializer(Writer writer) {
        try {
        	Log.d(TAG, "newSerializer");
            XmlPullParserFactory pf = XmlPullParserFactory.newInstance();
            //获取XmlSerializer实例
            XmlSerializer serializer = pf.newSerializer();
            //设置输出流对象
            serializer.setOutput(writer);
            return serializer;
        } catch (Exception e) {
        	Log.d(TAG, "newSerializer异常");
            throw new RuntimeException(e);
        }       
    }
    
    /**
     * 每次执行用例的开始和结束框架时调用，可以获取到测试的状态和结果，自定义收集结果的方式
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
		//测试开始  The test is starting.
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
	 * 开始记录测试结果
	 * @param results
	 */
    private void recordTestStart(Bundle results) {
        mTestStarted = System.currentTimeMillis();
    }
    
    private void recordTestResult(int resultCode, Bundle results) {
    	try {
        	Log.d(TAG, "recordTestResult");
            float time = (System.currentTimeMillis() - mTestStarted) / 1000.0f;
            //获取测试用例的类
            String className = results.getString(REPORT_KEY_NAME_CLASS);
            String testMethod = results.getString(REPORT_KEY_NAME_TEST);
            //a stack trace describing an error or failure.
            String stack = results.getString(REPORT_KEY_STACK);
            int current = results.getInt(REPORT_KEY_NUM_CURRENT);
            int total = results.getInt(REPORT_KEY_NUM_TOTAL);
            Log.d(TAG, "isTestSuiteStart = " + isTestSuiteStart);
            //每个testsuite开始时
            if(!testsuiteSet.contains(className)) {
				//如果不是第一个用例，则打上</testsuite>标签
				if(current != 1) {
					mTestSuiteSerializer.endTag(null, "testsuite");
				}
				mTestSuiteSerializer.startTag(null, "testsuite");
				Log.d(TAG, "打上<testsuite>标签");
	            /*
	             * 设置testsuite的节点属性，比如：
	             * <testsuite name="cn.com.fetion.test.testcase.testSendFreeMessage">
	             */
	            mTestSuiteSerializer.attribute(null, "name", className);
	            testsuiteSet.add(className);
	            isTestSuiteStart = false;
            }
            /*
             * 接着
             * <testcase classname="cn.com.fetion.test.testcase.testSendFreeMessage" 
             * name="testSendMessageCase1" time="86.040" /></testsuite>
             */
            mTestSuiteSerializer.startTag(null, "testcase");
            Log.d(TAG, "打上<testcase>标签");
            //为testcase标签添加属性
            mTestSuiteSerializer.attribute(null, "name", testMethod);
            mTestSuiteSerializer.attribute(null, "time", 
        			String.format("%.3f", time));
            mTestSuiteSerializer.attribute(null, "classname", className);
            //如果该用例测试不通过
            if (resultCode != REPORT_VALUE_RESULT_OK) {
            	//如果测试error，则添加error标签
            	if(resultCode == REPORT_VALUE_RESULT_ERROR) {
            		mTestSuiteSerializer.startTag(null, "error");
            		Log.d(TAG, "打上<error>标签");
            	} else {
            		mTestSuiteSerializer.startTag(null, "failure");
            		Log.d(TAG, "打上<failure>标签");
            	}
            	// 获取用例失败原因的详细描述
            	String message = stack.substring(0, stack.indexOf('\n'));
            	Log.d(TAG, "message = " + message);
            	//失败原因的概述
                String reason = "";
                //错误原因最开始描述的地方
                int index = message.indexOf(':');
                if(index > 0) {
                	reason = message.substring(0, index);
                	Log.d(TAG, "reason = " + reason);
                }
                mTestSuiteSerializer.attribute(null, "type", reason);
                mTestSuiteSerializer.attribute(null, "message", message);
                //节点的描述
                mTestSuiteSerializer.text(stack);
                
            	if(resultCode == REPORT_VALUE_RESULT_ERROR) {
            		mTestSuiteSerializer.endTag(null, "error");
            		Log.d(TAG, "打上</error>标签");
            	} else {
            		mTestSuiteSerializer.endTag(null, "failure");
            		Log.d(TAG, "打上</failure>标签");
            	}   
            }
            Log.d(TAG, "打上</testcase>标签");
            mTestSuiteSerializer.endTag(null, "testcase");    
            //用例结束时需为最后一个testsuite打上结束标签
            if (current == total) {
            	mTestSuiteSerializer.endTag(null, "testsuite");
            	Log.d(TAG, "打上</testsuite>标签");
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
     * testrunner执行结束
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
	 * 获取存放测试结果的目录
	 * @param context
	 * @return
	 */
	private String getTestResultDir(Context context) {
		String packageName =  "/robotium";
		String filepath = context.getCacheDir().getPath() + packageName;
		// 如果手机插入了SD卡，而且具有SD卡访问权限
		if(Environment.getExternalStorageState()
				.equals(Environment.MEDIA_MOUNTED)) {
			filepath = Environment.getExternalStorageDirectory().getAbsolutePath()
					+ packageName;
		}
		return filepath;
	}
	
}
