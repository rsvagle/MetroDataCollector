package com.example.DataCollection;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;


public class StudyListAdapter extends ArrayAdapter<Study> {

    private static final String TAG = "StudyListAdapter";

    private Context myContext;
    int myResource;

    public StudyListAdapter(Context context, int resource, List<Study> objects) {
        super(context, resource, objects);
        this.myContext = context;
        this.myResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String studyID = getItem(position).getStudyID();
        String studyName = getItem(position).getStudyName();
        final Study currentStudy = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(myContext);
        convertView = inflater.inflate(myResource, parent, false);

        TextView tvStudyID = convertView.findViewById(R.id.list_study_id);
        TextView tvStudyName = convertView.findViewById(R.id.list_study_name);
        Button btnViewStudyActivity = convertView.findViewById(R.id.view_study_btn);

        tvStudyID.setText(studyID);
        tvStudyName.setText(studyName);
        btnViewStudyActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewStudyIntent = new Intent(myContext, ViewStudyActivity.class);
                viewStudyIntent.putExtra("studyID", currentStudy.getStudyID());
                myContext.startActivity(viewStudyIntent);
            }
        });

        return convertView;
    }
}
