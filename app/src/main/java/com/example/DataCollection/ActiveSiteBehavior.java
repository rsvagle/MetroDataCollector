package com.example.DataCollection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Map;

public class ActiveSiteBehavior implements IBehavior, Serializable {

    @SerializedName("active_behavior_type")
    @Expose
    String activeBehaviorType = "Active";

    @Override
    public boolean addItem(Map<String, Item> siteReadings, Item i, String siteId) {
        if(i.getSiteID().equals(siteId)) {
            siteReadings.putIfAbsent(i.getReadingID(), i);
        }
        return siteReadings.containsValue(i);
    }

    @Override
    public boolean addReadings(Site s, Readings r) {
        return s.addReadings(r);
    }

    @Override
    public String toString(Site s) {
        return s.toString();
    }

    @Override
    public String behaviorTypeToString() {
        return activeBehaviorType;
    }
}
