package com.example.DataCollection;

import java.io.File;
import java.io.InputStream;


/**
 * The IReader method takes user input for readings and setting a study.
 * @param void
 * 
 * @return
 * nothing
 */

public interface IReader {
	
	Readings getReadings(InputStream is)  throws Exception;
	
	Study getStudy(InputStream is) throws Exception;

}
