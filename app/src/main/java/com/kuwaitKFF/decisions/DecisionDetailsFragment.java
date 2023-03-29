package com.kuwaitKFF.decisions;

import java.util.ArrayList;


import com.kuwaitKFF.base.BaseActivity;
import com.kuwaitKFF.main.Kfsd;

import com.kuwaitKFF.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.ProgressBar;

import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import butterknife.ButterKnife;

public class DecisionDetailsFragment extends BaseActivity {

	Kfsd kfsd;
	View view;
	ArrayList<String> circularList;
	ListView listView;
	WebView webView1;
	ProgressBar progressBar;
	

	public DecisionDetailsFragment() {
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



		setHeader(getResources().getString(R.string.decisions), true, true);

	}
	
	
	private void gotoPage(String url){



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

}
