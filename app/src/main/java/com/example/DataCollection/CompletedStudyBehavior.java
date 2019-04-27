package com.example.DataCollection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Map;

public class CompletedStudyBehavior implements IBehavior, Serializable {

    @SerializedName("completed_behavior_type")
    @Expose
    String completedBehaviorType = "Complete";

    @Override
    public boolean addItem(Map<String, Item> siteReadings, Item i, String siteID) {
        return false;
    }

    @Override
    public boolean addReadings(Site s, Readings r) {
        return false;
    }

    @Override
    public String toString(Site s) {
        return "Completed Study\n" + s.toString();
    }

    @Override
    public String behaviorTypeToString() {
        return completedBehaviorType;
    }
}
