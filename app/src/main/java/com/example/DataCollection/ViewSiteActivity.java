package com.example.DataCollection;

import android.app.Dialog;
import android.content.Context;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Random;

public class ViewSiteActivity extends AppCompatActivity {

    public static final String TAG = "ViewSiteActivity";
    private static final String STATE_FILEPATH = "STATE_FILE.json";

    private IReaderFactory iReaderFactory;
    private JSONWriter jsonWriter = new JSONWriter();
    private Site currentSite;

    private final Record theRecord = Record.getInstance();
    private final Context myContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_site);
        Log.d(TAG, "onCreate: Started.");

        currentSite = (Site) getIntent().getSerializableExtra("site");
        final String currentStudyID = (String) getIntent().getSerializableExtra("studyID");

        Button addReadingsButton = findViewById(R.id.add_readings_btn);
        Button addAReadingButton = findViewById(R.id.add_a_reading_btn);
        Button exportSiteButton = findViewById(R.id.export_site_btn);

        TextView siteIDTv = findViewById(R.id.site_id_tv);
        siteIDTv.setText("Site ID: " + currentSite.getSiteID());

        TextView siteStatusTv = findViewById(R.id.site_status_tv);
        siteStatusTv.setText("Site Status: " + currentSite.isRecording());

        final TextView siteItemsTv = findViewById(R.id.site_items_view);
        siteItemsTv.setText(currentSite.toString());


        /**
         * Manually add a reading button
         */
        addAReadingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(myContext);
                dialog.setContentView(R.layout.dialog_add_a_reading);
                dialog.setTitle("Add a reading");

                // set the custom dialog components - text, image and button
                final EditText getReadingIdText = (EditText) dialog.findViewById(R.id.dialog_get_a_reading_id);
                final EditText getTypeText = (EditText) dialog.findViewById(R.id.dialog_get_a_reading_type);
                final EditText getReadingValue = (EditText) dialog.findViewById(R.id.dialog_get_a_reading_value);
                final EditText getReadingUnit = (EditText) dialog.findViewById(R.id.dialog_get_a_reading_unit);

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
                        String newReadingId = getReadingIdText.getText().toString();
                        String newReadingType = getTypeText.getText().toString();
                        Double newReadingValue = Double.parseDouble(getReadingValue.getText().toString());
                        String newReadingUnit = getReadingUnit.getText().toString();
                        Date c = Calendar.getInstance().getTime();
                        long currentTime = c.getTime();
                        Item newItem = new Item(currentSite.getSiteID(), newReadingType, newReadingUnit, newReadingId, newReadingValue, currentTime);
                        theRecord.getStudyByID(currentStudyID).getSiteByID(currentSite.getSiteID()).addItem(newItem);
                        siteItemsTv.setText(theRecord.getStudyByID(currentStudyID).getSiteByID(currentSite.getSiteID()).toString());
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        /**
         * Add readings from file button
         */
        addReadingsButton.setOnClickListener(new View.OnClickListener() {
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
                            iReaderFactory = new IReaderFactory(filePath);
                            Readings importedReadings = iReaderFactory.getIReader().getReadings(fileInputStream);
                            theRecord.getStudyByID(currentStudyID).getSiteByID(currentSite.getSiteID()).addReadings(importedReadings);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        siteItemsTv.setText(theRecord.getStudyByID(currentStudyID).getSiteByID(currentSite.getSiteID()).toString());
                        dialog.dismiss();

                    }
                });
                dialog.show();
            }
        });

        /**
         * Export Site button
         */
        exportSiteButton.setOnClickListener(new View.OnClickListener() {
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
                            jsonWriter.writeToFileObject(theRecord.getStudyByID(currentStudyID).getSiteByID(currentSite.getSiteID()), filePath, myContext);
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
            jsonWriter.writeToFileRecord(theRecord, STATE_FILEPATH, this);
        } catch (Exception e) {
            Log.d(TAG, "onStop: caught exception!");
            e.printStackTrace();
        }
    }
}
