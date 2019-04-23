package com.example.DataCollection;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

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
        Button siteAddReadingsButton = findViewById(R.id.add_readings_btn);
        Button exportStudyButton = findViewById(R.id.export_study_btn);
        Button addSiteButton = findViewById(R.id.add_site_btn);
        final ListView siteListView = findViewById(R.id.site_list_view);
        final SiteListAdapter adapter = new SiteListAdapter(currentContext, R.layout.adapter_site_list, currentStudy.getAllSites());
        siteListView.setAdapter(adapter);

        addSiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rand = new Random();
                String newId = "" + rand.nextInt(100);
                Site newSite = new Site(newId);
                newSite.setRecording(true);
                theRecord.getStudyByID(currentStudy.getStudyID()).addSite(newSite);
                SiteListAdapter newadapter = new SiteListAdapter(currentContext, R.layout.adapter_site_list, theRecord.getStudyByID(currentStudy.getStudyID()).getAllSites());
                siteListView.setAdapter(newadapter);
            }
        });
    }

}
