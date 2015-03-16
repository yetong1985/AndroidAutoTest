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
 * 网络连接类
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
	 * 判断手机是否是飞行模式
	 * @param context
	 * @return
	 */
	public boolean isAirplaneMode() {
		//获取当前飞行模式状态,返回的是String值0,或1.   0为关闭飞行,1为开启飞行
		int isAirplaneMode = Settings.System.getInt(context.getContentResolver(), 
				Settings.System.AIRPLANE_MODE_ON, 0);
		Log.i(TAG, "isAirplaneMode = " + isAirplaneMode);
		return (isAirplaneMode == 1) ? true:false; 
	}
	
	/**
	 * 判断WIFI网络是否已连接
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
	 * 获取当前网络连接的类型信息
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
					Log.i(TAG, "当前网络类型是CMNET");
					netType = "CMNET";
				} else {
					Log.i(TAG, "当前网络类型是CMWAP");
					 netType = "CMWAP";
				} 
			} else if (nType==ConnectivityManager.TYPE_WIFI){ 
				Log.i(TAG, "当前网络类型是TYPE_WIFI");
				netType = "WIFI"; 
			}
		}
		return netType;
	}
	
	/**
	 * 判断是否打开WIFI开关
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
		// 0表示没有打开WIFI， 1表示已打开WIFI
        return value == 0 ? false : true;
    }
    
    /**
     * 判断网络(WIFI或数据网络)是否连接
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
