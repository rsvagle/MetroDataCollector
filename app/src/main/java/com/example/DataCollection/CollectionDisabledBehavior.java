package com.example.DataCollection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Map;

public class CollectionDisabledBehavior implements IBehavior, Serializable {

    @SerializedName("disabled_behavior_type")
    @Expose
    String disabledBehaviorType = "Collection Disabled";

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
        return "Collection Disabled";
    }

    @Override
    public String behaviorTypeToString() {
        return disabledBehaviorType;
    }
}
