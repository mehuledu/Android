package com.example.notetaker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.notetaker.DataAccessBase.DatabaseManager;

public class ListNotesActivity extends Activity {

	private List<Note> notes = new ArrayList<Note>();
	private ListView notesListView;
	private int editingNoteId = -1;

	// The class that opens or creates the database and makes SQL calls to it
	DatabaseManager db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_notes);
		
		//Create control reference
		notesListView = (ListView)findViewById(R.id.notesListview);
		
		Log.i(this.getClass().getName(),"create the database manager object."); 
        db = new DatabaseManager(this);
        
 		Log.i(this.getClass().getName(),"setOnItemClickListener and activate intent."); 
		notesListView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int itemNumber, long id) {
				Intent editNoteIntent = new Intent(view.getContext(),EditNoteActivity.class);
				editNoteIntent.putExtra("Note", notes.get(itemNumber));
				editingNoteId = itemNumber;
				startActivityForResult(editNoteIntent,1);
			}
		});
		
		Log.i(this.getClass().getName(),"Delete: ContextMenu: Register ContextMenu for \"res/menu/context_menu.xml\"."); 
		registerForContextMenu(notesListView);

		populateList();
		
	}

	//Delete: ContextMenu: Define ContextMenu
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.context_menu, menu);
	}
  
	//Delete: ContextMenu: Define delete action
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
		Note note = notes.get(info.position);
		db.deleteRow(note.getId());
		
		Toast.makeText(getBaseContext(), "Done Deleting Note...", Toast.LENGTH_SHORT).show();
		
		populateList();
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_notes, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent editNoteIntent = new Intent(this,EditNoteActivity.class);
		startActivityForResult(editNoteIntent, 1);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_CANCELED)
		{
			return;
		}
		
		if(resultCode == EditNoteActivity.RESULT_DELETE)
		{
			Note note = notes.get(editingNoteId);
			db.deleteRow(note.getId());
			editingNoteId = -1;
			populateList();
		}

		Serializable extra = data.getSerializableExtra("Note");
		if(extra != null)
		{
			Note newNote = (Note)extra;
			if(editingNoteId > -1)
			{
				Log.i(this.getClass().getName(),"Save edited note.");
				db.updateRow(newNote.getId(), newNote.getTitle(), newNote.getNote(), newNote.getDate().toString());
				editingNoteId = -1;
			}
			else 
			{
				Log.i(this.getClass().getName(),"Add new note.");
				db.addRow(newNote.getTitle(), newNote.getNote(), newNote.getDate().toString());
			}
			populateList();
		}
		
	}

	private void populateList() {
		Log.i(this.getClass().getName(),"populateList from DB");
		this.notes = db.getAllRowsAsArrays();
		
		List<String> values = new ArrayList<String>();
				
		for(Note note : notes)
			values.add(note.getTitle());
	
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,android.R.id.text1,values);
		notesListView.setAdapter(adapter);
	}
}