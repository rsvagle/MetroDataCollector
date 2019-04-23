package com.example.DataCollection;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import java.io.File;
import java.io.FileOutputStream;

public class JSONWriter {

    public static final String TAG = "JSONWriter";
    public JSONWriter(){

    }
    /**
     * WriteToFile method takes as a parameters a list of sites
     * and a file name. It write the sites in the list to a file on the disk
     */
    public void writeToFile(Record studyRecord, File outputFile) throws Exception{

        //path and construct of the output file
        File myOutputFile = outputFile;
        FileOutputStream fos = new FileOutputStream(myOutputFile);

        //Write JSON object in pretty format
        Gson myGson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        //new instance of JSON Object that will contains iterations of study
        JsonArray jObject = new JsonArray();
        for(Study s : studyRecord.getStudies()){
            jObject.add(myGson.toJsonTree(s));
        }

        String jsonString = myGson.toJson(jObject);
        fos.write(jsonString.getBytes());
        fos.close();
    }

    public void writeToFileRecord(Record studyRecord, String outputFileName, Context context) throws Exception{
        //path and construct of the output file
        FileOutputStream fos;
        Log.d(TAG, "create File Output Stream");

        String myJson = "hi";
        Log.d(TAG, "create String");

        Gson myGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        JsonArray jObject = new JsonArray();
        for(Study s : studyRecord.getStudies()){
            jObject.add(myGson.toJsonTree(s));
        }
        myJson = myGson.toJson(jObject);
        Log.d(TAG, "JSON Object made");

        fos = context.openFileOutput(outputFileName, Context.MODE_PRIVATE);
        Log.d(TAG, "FileOutput is open");
        fos.write(myJson.getBytes());
        Log.d(TAG, "File is written");
        fos.close();
    }
}
