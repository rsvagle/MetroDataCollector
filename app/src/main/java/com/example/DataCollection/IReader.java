package com.example.DataCollection;

import java.io.File;

public interface IReader {
	
	Readings getReadings(String fileName)  throws Exception;
	
	Study getStudy(String fileName) throws Exception;

}
