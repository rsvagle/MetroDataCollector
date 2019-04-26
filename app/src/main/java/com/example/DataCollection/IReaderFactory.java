package com.example.DataCollection;

import java.io.File;

public class IReaderFactory {
	private String fileType ;
	private String myfileName ;
	IReader myReader;

	
	/**
	 * The IReaderFactory method takes a fileName string and creates an IReader.
	 * @param fileName
	 * 
	 * @return
	 * nothing
	 */
	
	public IReaderFactory(String fileName) {
		fileType = getFileExtension(fileName);
		myfileName = fileName;
//		File myFile;
	}
	
	
	/**
	 * The getFileExtension method takes a fileName string and gets the file extension.
	 * @param fileName
	 * 
	 * @return
	 * The file type of the said file. 
	 */

	public String getFileExtension(String fileName) {
	    int dotIndex = fileName.lastIndexOf('.');
	    return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
	}
	
	
	

	public IReader getIReader(){
		if(fileType.equals("json")) {
			myReader = new JSONReader();
		}
		else if(fileType.equals("xml")) {
			myReader = new XMLReader();
		}
		else {
            myReader = new JSONReader();
		}
		return myReader;
	}
}
