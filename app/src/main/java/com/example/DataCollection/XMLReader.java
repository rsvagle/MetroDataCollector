package com.example.DataCollection;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * 
 * This class responsibility is to read an XML file
 * It has a default constructor
 *
 */

public class XMLReader implements IReader{
	private SAXParserFactory saxParserFactory = null;
	private Study myStudy;
	Readings readings = new Readings();
	
	public XMLReader() {
		saxParserFactory = SAXParserFactory.newInstance();
	}
	
	public void readXML(File file) {
		try {
			SAXParser saxParser = saxParserFactory.newSAXParser();
			XMLSAXParserHandler handler = new XMLSAXParserHandler();
			saxParser.parse(file, handler);
			//Get Item List
			myStudy = handler.getStudy();
			readings.setReadings(handler.getItemList());
			for(Item i: readings.getReadings()) {
				//correction to date and unit in the imported readings
				i.validateDate();
				i.ValidateUnit();
			}
		}
		catch (ParserConfigurationException | SAXException | IOException e) {
		    e.printStackTrace();
		}
	}
	
	/**
	 * Read the XML file
	 */
	public Readings getReadings(File file)  throws Exception{
		this.readXML(file);
		return readings;
	}
	
	/**
	 * This method returns the study imported from the input file
	 * @return
	 */
	public Study getStudy(File file)  throws Exception{
		this.readXML(file);
		myStudy.setSiteForReading(readings);
		myStudy.addReadings(readings);
		return myStudy;
	}
}
