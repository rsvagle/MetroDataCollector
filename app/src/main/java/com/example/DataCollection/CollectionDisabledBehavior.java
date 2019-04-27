package com.example.DataCollection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Map;

public class CollectionDisabledBehavior implements IBehavior, Serializable {

    @SerializedName("disabled_behavior_type")
    @Expose
    String disabledBehaviorType = "Collection Disabled";
    
    /**
     * Takes a Site reading array, and item,  and the Site ID and returns false
     * @param siteReadings, i, siteID
     * @return
     * false
     */
    
    @Override
    public boolean addItem(Map<String, Item> siteReadings, Item i, String siteID) {
        return false;
    }
    
    /**
     * Takes a Site and reading and returns false
     * @param s, r
     * @return
     * false
     */

    @Override
    public boolean addReadings(Site s, Readings r) {
        return false;
    }
    
    /**
     * Writes collection disabled to text
     * @param s
     * @return
     * "Collection Disabled"
     */

    @Override
    public String toString(Site s) {
        return "Collection Disabled";
    }
    
    /**
     * 
     * @return
     * the disabled Behavior Type
     */

    @Override
    public String behaviorTypeToString() {
        return disabledBehaviorType;
    }
}
