package com.example.apiinterface;

import java.io.InputStream;
import java.net.ContentHandler;
import java.net.ContentHandlerFactory;
import java.net.HttpURLConnection;

import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import android.os.AsyncTask;

public class UrlHttpsAsyncTask extends AsyncTask<String,Integer,Object>{

	@Override
	protected Object doInBackground(String... params) {
		try {
			TrustManager trust[]=new TrustManager[]{
					new X509TrustManager() {
						
						@Override
						public X509Certificate[] getAcceptedIssuers() {
							// TODO Auto-generated method stub
							return new X509Certificate[]{};
						}
						
						@Override
						public void checkServerTrusted(X509Certificate[] chain, String authType)
								throws CertificateException {
							// TODO Auto-generated method stub
							
						}
						
						
						@Override
						public void checkClientTrusted(X509Certificate[] chain, String authType)
								throws CertificateException {
							// TODO Auto-generated method stub
							
						}
					}
			};
			SSLContext context=SSLContext.getInstance("SSL");
			context.init(null,trust,new SecureRandom());
			HostnameVerifier verifier=new HostnameVerifier() {
				
				@Override
				public boolean verify(String hostname, SSLSession session) {
					// TODO Auto-generated method stub
					return true;
				}
			};
			URL url=new URL(params[0]);
			HttpsURLConnection connection=(HttpsURLConnection) url.openConnection();
			connection.setDefaultSSLSocketFactory(context.getSocketFactory());
			connection.setDefaultHostnameVerifier(verifier);
			connection.setDefaultUseCaches(true);
			connection.setDoInput(true);
			ArrayList<Byte>list=new ArrayList<>();
			InputStream stream=connection.getInputStream();
			int i;
			while((i=stream.read())!=-1){
				list.add((byte) i);
			}
			stream.close();
			connection.disconnect();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
	}

	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
	}
}


