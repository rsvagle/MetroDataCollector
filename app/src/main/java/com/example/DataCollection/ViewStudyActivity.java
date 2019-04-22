package com.example.DataCollection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ViewStudyActivity extends AppCompatActivity {

    private JSONReader jsonReader = new JSONReader();
    private Study currentStudy;
    public static final String TAG = "ViewStudyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_study);
        Log.d(TAG, "onCreate: Started.");
        currentStudy = (Study) getIntent().getSerializableExtra("study");

        TextView studyName = findViewById(R.id.study_name_textView);
        studyName.setText(currentStudy.getStudyName());
        TextView studyId = findViewById(R.id.study_id_tv);
        studyId.setText("ID: " + currentStudy.getStudyID());
        Button siteAddReadingsButton = findViewById(R.id.add_readings_btn);
        Button exportStudyButton = findViewById(R.id.export_study_btn);
        Button addSiteButton = findViewById(R.id.add_site_btn);
        ListView siteListView = findViewById(R.id.site_list_view);
        SiteListAdapter adapter = new SiteListAdapter(this, R.layout.adapter_site_list, currentStudy.getAllSites());
        siteListView.setAdapter(adapter);
    }

}
