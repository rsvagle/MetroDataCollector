package com.example.DataCollection;

import android.os.Environment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

/**
 * 
 * JSONFile is a singleton class the handle JSON file
 * It reads, write JSON file into java object
 *
 */
public class JSONReader implements IReader{

	private static Study myStudy;
	private static Readings myReadings = new Readings();
	
	public JSONReader() {
		
	}

	public void readFile(File fileName) throws IOException {
		File myFile = fileName;
		FileInputStream fis = new FileInputStream(myFile);
		InputStreamReader isr = new InputStreamReader(fis);
		Gson myGson = new Gson();
		myReadings = myGson.fromJson(isr, Readings.class);
		isr.close();
		
		// Validate each item
		for(Item i : myReadings.getReadings()) {
			i.validateDate();
			i.ValidateUnit();
		}
	}
	
	/**
	 * Returns a Readings object that contains the collection of deserialized items
	 * @param
	 * @return
	 * A readings object containing the items in the file
	 */	
	public Readings getReadings(File fileName) throws Exception{
		this.readFile(fileName);
		return myReadings;
	}
	
	public Study getStudy(File fileName) throws Exception{
		this.readFile(fileName);
		myStudy = new Study("xxx","UnknownStudy");
		myStudy.setSiteForReading(myReadings);
		myStudy.addReadings(myReadings);
		return myStudy;
	}
	
	/**
	 * @param is
	 * Returns a record that encapsulates the previous state.
	 */
	public Record loadState(InputStream is) throws Exception {

		Record myRecord = Record.getInstance();
		int size = is.available();
		byte[] buffer = new byte[size];
		is.read(buffer);
		is.close();
		String jsonString = new String(buffer, "UTF-8");
		Study[] importedStudies;
		Gson myGson = new GsonBuilder().setLenient().create();
		importedStudies = myGson.fromJson(jsonString, Study[].class);
		for(Study s : importedStudies) {
			myRecord.importStudy(s);
		}
		return myRecord;
	}
}
