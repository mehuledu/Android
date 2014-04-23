package com.example.notetaker.ExternalComm;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class CopyOfRSSFeed {
	private static String RSSFeedUrl = "http://rss.news.yahoo.com/rss/entertainment";
	private static final int HTTP_STATUS_OK = 200;
	private static byte[] buff = new byte[1024];
	
	public synchronized String getRSSFeed()
	{
		String returnValue = null;
		
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(RSSFeedUrl);
		
		try
		{
			// execute the request
			HttpResponse response = client.execute(request);
			StatusLine status = response.getStatusLine();
			
			if(status.getStatusCode() != HTTP_STATUS_OK)
			{
				 //TODO:Handle error here
			}
			
			//Process content
			HttpEntity entity = response.getEntity();
			InputStream ist = entity.getContent();
			ByteArrayOutputStream content = new ByteArrayOutputStream();
			
			int readCount = 0;
			while((readCount = ist.read(buff)) != -1)
			{
				content.write(buff, 0, readCount);
			}
			returnValue = new String(content.toByteArray());
		} 
		catch(Exception e)
		{
			Log.e("error", e.getMessage());
		}
		return returnValue;
	}
}
