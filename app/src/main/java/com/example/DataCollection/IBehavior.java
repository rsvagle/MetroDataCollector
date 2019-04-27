package com.example.DataCollection;

import java.io.Serializable;
import java.util.Map;

public interface IBehavior {

    boolean addItem(Map<String,Item> siteReadings, Item i, String siteID);

    boolean addReadings(Site s, Readings r);

    String toString(Site s);

    String behaviorTypeToString();

}
