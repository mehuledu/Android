package com.example.rssreader.ExternalComm;

import java.util.List;
import java.util.Vector;

public class RSSFeedIBM {
	private String _title = null;
	private String _pubdate = null;
	private int _itemcount = 0;
	private List<RSSItemIBM> _itemlist;

	RSSFeedIBM() {
		_itemlist = new Vector<RSSItemIBM>(0);
	}

	int addItem(RSSItemIBM item) {
		_itemlist.add(item);
		_itemcount++;
		return _itemcount;
	}

	public RSSItemIBM getItem(int location) {
		return _itemlist.get(location);
	}

	public List<RSSItemIBM> getAllItems() {
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

	public String getTitle() {
		return _title;
	}

	public String getPubDate() {
		return _pubdate;
	}

}
