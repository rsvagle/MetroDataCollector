package com.example.DataCollection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private JSONReader jsonReader = new JSONReader();
    private static Record record;
    public static final String TAG = "MainActivity";
    private static final String STATE_FILEPATH = "STATE_FILE.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Started.");
        record = Record.getInstance();
        try {
            record = jsonReader.loadState(STATE_FILEPATH, record);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Button studyImportButton = findViewById(R.id.ImportDataButton);
        Button studyAddReadingsButton = findViewById(R.id.add_readings_btn);
        Button recordExportButton = findViewById(R.id.exportRecordBtn);
        ListView studyListView = findViewById(R.id.StudyListView);
        StudyListAdapter adapter = new StudyListAdapter(this, R.layout.adapter_study_list, record.getStudies());
        studyListView.setAdapter(adapter);

        Study newStudy = new Study("101", "Ryan's Study");
        record.addStudy(newStudy);

        JSONWriter jsonWriter = new JSONWriter();
        try {
            jsonWriter.writeToFile(record, STATE_FILEPATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        studyImportButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            //    importedFile = chooseFile();
//            }
//        });
//
//        studyAddReadingsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        recordExportButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }
}
