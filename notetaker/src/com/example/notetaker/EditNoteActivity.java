package com.example.notetaker;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditNoteActivity extends Activity {

	public static final int RESULT_DELETE = -500;
	private boolean isInEditMode = true;
	private boolean isAddingNote = true;
	private int id = -1;
	private DateFormat dateFormat;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_note);
		
		final Button saveButton= (Button)findViewById(R.id.saveButton);
		final Button cancelButton= (Button)findViewById(R.id.cancelButton);
		final EditText titleEditText = (EditText)findViewById(R.id.titleEditText);
		final EditText noteEditText = (EditText)findViewById(R.id.noteEditText);
		final TextView dateTextView = (TextView)findViewById(R.id.dateTextView);
		dateTextView.setEnabled(false);
		
		Serializable extra = getIntent().getSerializableExtra("Note");
		if(extra!=null)
		{
			Note note = (Note)extra;
			
			id = note.getId();
			
			titleEditText.setText(note.getTitle());
			noteEditText.setText(note.getNote());
			
			dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss a");
			String date = dateFormat.format(note.getDate());
			dateTextView.setText(date);
			
			isInEditMode = false;
			titleEditText.setEnabled(false);
			noteEditText.setEnabled(false);
			saveButton.setText("Edit");
			
			isAddingNote = false;
		}
		else
		{
			dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss a");
			dateTextView.setText(dateFormat.format(new Date()));
		}
		
		cancelButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				setResult(RESULT_CANCELED, new Intent());
				finish();
			}
			
		});
		
		saveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(isInEditMode)
				{
					//Build Return Result
					Intent returnIntent = new Intent();
					Note note = new Note(id, titleEditText.getText().toString(), noteEditText.getText().toString(),	Calendar.getInstance().getTime());
					returnIntent.putExtra("Note", note);
					setResult(RESULT_OK,returnIntent);
					finish();
				}
				else
				{
					isInEditMode = true;
					saveButton.setText("Save");
					titleEditText.setEnabled(true);
					noteEditText.setEnabled(true);
				}
			}
		});
	}

	//Delete: Confirmation Message
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder.setMessage(R.string.are_you_sure_you_want_to_delete_this_note_it_can_t_be_undone_);
		builder.setTitle(R.string.confirm_delete);
		builder.setIcon(R.drawable.ic_launcher);
		
		builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent returnIntent = new Intent();
				setResult(RESULT_DELETE,returnIntent);
				finish();
			}
		});
		
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		
		builder.create().show();
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		if(isAddingNote)
		{
			menu.removeItem(R.id.deleteNote);
		}
		return true;
	}

}
