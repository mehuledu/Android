package com.example.notetaker;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Note implements Serializable {
	public Note(int id, String title, String note, Date date) {
		super();
		this.id = id;
		this.title = title;
		this.note = note;
		this.date = date;
	}
	private int id;
	private String title;
	private String note;
	private Date date;
	public int getId() {
		return id;
	}
	public int SetId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}