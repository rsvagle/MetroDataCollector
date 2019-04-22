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

public class SiteListAdapter extends ArrayAdapter<Site> {

    private static final String TAG = "SiteListAdapter";

    private Context myContext;
    int myResource;

    public SiteListAdapter(Context context, int resource, List<Site> objects) {
        super(context, resource, objects);
        this.myContext = context;
        this.myResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String siteID = getItem(position).getSiteID();
        Site currentSite = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(myContext);
        convertView = inflater.inflate(myResource, parent, false);

        TextView tvSiteID = (TextView) convertView.findViewById(R.id.list_site_id);
        TextView tvSiteStatus = (TextView) convertView.findViewById(R.id.list_site_status);
        Button btnViewSite = (Button) convertView.findViewById(R.id.view_site_btn);

        tvSiteID.setText(siteID);
        tvSiteStatus.setText(currentSite.isRecording() + "");
        btnViewSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewSiteIntent = new Intent(getContext(), ViewSiteActivity.class);
                myContext.startActivity(viewSiteIntent);
            }
        });

        return convertView;
    }
}
