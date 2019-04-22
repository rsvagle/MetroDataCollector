package com.example.DataCollection;

import java.io.File;

public interface IReader {
	
	Readings getReadings(File fileName)  throws Exception;
	
	Study getStudy(File fileName) throws Exception;

}
