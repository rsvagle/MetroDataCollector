package com.example.DataCollection;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class ViewSiteActivity extends AppCompatActivity {

    private JSONReader jsonReader = new JSONReader();
    private Site currentSite;
    public static final String TAG = "ViewSiteActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_site);
        Log.d(TAG, "onCreate: Started.");
        currentSite = (Site) getIntent().getSerializableExtra("site");
        final String currentStudyID = (String) getIntent().getSerializableExtra("studyID");
        final Record theRecord = Record.getInstance();

        TextView siteIDTv = findViewById(R.id.site_id_tv);
        siteIDTv.setText("Site ID: " + currentSite.getSiteID());
        TextView siteStatusTv = findViewById(R.id.site_status_tv);
        siteStatusTv.setText("Site Status: " + currentSite.isRecording());
        final TextView siteItemsTv = findViewById(R.id.site_items_view);
        siteItemsTv.setText(currentSite.toString());
        Button addAReading = findViewById(R.id.add_a_reading_btn);

        addAReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rand = new Random();
                String newId = "" + rand.nextInt(100);
                Item newItem = new Item(currentSite.getSiteID(), "", "", newId, 10, 10);
                theRecord.getStudyByID(currentStudyID).getSiteByID(currentSite.getSiteID()).addItem(newItem);
                siteItemsTv.setText(theRecord.getStudyByID(currentStudyID).getSiteByID(currentSite.getSiteID()).toString());
            }
        });
    }

}
