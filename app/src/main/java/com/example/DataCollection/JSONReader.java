package com.example.DataCollection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

/**
 * 
 * JSONFile is a singleton class the handle JSON file
 * It reads, write JSON file into java object
 *
 */
public class JSONReader implements IReader{

	private static FileReader reader = null;
	private static Study myStudy;
	private static Readings myReadings = new Readings();
	
	public JSONReader() {
		
	}

//	private File chooseFile() throws IOException {
//		//Specify the current directory for the file chooser()
//		File currentDir = new File(System.getProperty("user.dir")+"/src");
//		chooser = new JFileChooser(currentDir);
//		return chooser.getSelectedFile();
//	}
	
	public void readFile(File inputFile) throws IOException {
		reader = new FileReader(inputFile);
		Gson myGson = new Gson();
		myReadings = myGson.fromJson(reader, Readings.class);
		reader.close();
		
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
	public Readings getReadings(File inputFile) throws Exception{
		this.readFile(inputFile);
		return myReadings;
	}
	
	public Study getStudy(File inputFile) throws Exception{
		this.readFile(inputFile);
		myStudy = new Study("UnknownStudy","UnknownStudy");
		myStudy.setSiteForReading(myReadings);
		myStudy.addReadings(myReadings);
		return myStudy;
	}
	
	/**
	 * @param fileName
	 * Returns a record that encapsulates the previous state.
	 */
	public Record loadState(String fileName) throws Exception {
		
		final File FILE = new File(fileName);
		
		//Instantiates a BufferReader object that takes the input file as an argument 
		reader = new FileReader(FILE);
		Record myRecord = Record.getInstance();
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new FileReader(FILE));
		
		//check if state file is empty
		if (br.readLine() != null) {
			Study[] importedStudies;
			Gson myGson = new GsonBuilder().setLenient().create();
			importedStudies = myGson.fromJson(reader, Study[].class);
			for(Study s : importedStudies) {
				myRecord.importStudy(s);
			}
		}
		reader.close();
		return myRecord;
	}
}
