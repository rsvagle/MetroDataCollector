package com.example.DataCollection;

import java.io.InputStream;


/**
 * IReader defines the set of functions that readers must implement
 * to be considered an IReader
 */

public interface IReader {
	
	Readings getReadings(InputStream is) throws Exception;
	
	Study getStudy(InputStream is) throws Exception;

}
