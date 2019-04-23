package com.example.DataCollection;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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

        TextView siteIDTv = findViewById(R.id.site_id_tv);
        siteIDTv.setText("Site ID: " + currentSite.getSiteID());
        TextView siteStatusTv = findViewById(R.id.site_status_tv);
        siteStatusTv.setText("Site Status: " + currentSite.isRecording());
        TextView siteItemsTv = findViewById(R.id.site_items_view);
        siteItemsTv.setText(currentSite.getItems().toString());
    }

}
