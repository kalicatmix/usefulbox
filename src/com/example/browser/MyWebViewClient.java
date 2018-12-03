package com.example.browser;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Message;
import android.view.KeyEvent;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MyWebViewClient extends WebViewClient {

	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		// TODO Auto-generated method stub
		view.loadUrl(url);
		return false;
		//return super.shouldOverrideUrlLoading(view, url);
	}

	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		// TODO Auto-generated method stub
		super.onPageStarted(view, url, favicon);
	}

	@Override
	public void onPageFinished(WebView view, String url) {
		
		super.onPageFinished(view, url);
	}

	@Override
	public void onLoadResource(WebView view, String url) {
		// TODO Auto-generated method stub
		super.onLoadResource(view, url);
	}

	@Override
	public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
		// TODO Auto-generated method stub
		return super.shouldInterceptRequest(view, url);
	}

	@Override
	@Deprecated
	public void onTooManyRedirects(WebView view, Message cancelMsg,
			Message continueMsg) {
		// TODO Auto-generated method stub
		super.onTooManyRedirects(view, cancelMsg, continueMsg);
	}

	@Override
	public void onReceivedError(WebView view, int errorCode,
			String description, String failingUrl) {
		// TODO Auto-generated method stub
		//super.onReceivedError(view, errorCode, description, failingUrl);
	    if(errorCode==this.ERROR_CONNECT){
	    	Toast.makeText(view.getContext(),"ÍøÂç´íÎó",1000).show();
	    }else if(errorCode==this.ERROR_BAD_URL){
	    	Toast.makeText(view.getContext(), "url´íÎó",1000).show();
	    	if(view.canGoBack()){
	    		view.goBack();
	    	}
	    }else if(errorCode==this.ERROR_UNKNOWN){
	    	Toast.makeText(view.getContext(),"Î´Öª´íÎó",1000).show();
	    }else{
	    	Toast.makeText(view.getContext(), "ÇëÇó´íÎó",1000).show();
	    }
	    view.loadUrl("file:///android_asset/error.html");
	}

	@Override
	public void onFormResubmission(WebView view, Message dontResend,
			Message resend) {
		// TODO Auto-generated method stub
		super.onFormResubmission(view, dontResend, resend);
	}

	@Override
	public void doUpdateVisitedHistory(WebView view, String url,
			boolean isReload) {
		// TODO Auto-generated method stub
		super.doUpdateVisitedHistory(view, url, isReload);
	}

	@Override
	public void onReceivedSslError(WebView view, SslErrorHandler handler,
			SslError error) {
		
		//super.onReceivedSslError(view, handler, error);
		view.loadUrl("file:///android_asset/error.html");
	}

	@Override
	public void onReceivedHttpAuthRequest(WebView view,
			HttpAuthHandler handler, String host, String realm) {
		// TODO Auto-generated method stub
		super.onReceivedHttpAuthRequest(view, handler, host, realm);
	}

	@Override
	public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.shouldOverrideKeyEvent(view, event);
	}

	@Override
	public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
		// TODO Auto-generated method stub
		super.onUnhandledKeyEvent(view, event);
	}

	@Override
	public void onScaleChanged(WebView view, float oldScale, float newScale) {
		// TODO Auto-generated method stub
		super.onScaleChanged(view, oldScale, newScale);
	}

	@Override
	public void onReceivedLoginRequest(WebView view, String realm,
			String account, String args) {
		// TODO Auto-generated method stub
		super.onReceivedLoginRequest(view, realm, account, args);
	}

}
