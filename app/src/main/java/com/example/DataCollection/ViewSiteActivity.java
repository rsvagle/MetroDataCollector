package com.example.DataCollection;

import android.app.Dialog;
import android.content.Context;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;


public class ViewSiteActivity extends AppCompatActivity {

    public static final String TAG = "ViewSiteActivity";
    private static final String STATE_FILEPATH = "STATE_FILE.json";

    private IReaderFactory iReaderFactory;
    private JSONWriter jsonWriter = new JSONWriter();
    private Site currentSite;

    private final Record theRecord = Record.getInstance();
    private final Context myContext = this;

    Button dialogCancelButton;
    Button dialogConfirmButton;

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
        String siteID = String.format("Site ID: %s", currentSite.getSiteID());
        siteIDTv.setText(siteID);

        TextView siteStatusTv = findViewById(R.id.site_status_tv);
        String siteStatus = String.format("Site Status: %s", currentSite.isRecording());
        siteStatusTv.setText(siteStatus);

        final TextView siteItemsTv = findViewById(R.id.site_items_view);
        siteItemsTv.setMovementMethod(new ScrollingMovementMethod());
        siteItemsTv.setText(currentSite.toString());


        /*
         * Manually add a reading button
         */
        addAReadingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(myContext);
                dialog.setContentView(R.layout.dialog_add_a_reading);
                dialog.setTitle("Add a reading");

                /*
                 * set the custom dialog components - text, image and button
                 */
                final EditText getReadingIdText = dialog.findViewById(R.id.dialog_get_a_reading_id);
                final EditText getTypeText = dialog.findViewById(R.id.dialog_get_a_reading_type);
                final EditText getReadingValue = dialog.findViewById(R.id.dialog_get_a_reading_value);
                final EditText getReadingUnit = dialog.findViewById(R.id.dialog_get_a_reading_unit);

                dialogCancelButton = dialog.findViewById(R.id.dialog_cancel_btn);
                dialogConfirmButton = dialog.findViewById(R.id.dialog_confirm_btn);
                dialogCancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "Operation Cancelled!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                dialogConfirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newReadingId = getReadingIdText.getText().toString();
                        String newReadingType = getTypeText.getText().toString();
                        Double newReadingValue;
                        if (!getReadingValue.getText().toString().equals("")) {
                            newReadingValue = Double.parseDouble(getReadingValue.getText().toString());
                        } else {
                            newReadingValue = 0d;
                        }
                        String newReadingUnit = getReadingUnit.getText().toString();
                        Date c = Calendar.getInstance().getTime();
                        long currentTime = c.getTime();
                        if (!newReadingId.equals("") && !newReadingType.equals("") && !newReadingUnit.equals("")){
                            Item newItem = new Item(
                                    currentSite.getSiteID(),
                                    newReadingType,
                                    newReadingUnit,
                                    newReadingId,
                                    newReadingValue,
                                    currentTime);
                        theRecord.getStudyByID(currentStudyID).getSiteByID(currentSite.getSiteID()).addItem(newItem);
                        Toast.makeText(getApplicationContext(), "Reading Created Successfully!",Toast.LENGTH_SHORT ).show();
                        siteItemsTv.setText(theRecord.getStudyByID(currentStudyID).getSiteByID(currentSite.getSiteID()).toString());
                        } else {
                            Toast.makeText(getApplicationContext(), "Please Provide data in all the fields",Toast.LENGTH_LONG).show();
                        }
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        /*
         * Add readings from file button
         */
        addReadingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(myContext);
                dialog.setContentView(R.layout.dialog_add_readings);
                dialog.setTitle("Add readings");

                /*
                 * set the custom dialog components - text, image and button
                 */
//                TextView askFileNameText = dialog.findViewById(R.id.dialog_ask_file_name);
                final EditText getFileNameText = dialog.findViewById(R.id.dialog_get_file_name);

                dialogCancelButton = dialog.findViewById(R.id.dialog_cancel_btn);
                dialogConfirmButton = dialog.findViewById(R.id.dialog_confirm_btn);
                dialogCancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "Operation Cancelled!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                dialogConfirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String filePath = getFileNameText.getText().toString();
                        try {
                            FileInputStream fileInputStream = myContext.openFileInput(filePath);
                            iReaderFactory = new IReaderFactory(filePath);
                            Readings importedReadings = iReaderFactory.getIReader().getReadings(fileInputStream);
                            theRecord.getStudyByID(currentStudyID).getSiteByID(currentSite.getSiteID()).addReadings(importedReadings);
                            Toast.makeText(getApplicationContext(), "Readings added Successfully!", Toast.LENGTH_SHORT).show();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "File Not Found!", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            siteItemsTv.setText(theRecord.getStudyByID(currentStudyID).getSiteByID(currentSite.getSiteID()).toString());
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();
            }
        });

        /*
         * Export Site button
         */
        exportSiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(myContext);
                dialog.setContentView(R.layout.dialog_export_to_file);
                dialog.setTitle("Export File");

                /*
                 * set the custom dialog components - text, image and button
                 */
//                TextView askFileNameText = dialog.findViewById(R.id.dialog_ask_output_file_name);
                final EditText getFileNameText = dialog.findViewById(R.id.dialog_get_file_name);

                dialogCancelButton = dialog.findViewById(R.id.dialog_cancel_btn);
                dialogConfirmButton = dialog.findViewById(R.id.dialog_confirm_btn);

                dialogCancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "Operation Cancelled!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                dialogConfirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String filePath = getFileNameText.getText().toString();
                        try {
                            jsonWriter.writeToFileObject(theRecord.getStudyByID(currentStudyID).getSiteByID(currentSite.getSiteID()), filePath, myContext);
                            Toast.makeText(getApplicationContext(), "Site Successfully Exported!", Toast.LENGTH_SHORT).show();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "File Not Found!", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            dialog.dismiss();
                        }
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
