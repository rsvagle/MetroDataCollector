package com.example.DataCollection;

import java.io.File;

public class IReaderFactory {
	private String fileType = "";
	private String myfileName = "";
	IReader myReader;
	
	public IReaderFactory(String fileName) {
		fileType = getFileExtension(fileName);
		myfileName = fileName;
	}
	
	public String getFileExtension(String fileName) {
	    int dotIndex = fileName.lastIndexOf('.');
	    return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
	}
	
	public Readings getReadings() throws Exception {
		if(fileType.equals("json")) {
			myReader = new JSONReader();
			return myReader.getReadings(myfileName);
		}
		else if(fileType.equals("xml")) {
			myReader = new XMLReader();
			return myReader.getReadings(myfileName);
		}
		else {
			return null;
		}
	}
	
	public Study getStudy() throws Exception{
		if(fileType.equals("json")) {
			myReader = new JSONReader();
			return myReader.getStudy(myfileName);
		}
		else if(fileType.equals("xml")){
			myReader = new XMLReader();
			return myReader.getStudy(myfileName);
		}
		else {
			return null;
		}
	}
}
