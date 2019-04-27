package com.example.DataCollection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Map;

public class SiteInvalidBehavior implements IBehavior, Serializable {

    @SerializedName("invalid_behavior_type")
    @Expose
    String invalidBehaviorType = "Invalid";

    public boolean addItem(Map<String, Item> siteReadings, Item i, String siteId) {
        return false;
    }

    @Override
    public boolean addReadings(Site s, Readings r) {
        return false;
    }

    @Override
    public String toString(Site s) {
        return "Site has been marked invalid!";
    }

    @Override
    public String behaviorTypeToString() {
        return invalidBehaviorType;
    }
}
