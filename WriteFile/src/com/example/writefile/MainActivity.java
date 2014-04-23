package com.example.writefile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	Button btnWriteFile;
	Button btnReadFile;
	EditText txtData;
	String FileLocation = "/sdcard/mysdfile.txt";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnWriteFile = (Button) findViewById(R.id.button_w);
		btnReadFile = (Button) findViewById(R.id.button_r);
		txtData = (EditText) findViewById(R.id.editText1);

		btnWriteFile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// write on SD card file data in the text box
				try {
					File myFile = new File(FileLocation);
					myFile.createNewFile();
					FileOutputStream fOut = new FileOutputStream(myFile);
					OutputStreamWriter myOutWriter = new OutputStreamWriter(
							fOut);
					myOutWriter.append(txtData.getText());
					myOutWriter.close();
					fOut.close();
					Toast.makeText(getBaseContext(),
							"Done writing SD 'mysdfile.txt'",
							Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					Toast.makeText(getBaseContext(), e.getMessage(),
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		btnReadFile.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// write on SD card file data in the text box
				try {
					File myFile = new File(FileLocation);
					FileInputStream fIn = new FileInputStream(myFile);
					BufferedReader myReader = new BufferedReader(
							new InputStreamReader(fIn));
					String aDataRow = "";
					String aBuffer = "";
					while ((aDataRow = myReader.readLine()) != null) {
						aBuffer += aDataRow + "\n";
					}
					txtData.setText(aBuffer);
					txtData.setBackgroundColor(Color.YELLOW);
					myReader.close();
					Toast.makeText(getBaseContext(),
							"Done reading SD 'mysdfile.txt'",
							Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					Toast.makeText(getBaseContext(), e.getMessage(),
							Toast.LENGTH_SHORT).show();
				}
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
