package com.kuwaitKFF.main;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

public class Kfsd extends Application{
	
	// Check the network reachable or not , value will be return in boolean
		public boolean checkNetworkRechability() {
			Boolean bNetwork = false;
			ConnectivityManager connectivityManager = (ConnectivityManager) this
					.getSystemService(Context.CONNECTIVITY_SERVICE);

			for (NetworkInfo networkInfo : connectivityManager.getAllNetworkInfo()) {
				int netType = networkInfo.getType();
				int netSubType = networkInfo.getSubtype();

				if (netType == ConnectivityManager.TYPE_WIFI) {
					bNetwork = networkInfo.isConnected();
					if (bNetwork == true)
						break;
				} else if (netType == ConnectivityManager.TYPE_MOBILE
						&& netSubType != TelephonyManager.NETWORK_TYPE_UNKNOWN) {
					bNetwork = networkInfo.isConnected();
					if (bNetwork == true)
						break;
				} else {
					bNetwork = false;
				}
			}

			return bNetwork;
		}

}