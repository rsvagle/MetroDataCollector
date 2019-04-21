package com.example.DataCollection;

import java.io.File;

public class IReaderFactory {
	private File inputFile;
	private String fileType = "";
	IReader myReader;
	
	public IReaderFactory(File file) {
		inputFile = file;
		fileType = getFileExtension(file);
	}
	
	public String getFileExtension(File file) {
	    String fileName = file.getName();
	    int dotIndex = fileName.lastIndexOf('.');
	    return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
	}
	
	public Readings getReadings() throws Exception {
		if(fileType.equals("json")) {
			myReader = new JSONReader();
			return myReader.getReadings(inputFile);
		}
		else if(fileType.equals("xml")) {
			myReader = new XMLReader();
			return myReader.getReadings(inputFile);
		}
		else {
			return null;
		}
	}
	
	public Study getStudy() throws Exception{
		if(fileType.equals("json")) {
			myReader = new JSONReader();
			return myReader.getStudy(inputFile);
		}
		else if(fileType.equals("xml")){
			myReader = new XMLReader();
			return myReader.getStudy(inputFile);
		}
		else {
			return null;
		}
	}
}
