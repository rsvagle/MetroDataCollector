package com.example.DataCollection;

import java.util.Map;

/**
 * IBehavior defines a family of methods the must be implemented by
 * site behavior patterns
 */
public interface IBehavior {

    boolean addItem(Map<String,Item> siteReadings, Item i, String siteID);

    boolean addReadings(Site s, Readings r);

    String toString(Site s);

    String behaviorTypeToString();

}
