package com.kuwaitKFF.advertisements;

import java.util.ArrayList;
import java.util.Locale;


import com.kuwaitKFF.base.BaseActivity;
import com.kuwaitKFF.common.MySharedPref;

import com.kuwaitKFF.main.Kfsd;

import com.kuwaitKFF.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;
import android.widget.ProgressBar;

import butterknife.ButterKnife;

public class AdvertisementsDetailsFragment extends BaseActivity   {

	Kfsd kfsd;
	View view;
	ArrayList<String> circularList;
	ListView listView;
	WebView webView1;
	ProgressBar progressBar;
	
	
	
	public AdvertisementsDetailsFragment() {
		super(R.layout.fragment_circular_details);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialization() {
		ButterKnife.bind(this);

	}

	@Override
	public void implementation() {
		kfsd = (Kfsd) getApplication();
		

		Bundle b = getIntent().getExtras();
		String url=b.getString("pdfUrl");

		Log.d("MzTagDoc", url);
	
		webView1=(WebView)findViewById(R.id.webViewContent);

	
		
		progressBar = (ProgressBar)findViewById(R.id.loading);

		progressBar.setProgress(0);

		progressBar.setVisibility(View.VISIBLE);

		webView1.setWebChromeClient(new WebChromeClient(){

			public void onProgressChanged(WebView view, int progress) {

				progressBar.setProgress(progress);
				if(progress == 100) {
					progressBar.setVisibility(View.GONE);
				}
			}
		});
		webView1.getSettings();
		webView1.setBackgroundColor(Color.TRANSPARENT);
		if(isNetworkAvailable())
		{
			gotoPage(url);
		}
		else
		{
			showNetworkErrorDialog();
		}



		setHeader(getResources().getString(R.string.advertisement), true, true);



		

	}
	
	
	private void gotoPage(String url){

		Log.d("MzTagOO", url);


		WebSettings webSettings = webView1.getSettings();
		webSettings.setBuiltInZoomControls(true);

		webSettings.setJavaScriptEnabled(true);
		webSettings.setDomStorageEnabled(true);

		//webSettings.setDomStorageEnabled(true);
		webView1.clearHistory();
		webView1.clearCache(true);
		webView1.setWebViewClient(new Callback()); 
		
				//HERE IS THE MAIN CHANGE
		webView1.loadUrl("http://docs.google.com/gview?embedded=true&url="+url);

		saveSelectedLanguage(MySharedPref.getLanguage(kfsd)); // to fix the language change bug (mazen)

		
		webView1.requestFocus(View.FOCUS_DOWN);
	    webView1.setOnTouchListener(new View.OnTouchListener() {
	        

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				 switch (event.getAction()) {
	                case MotionEvent.ACTION_DOWN:
	                case MotionEvent.ACTION_UP:
	                    if (!v.hasFocus()) {
	                        v.requestFocus();
	                    }
	                    break;
	            }
	           
				return false;
			}
	    });
	    
	}	
	
	
	private class Callback extends WebViewClient{  //HERE IS THE MAIN CHANGE. 

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {


			return (false);
		}



	}


	
	public  boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager 
		= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null;
	}

	@SuppressWarnings("deprecation")
	public void showNetworkErrorDialog()
	{
		AlertDialog alertDialog = new AlertDialog.Builder(getParent()).create();


		alertDialog.setMessage("Network connection is not available. Please check your internet connection and try again later.");

		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// here you can add functions

				dialog.dismiss();

			}
		});

		alertDialog.show();
	}


	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// preventing default implementation previous to
			// android.os.Build.VERSION_CODES.ECLAIR
			return true;
		}
		return false;

	}

	/**
	 * Overrides the default implementation for KeyEvent.KEYCODE_BACK so that
	 * all systems call onBackPressed().
	 */

	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			try {

			finish();

			} catch (Exception e) {
				e.printStackTrace();
			}

			return true;

		}

		return false;
	}


	private void saveSelectedLanguage(String language) {
		Resources res = getResources();

		// Change locale settings in the application.
		DisplayMetrics dm = res.getDisplayMetrics();
		android.content.res.Configuration conf = res.getConfiguration();
		conf.locale = new Locale(language);
		res.updateConfiguration(conf, dm);
	}
	

}
