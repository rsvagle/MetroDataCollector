package com.example.DataCollection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.DataCollection.R;

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
        Study currentStudy = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(myContext);
        convertView = inflater.inflate(myResource, parent, false);

        TextView tvStudyID = (TextView) convertView.findViewById(R.id.studyID);
        TextView tvStudyName = (TextView) convertView.findViewById(R.id.studyName);
        Button btnViewStudy = (Button) convertView.findViewById(R.id.viewStudyBtn);

        tvStudyID.setText(studyID);
        tvStudyName.setText(studyName);
        btnViewStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }
}
