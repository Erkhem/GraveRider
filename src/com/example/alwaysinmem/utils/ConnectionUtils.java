package com.example.alwaysinmem.utils;

import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;

public class ConnectionUtils {

	public static Boolean isAppConnected(ConnectivityManager manager) {
		ConnectivityManager connectManager = manager;
		State mobile = connectManager.getNetworkInfo(0).getState();
		State wifi = connectManager.getNetworkInfo(1).getState();

		return (mobile == State.CONNECTED || mobile == State.CONNECTING || wifi == State.CONNECTED
				|| wifi == State.CONNECTING || wifi == State.CONNECTING);
	}
	
	
}
