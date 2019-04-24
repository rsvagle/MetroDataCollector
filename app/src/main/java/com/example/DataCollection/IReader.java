package com.example.DataCollection;

import java.io.File;
import java.io.InputStream;

public interface IReader {
	
	Readings getReadings(InputStream is)  throws Exception;
	
	Study getStudy(InputStream is) throws Exception;

}
