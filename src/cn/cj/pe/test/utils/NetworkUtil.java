package cn.cj.pe.test.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * ����������
 * @author yetong
 *
 */
public class NetworkUtil {
	private final static String TAG = "NetworkUtil";
	private Context context;
	private NetworkInfo mNetworkInfo;
	
	public NetworkUtil(Context context) {
		this.context = context;
	}
	/**
	 * �ж��ֻ��Ƿ��Ƿ���ģʽ
	 * @param context
	 * @return
	 */
	public boolean isAirplaneMode() {
		//��ȡ��ǰ����ģʽ״̬,���ص���Stringֵ0,��1.   0Ϊ�رշ���,1Ϊ��������
		int isAirplaneMode = Settings.System.getInt(context.getContentResolver(), 
				Settings.System.AIRPLANE_MODE_ON, 0);
		Log.i(TAG, "isAirplaneMode = " + isAirplaneMode);
		return (isAirplaneMode == 1) ? true:false; 
	}
	
	/**
	 * �ж�WIFI�����Ƿ�������
	 * @param context
	 * @return
	 */
	public boolean isWifiConnected() {
		ConnectivityManager mConnectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mWiFiNetworkInfo = mConnectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	//	Log.i(TAG, "isWifiConnected(), isAvailable(): " + mWiFiNetworkInfo.isAvailable());
	//	Log.i(TAG, "isWifiConnected(), isConnected(): " + mWiFiNetworkInfo.isConnected());
		if (mWiFiNetworkInfo != null) {
			return mWiFiNetworkInfo.isConnected();  
		}
		Log.i(TAG, "isWifiConnected, mWiFiNetworkInfo == null");
		return false;
	}
	
	/**
	 * ��ȡ��ǰ�������ӵ�������Ϣ
	 * @param context
	 * @return
	 */
	public String getConnectedType() {
		String netType = "";

		if (isNetworkConnected()) {
			int nType = mNetworkInfo.getType(); 
			if(nType == ConnectivityManager.TYPE_MOBILE) {
				Log.i("networkInfo.getExtraInfo()", "networkInfo.getExtraInfo() is " + 
						mNetworkInfo.getExtraInfo());
				if(mNetworkInfo.getExtraInfo().toLowerCase().equals("cmnet")) { 
					Log.i(TAG, "��ǰ����������CMNET");
					netType = "CMNET";
				} else {
					Log.i(TAG, "��ǰ����������CMWAP");
					 netType = "CMWAP";
				} 
			} else if (nType==ConnectivityManager.TYPE_WIFI){ 
				Log.i(TAG, "��ǰ����������TYPE_WIFI");
				netType = "WIFI"; 
			}
		}
		return netType;
	}
	
	/**
	 * �ж��Ƿ��WIFI����
	 * @return
	 */
    public boolean isWifiOn() {
        int value = -1;
		try {
			value = Settings.System.getInt(context.getContentResolver(), Settings.Secure.WIFI_ON);
			Log.i(TAG, "isWifiOn, value = " + value);
		} catch (SettingNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 0��ʾû�д�WIFI�� 1��ʾ�Ѵ�WIFI
        return value == 0 ? false : true;
    }
    
    /**
     * �ж�����(WIFI����������)�Ƿ�����
     * @return
     */
    public boolean isNetworkConnected() {
    	ConnectivityManager mConnectivityManager = (ConnectivityManager) context
    			.getSystemService(Context.CONNECTIVITY_SERVICE);
    	mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
    	if (mNetworkInfo != null) {  
    //		Log.i(TAG, "isNetworkConnected, isConnected: " + mNetworkInfo.isConnected());
    //		Log.i(TAG, "isNetworkConnected, isAvailable: " + mNetworkInfo.isAvailable());
    		return mNetworkInfo.isConnected();
    	}
    	Log.i(TAG, "isNetworkConnected, mNetworkInfo = null");
    	return false;
    }
}
