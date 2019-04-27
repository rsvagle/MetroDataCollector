package com.example.DataCollection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Map;

public class SiteInvalidBehavior implements IBehavior, Serializable {

    @SerializedName("invalid_behavior_type")
    @Expose
    String invalidBehaviorType = "Invalid";
    
    /**
     * takes a map of site readings and item and the site ID and reutrns false
     * @param siteReadings, i, siteID
     * @return
     * false
     */

    public boolean addItem(Map<String, Item> siteReadings, Item i, String siteId) {
        return false;
    }
    
    /**
     * takes the site and the reading and returns false
     * @param s, r
     * @return
     * false
     */

    @Override
    public boolean addReadings(Site s, Readings r) {
        return false;
    }
    
    /**
     * writes "Site has been marked invalid!" to string
     * @return
     * "Site has been marked invalid!"
     */

    @Override
    public String toString(Site s) {
        return "Site has been marked invalid!";
    }
    
    /**
     * @return
     * invalidbehaviorType
     */

    @Override
    public String behaviorTypeToString() {
        return invalidBehaviorType;
    }
}
