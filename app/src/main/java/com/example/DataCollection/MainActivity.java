package com.example.DataCollection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private File importedFile;
    private JSONReader jsonReader = new JSONReader();
    private static Record record = null;
    public static final String TAG = "MainActivity";
    private static final String STATE_FILE = "STATE_FILE.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Started.");
        try {
            record = jsonReader.loadState(STATE_FILE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Button studyImportButton = findViewById(R.id.ImportDataButton);
        Button studyAddReadingsButton = findViewById(R.id.AddReadingsButton);
        Button recordExportButton = findViewById(R.id.exportRecordBtn);
        ListView studyListView = findViewById(R.id.StudyListView);
        StudyListAdapter adapter = new StudyListAdapter(this, R.layout.adapter_view_layout, record.getStudies());
        studyListView.setAdapter(adapter);

        studyImportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            //    importedFile = chooseFile();
            }
        });

        studyAddReadingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        recordExportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
