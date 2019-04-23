package com.example.DataCollection;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

public class ViewStudyActivity extends AppCompatActivity {

    private JSONReader jsonReader = new JSONReader();
    private Study currentStudy;
    public static final String TAG = "ViewStudyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_study);
        Log.d(TAG, "onCreate: Started.");
        final Record theRecord = Record.getInstance();
        currentStudy = (Study) getIntent().getSerializableExtra("study");

        final Context currentContext = this;
        TextView studyName = findViewById(R.id.study_name_textView);
        studyName.setText(currentStudy.getStudyName());
        TextView studyId = findViewById(R.id.study_id_tv);
        studyId.setText("ID: " + currentStudy.getStudyID());
        Button studyAddReadingsButton = findViewById(R.id.add_readings_btn);
        Button exportStudyButton = findViewById(R.id.export_study_btn);
        Button addSiteButton = findViewById(R.id.add_site_btn);
        final ListView siteListView = findViewById(R.id.site_list_view);
        final SiteListAdapter adapter = new SiteListAdapter(currentContext, R.layout.adapter_site_list, currentStudy.getAllSites());
        siteListView.setAdapter(adapter);

        addSiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(currentContext);
                dialog.setContentView(R.layout.dialog_add_site);
                dialog.setTitle("Add a site");

                // set the custom dialog components - text, image and button
                TextView askIdText = (TextView) dialog.findViewById(R.id.dialog_ask_site_id);
                final EditText getIdText = (EditText) dialog.findViewById(R.id.dialog_get_site_id);

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
                        String newSiteId = getIdText.getText().toString();
                        Site newSite = new Site(newSiteId);
                        newSite.setRecording(true);
                        theRecord.getStudyByID(currentStudy.getStudyID()).addSite(newSite);
                        dialog.dismiss();
                        SiteListAdapter newadapter = new SiteListAdapter(currentContext, R.layout.adapter_site_list, theRecord.getStudyByID(currentStudy.getStudyID()).getAllSites());
                        siteListView.setAdapter(newadapter);
                    }
                });
                dialog.show();
            }
        });

        studyAddReadingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(currentContext);
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
                            FileInputStream fileInputStream = currentContext.openFileInput(filePath);
                            Readings importedReadings = jsonReader.getReadings(fileInputStream);
                            theRecord.getStudyByID(currentStudy.getStudyID()).addReadings(importedReadings);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        dialog.dismiss();
                        SiteListAdapter newadapter = new SiteListAdapter(currentContext, R.layout.adapter_site_list, theRecord.getStudyByID(currentStudy.getStudyID()).getAllSites());
                        siteListView.setAdapter(newadapter);
                    }
                });
                dialog.show();
            }
        });
    }

}
