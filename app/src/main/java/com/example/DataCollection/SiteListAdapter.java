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
    
    /**
     * Adapts the site list to android
     * @param context
     * @param resource
     * @param objects
     */

    public SiteListAdapter(Context context, int resource, List<Site> objects) {
        super(context, resource, objects);
        this.myContext = context;
        this.myResource = resource;
    }
    
    /**
     * Sets the view for the app
     * @param position
     * @param convertView
     * @param parent
     * @return
     * view settings
     */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String siteID = getItem(position).getSiteID();
        final Site currentSite = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(myContext);
        convertView = inflater.inflate(myResource, parent, false);

        TextView tvStudyID = convertView.findViewById(R.id.list_site_study_id);
        TextView tvSiteID =  convertView.findViewById(R.id.list_site_id);
        TextView tvSiteStatus = convertView.findViewById(R.id.list_site_status);
        Button btnViewSite = convertView.findViewById(R.id.view_site_btn);

        tvStudyID.setText(currentSite.getStudyID());
        tvSiteID.setText(siteID);
        tvSiteStatus.setText(currentSite.getMyBehavior().behaviorTypeToString());
        btnViewSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewSiteIntent = new Intent(getContext(), ViewSiteActivity.class);
                viewSiteIntent.putExtra("siteID", currentSite.getSiteID());
                viewSiteIntent.putExtra("studyID", currentSite.getStudyID());
                myContext.startActivity(viewSiteIntent);
            }
        });

        return convertView;
    }
}
