package com.example.rssreader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class ShowDescription extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_description);
		
        String theStory = null;
        
        
        Intent startingIntent = getIntent();
        
        if (startingIntent != null)
        {
        	Bundle b = startingIntent.getBundleExtra("android.intent.extra.INTENT");
        	if (b == null)
        	{
        		theStory = "bad bundle?";
        	}
        	else
    		{
        		theStory = b.getString("title") + "\n\n" + b.getString("pubdate") + "\n\n" + b.getString("description").replace('\n',' ') + "\n\nMore information:\n" + b.getString("link");
    		}
        }
        else
        {
        	theStory = "Information Not Found.";
        
        }
        TextView db= (TextView) findViewById(R.id.storybox);
        db.setText(theStory);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_description, menu);
		return true;
	}

}
