package com.kuwaitKFF.common;


import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.entity.StringEntity;

public class MyHttpConnection {

	private static AsyncHttpClient client = new AsyncHttpClient();
	private static int timeout = 240 * 1000;

	public static void get(String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		
		client.setTimeout(timeout);
		client.get(getAbsoluteUrl(url), params, responseHandler);
	}

	public static void getNew(String url, RequestParams params,
						   AsyncHttpResponseHandler responseHandler) {

		client.setTimeout(timeout);
		client.get(getAbsoluteUrlNew(url), params, responseHandler);
	}
	
	public static void get(String url, 
			AsyncHttpResponseHandler responseHandler) {
		
		
		client.get(getAbsoluteUrl(url),  responseHandler);
		
	}

	public static void getNew(String url,
						   AsyncHttpResponseHandler responseHandler) {


		client.get(getAbsoluteUrlNew(url),  responseHandler);

	}

	public static void getLeaveRequest(String url, RequestParams params,
									   AsyncHttpResponseHandler responseHandler) {

		client.setTimeout(timeout);
		client.get(getAbsoluteUrlLeaveRequest(url), params, responseHandler);
	}

	public static void getPermissionRequest(String url, RequestParams params,
									   AsyncHttpResponseHandler responseHandler) {

		client.setTimeout(timeout);
		client.get(getAbsoluteUrlPermissionRequest(url), params, responseHandler);
	}


	public static void post(String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {

		String urlValue = getAbsoluteUrl(url)+params;
		client.post(getAbsoluteUrl(url), params, responseHandler);

	}

	private static String getAbsoluteUrl(String relativeUrl) {
		return MyCommon.WS_BASE_URL + relativeUrl;
	}

	private static String getAbsoluteUrlNew(String relativeUrl) {
		return MyCommon.WS_BASE_URL_NEW  + relativeUrl;
	}

	private static String getAbsoluteUrlLeaveRequest(String relativeUrl) {
		return MyCommon.WS_BASE_URL_LEAVE_REQUEST  + relativeUrl;
	}

	private static String getAbsoluteUrlPermissionRequest(String relativeUrl) {
		return MyCommon.WS_BASE_URL_PERMISSION_REQUEST  + relativeUrl;
	}
	
	public static void postWithJsonEntity(Context context, String url, StringEntity stringEntitiy, AsyncHttpResponseHandler responseHandler){
		client.setTimeout(timeout);
		client.post(context, getAbsoluteUrl(url), stringEntitiy, "application/json", responseHandler);
		
	}
}
