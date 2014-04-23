package com.example.notetaker.ExternalComm;

import java.util.List;
import java.util.Vector;

public class RSSFeedIBM {
	private String _title = null;
	private String _pubdate = null;
	private int _itemcount = 0;
	private List<RSSItemIBM> _itemlist;

	RSSFeedIBM() {
		_itemlist = new Vector(0);
	}

	int addItem(RSSItemIBM item) {
		_itemlist.add(item);
		_itemcount++;
		return _itemcount;
	}

	RSSItemIBM getItem(int location) {
		return _itemlist.get(location);
	}

	public List getAllItems() {
		return _itemlist;
	}

	int getItemCount() {
		return _itemcount;
	}

	void setTitle(String title) {
		_title = title;
	}

	void setPubDate(String pubdate) {
		_pubdate = pubdate;
	}

	String getTitle() {
		return _title;
	}

	String getPubDate() {
		return _pubdate;
	}

}
