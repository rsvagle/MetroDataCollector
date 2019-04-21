package com.example.DataCollection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;

public class JSONWriter {

    File outputFile;
    /**
     * WriteToFile method takes as a parameters a list of sites
     * and a file name. It write the sites in the list to a file on the disk
     */
    public void writeToFile(Record studyRecord, String outputFileName) throws Exception{

        //path and construct of the output file
        outputFile = new File(outputFileName);

        //Instantiate a PrintWriter object
        FileWriter writer = new FileWriter(outputFile);

        //Write JSON object in pretty format
        Gson myGson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        //new instance of JSON Object that will contains iterations of study
        JsonArray jObject = new JsonArray();
        Iterator<Study> it = studyRecord.iterator();
        while(it.hasNext()) {
            jObject.add(myGson.toJsonTree(it.next()));
        }

        String jsonString = myGson.toJson(jObject);
        writer.write(jsonString);
        writer.close();
    }
}
