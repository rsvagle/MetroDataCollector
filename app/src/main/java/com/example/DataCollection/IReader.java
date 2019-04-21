package com.example.DataCollection;

import java.io.File;

public interface IReader {
	
	public Readings getReadings(File inputFile)  throws Exception;
	
	public Study getStudy(File inputFile) throws Exception;

}
