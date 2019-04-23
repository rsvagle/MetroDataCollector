package com.example.DataCollection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Study implements Serializable {
	
	@SerializedName("study_name")
	@Expose
	private String studyName;	
	@SerializedName("study_id")
	@Expose
	private String studyID;	
	@SerializedName("sites")
	@Expose
	private Map<String,Site> associatedSites = new HashMap<String, Site>();
	
	public Study() {
		this.studyID = "";
		this.studyName = "";
	}
  
	public Study(String studyID, String studyName) {
		this.studyID = studyID;
		this.studyName = studyName;		
	}

	public String getStudyName() {
		return studyName;
	}

	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}

	public String getStudyID() {
		return studyID;
	}

	public void setStudyID(String studyId) {
		this.studyID = studyId;
	}
	
	/**
	 * @param
	 * The addSite method takes a Site object and adds it to the study.
	 * @return
	 * void
	 */
	public void addSite(Site site) {
		site.setStudyID(this.getStudyID());
		associatedSites.put(site.getSiteID(), site);
	}

	public void addSiteTwo(String siteID){
		Site newSite = new Site(siteID);
		associatedSites.put(siteID, newSite);
	}
	
	/**
	 * Set all sites in the study to start collecting
	 */
	public void startAllSiteCollection() {
		for(Site s : associatedSites.values()) {
			s.setRecording(true);
		}
	}
	
	/**
	 * End collection for all sites in the study
	 */
	public void endAllSiteCollection() {
		for(Site s: associatedSites.values()) {
			s.setRecording(false);
		}
	}
	
	/**
	 * @param
	 * The addItem method takes a Item object and adds it to the appropriate
	 * site in the study. If the site doesn't exist within the study, create it
	 * @return
	 * A boolean value is returned indicating whether or not the item is in the site
	 */
	public boolean addItem(Item i) {
		if(associatedSites.containsKey(i.getSiteID())) {
			associatedSites.get(i.getSiteID()).addItem(i);
		}
		else {
			Site newSite = new Site(i.getSiteID());
			associatedSites.putIfAbsent(i.getSiteID(), newSite);
			associatedSites.get(i.getSiteID()).addItem(i);
		}
		return associatedSites.get(i.getSiteID()).getItems().contains(i);
	}
	
	/**
	 * @param
	 * The addReadings method takes a Readings object and adds the items to the appropriate
	 * site in the study.
	 * @return
	 * void
	 */
	public void addReadings(Readings r) {
		for(Item i : r.getReadings()) {
			if(associatedSites.containsKey(i.getSiteID())) {
				associatedSites.get(i.getSiteID()).addItem(i);
			}
		}
	}
	
	/**
	 * @param
	 * setSiteForReading method takes a Readings object and sets up a list
	 * of sites associated with each reading object under a study
	 * @return
	 * void
	 */
	public void setSiteForReading(Readings reading) {
		Iterator<Item> itemIterator = reading.getReadings().iterator();
		//Iterate over readings to create site and associate the items to it
		while (itemIterator.hasNext()) {
			Item currentItem = itemIterator.next();
			if(associatedSites.containsKey(currentItem.getSiteID())) {
				associatedSites.get(currentItem.getSiteID()).setRecording(true);
			}
			else if (currentItem.getSiteID() != null) {
				Site currentSite = new Site(currentItem.getSiteID());
				currentSite.setRecording(true);
				currentSite.setStudy(this);
				this.addSite(currentSite);
			}
		}
	}
	
	/**
	 * @param
	 * This method takes an ID string as parameter and return 
	 * a site from study that match the input string ID
	 * @return
	 * a Site is return to the caller
	 */
	public Site getSiteByID(String siteId) {
		return associatedSites.get(siteId);
	}
	
	/**
	 * Return a list of sites in study
	 * @return
	 */
	public ArrayList<Site> getAllSites() {
		ArrayList<Site> mySites = new ArrayList<Site>(associatedSites.values());
		return mySites;
	}
	
	/**
	 * Removes empty sites from the study
	 */
	public void removeEmptySite() {
		for (Site s : associatedSites.values()) {
			if (s.isEmpty()) {
				associatedSites.remove(s.getSiteID());
			} 
		}
	}
	
	/**
	 * Checks another object for equality
	 */
	@Override
	public boolean equals(Object o) {
		Study nstudy = null;
		if(o instanceof Study) {
			nstudy = (Study)o;
		}
		return this.studyID.equals(nstudy.getStudyID()) && this.studyName.equals(nstudy.getStudyName());
	}
	
	/**
	 * Returns a string representation of the study
	 */
	@Override
	public String toString() {
		String text = "";
		for(Site s : associatedSites.values()) {
			text += s.toString() + "\n";
		}
		return "\nStudy_ID: " + this.studyID +"\nStudy_Name: "+ this.studyName +"\n" + text;
	}

}