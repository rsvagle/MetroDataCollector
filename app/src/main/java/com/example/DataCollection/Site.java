package com.example.DataCollection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Site  implements Serializable {
	private Study study;

	@SerializedName("recording")
	@Expose
	private boolean recording;
	@SerializedName("study_id")
	@Expose
	private String studyID;
	@SerializedName("active")
	@Expose
	private boolean active;
	@SerializedName("site_ID")
	@Expose
	private String siteID;
	@SerializedName("readings")
	@Expose
	private Map<String,Item> siteReadings = new HashMap<String, Item>();
	
	public Site() {
		recording = false;
		active = true;
		studyID = "xxx";
	}
	
	public Site (String id) {
		this();
		siteID = id;
	}
		
	/**
	 * 
	 * @return
	 */
	public String getSiteID() {
		return siteID;
	}

	/**
	 * 
	 * @param siteID
	 */
	public void setSiteID(String siteID) {
		this.siteID = siteID;
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<Item> getItems() {
		ArrayList<Item> myItems = new ArrayList<Item>(siteReadings.values());
		return myItems;
	}
	
	/**
	 * 
	 * @param items
	 */
	public void setItems(ArrayList<Item> items) {
		for(Item i : items) {
			if (i.getSiteID() == this.siteID) {
				siteReadings.put(i.getReadingID(), i);
			}
		}
	}
	
	/**
	 * @param
	 * return the status of a site either start collecting
	 * or end collecting
	 * @return
	 * return true if start collecting and false if collection ended
	 */
	public boolean isRecording() {
		return this.recording;
	}
	
	/**
	 * 
	 * @param bool
	 */
	public void setRecording(boolean bool) {
		this.recording = bool;
	}

	/**
	 *
	 * @return
	 */
	public Study getStudy() {
		return this.study;
	}

	public void setStudy(Study s) {
		this.study = s;
		this.studyID = s.getStudyID();
	}

	/**
	 * 
	 * @return
	 */
	public String getStudyID() {
		return this.studyID;
	}
	
	public void setStudyID(String newStudyID) {
		this.studyID = newStudyID;
	}
	
	/**
	 * @param
	 * addItem: takes a single Item as a parameter, adds it to 
	 * the instance of site's list of items
	 * @return
	 * return true if the new item is added to site
	 */
	public boolean addItem(Item i) {
		if(recording && i.getSiteID().equals(this.getSiteID())) {
			siteReadings.putIfAbsent(i.getReadingID(), i);
		}
		return siteReadings.containsValue(i);
	}
	
	/**
	 * @param
	 * addReadings: takes a Readings as a parameter, add readings to 
	 * the instance of site list of items
	 * @return
	 * return true if the new item is added to site
	 */
	public boolean addReadings(Readings readings) {
		boolean result = false;
		if(this.recording) {
			for(Item item : readings.getReadings()) {
				this.addItem(item);
				result = siteReadings.containsValue(item);
			}
		}
		return result;
	}
	
	/**
	 * 
	 */
	public void invalidateSite() {
		this.active = false;
		this.recording = false;
		siteReadings.clear();
	}
	
	/**
	 * Determines a site to be empty or not
	 * @param
	 * void
	 * @return
	 * Returns a boolean indicating the presence of items in a site's readings
	 */
	public boolean isEmpty() {
		return siteReadings.isEmpty();
	}
	
	/**
	 * 
	 */
	@Override
	public boolean equals(Object object) {
		Site nsite = null;
		if(object instanceof Site) {
			nsite = (Site)object;
		}
		//true if both siteID field are equal
		return this.siteID.equals(nsite.getSiteID());
	}
	
	/**
	 * 
	 */
	@Override
	public String toString() {
		String text = "";
		for(Item i: siteReadings.values()) {
			text += i.toString() + "\n\r";
		}
		return text;
	}
}