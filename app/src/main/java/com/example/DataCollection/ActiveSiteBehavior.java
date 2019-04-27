package com.example.DataCollection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Map;

public class ActiveSiteBehavior implements IBehavior, Serializable {

    @SerializedName("active_behavior_type")
    @Expose
    String activeBehaviorType = "Active";

    /**
     * Checks if site contains a certain reading.
     * @param siteReadings, i, siteId
     * 
     * @return
     * true if site contains reading
     */
    
    @Override
    public boolean addItem(Map<String, Item> siteReadings, Item i, String siteId) {
        if(i.getSiteID().equals(siteId)) {
            siteReadings.putIfAbsent(i.getReadingID(), i);
        }
        return siteReadings.containsValue(i);
    }

    /**
     * Adds a reading to the site
     * @return
     * the reading in the site.
     */
    
    @Override
    public boolean addReadings(Site s, Readings r) {
        return s.addReadings(r);
    }

    /**
     * 
     * @param s
     * @return
     * The current site to text
     */
    
    @Override
    public String toString(Site s) {
        return s.toString();
    }
    
    /**
     * 
     * @return
     * the active Behavior Type
     */

    @Override
    public String behaviorTypeToString() {
        return activeBehaviorType;
    }
}
