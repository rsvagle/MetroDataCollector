package com.example.DataCollection;

import java.io.File;

public class IReaderFactory {
<<<<<<< HEAD
	private String fileType ;
	private String myfileName ;
=======
	private String fileType;
	private String myfileName;
>>>>>>> 88f720ba9a35b2ef976c5b570e79aa114ced51e4
	IReader myReader;

	public IReaderFactory(String fileName) {
		fileType = getFileExtension(fileName);
		myfileName = fileName;
//		File myFile;
	}

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
