package com.example.notetaker.ExternalComm;

import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.os.StrictMode;

public class RSSFeedx {
	
	private static final int HTTP_STATUS_OK = 200;
	private static byte[] buff = new byte[1024];

	public RSSFeedIBM getRSSFeed(String RSSFeedUrl) {

		try {

			// setup the url
			URL url = new URL(RSSFeedUrl);

			// create the factory
			SAXParserFactory factory = SAXParserFactory.newInstance();
			
			// create a parser
			SAXParser parser = factory.newSAXParser();

			// create the reader (scanner)
			XMLReader xmlreader = parser.getXMLReader();
			
			// instantiate our handler
			RSSHandlerIBM theRssHandler = new RSSHandlerIBM();
			
			// assign our handler
			xmlreader.setContentHandler(theRssHandler);
			
			// get our data via the url class
			InputSource is = new InputSource(url.openStream());
			
			// perform the synchronous parse
			xmlreader.parse(is);
			
			// get the results - should be a fully populated RSSFeed instance, or null on error
			return theRssHandler.getFeed();
		} catch (Exception e) {
			// if we have a problem, simply return null
			String errorMsg = e.getMessage();
			return null;
		}
	}
}