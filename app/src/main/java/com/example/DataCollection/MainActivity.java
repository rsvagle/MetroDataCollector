package com.example.DataCollection;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private static final String STATE_FILEPATH = "STATE_FILE.json";

    private IReaderFactory iReaderFactory;
    private JSONWriter jsonWriter = new JSONWriter();
    private JSONReader jsonReader = new JSONReader();

    private static Record record;
    private final Context myContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Started.");

        /**
         * Try to load the record from internal storage. If it fails, create a blank record.
         */
        try {
            FileInputStream fileInputStream = myContext.openFileInput(STATE_FILEPATH);
            record = jsonReader.loadState(fileInputStream);
        } catch (Exception e) {
            record = Record.getInstance();
            e.printStackTrace();
        }


        /**
         * Initialize Buttons and Views
         */
        Button studyImportButton = findViewById(R.id.ImportDataButton);
        Button recordAddReadingsButton = findViewById(R.id.add_readings_btn);
        Button recordExportButton = findViewById(R.id.exportRecordBtn);
        Button addStudyButton = findViewById(R.id.add_study_btn);
        final ListView studyListView = findViewById(R.id.StudyListView);
        final StudyListAdapter adapter = new StudyListAdapter(this, R.layout.adapter_study_list, record.getStudies());
        studyListView.setAdapter(adapter);


        /**
         * Set buttons
         */

        /**
         *  Import Study from file button
         */
        studyImportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(myContext);
                dialog.setContentView(R.layout.dialog_add_readings);
                dialog.setTitle("Import readings");

                // set the custom dialog components - text, image and button
                TextView askFileNameText = (TextView) dialog.findViewById(R.id.dialog_ask_file_name);
                final EditText getFileNameText = (EditText) dialog.findViewById(R.id.dialog_get_file_name);

                Button dialogCancelButton = (Button) dialog.findViewById(R.id.dialog_cancel_btn);
                Button dialogConfimButton = (Button) dialog.findViewById(R.id.dialog_confirm_btn);
                dialogCancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialogConfimButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String filePath = getFileNameText.getText().toString();
                        try {
                            FileInputStream fileInputStream = myContext.openFileInput(filePath);
                            iReaderFactory = new IReaderFactory(filePath);
                            Study importedStudy = iReaderFactory.getIReader().getStudy(fileInputStream);
                            record.importStudy(importedStudy);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        dialog.dismiss();
                        studyListView.setAdapter(adapter);
                    }
                });
                dialog.show();
            }
        });

        /**
         *  Add Readings from file button
         */
        recordAddReadingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(myContext);
                dialog.setContentView(R.layout.dialog_add_readings);
                dialog.setTitle("Add readings");

                // set the custom dialog components - text, image and button
                TextView askFileNameText = (TextView) dialog.findViewById(R.id.dialog_ask_file_name);
                final EditText getFileNameText = (EditText) dialog.findViewById(R.id.dialog_get_file_name);

                Button dialogCancelButton = (Button) dialog.findViewById(R.id.dialog_cancel_btn);
                Button dialogConfimButton = (Button) dialog.findViewById(R.id.dialog_confirm_btn);
                dialogCancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialogConfimButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String filePath = getFileNameText.getText().toString();
                        try {
                            FileInputStream fileInputStream = myContext.openFileInput(filePath);
                            IReaderFactory fac = new IReaderFactory(filePath);
                            Readings importedReadings = fac.getIReader().getReadings(fileInputStream);
                            record.addReadings(importedReadings);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        dialog.dismiss();
                        studyListView.setAdapter(adapter);
                    }
                });
                dialog.show();
            }
        });

        /**
         *  Manually add a study button
         */
        addStudyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(myContext);
                dialog.setContentView(R.layout.dialog_add_study);
                dialog.setTitle("Add a study");

                // set the custom dialog components - text, image and button
                TextView askIdText = (TextView) dialog.findViewById(R.id.dialog_ask_study_id);
                TextView askNameText = (TextView) dialog.findViewById(R.id.dialog_ask_study_name);
                final EditText getIdText = (EditText) dialog.findViewById(R.id.dialog_get_study_id);
                final EditText getNameText = (EditText) dialog.findViewById(R.id.dialog_get_study_name);

                Button dialogCancelButton = (Button) dialog.findViewById(R.id.dialog_cancel_btn);
                Button dialogConfimButton = (Button) dialog.findViewById(R.id.dialog_confirm_btn);

                dialogCancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialogConfimButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newStudyId = getIdText.getText().toString();
                        String newStudyName = getNameText.getText().toString();
                        Study newStudy = new Study(newStudyId, newStudyName);
                        record.addStudy(newStudy);
                        dialog.dismiss();
                    }
                });

                dialog.show();
                studyListView.setAdapter(adapter);
            }
        });

        /**
         *  Export to file button
         */
        recordExportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(myContext);
                dialog.setContentView(R.layout.dialog_export_to_file);
                dialog.setTitle("Export File");

                // set the custom dialog components - text, image and button
                TextView askFileNameText = (TextView) dialog.findViewById(R.id.dialog_ask_output_file_name);
                final EditText getFileNameText = (EditText) dialog.findViewById(R.id.dialog_get_file_name);

                Button dialogCancelButton = (Button) dialog.findViewById(R.id.dialog_cancel_btn);
                Button dialogConfimButton = (Button) dialog.findViewById(R.id.dialog_confirm_btn);

                dialogCancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialogConfimButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String filePath = getFileNameText.getText().toString();
                        try {
                            jsonWriter.writeToFileRecord(record, filePath, myContext);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    public void onStop() {
        super.onStop();
        /*
            Try to write the record to internal storage
         */
        Log.d(TAG, "onStop: Started");
        try {
            Log.d(TAG, "onStop: WritingToFile");
            jsonWriter.writeToFileRecord(record, STATE_FILEPATH, myContext);
        } catch (Exception e) {
            Log.d(TAG, "onStop: caught exception!");
            e.printStackTrace();
        }
    }

}
