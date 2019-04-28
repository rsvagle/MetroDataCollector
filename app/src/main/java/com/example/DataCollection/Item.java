package com.example.DataCollection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * Item class represent the reading object of the site
 * It keeps track of the attributes relating to a particular 
 * reading at a specified site.
 *
 */

public class Item implements Serializable {
	@SerializedName("site_id")
	@Expose
	private String siteID;
	
	@SerializedName("reading_type")
	@Expose
	private String readingType;
	
	@SerializedName("reading_id")
	@Expose
	private String readingID;
	
	@SerializedName("reading_value")
	@Expose
	private double readingValue;
	
	@SerializedName("reading_unit")
	@Expose
	private String unit = "";
	
	@SerializedName("reading_date")
	@Expose
	private long readingDate;

	//The constructors of the Item class
	public Item() {
		
	}
	
	/**
	 * Item takes portions of the data and creates and Item object with them 
	 * @param siteID
	 * @param readingType
	 * @param unit
	 * @param readingID
	 * @param readingValue
	 * @param readingDate
	 * @return
	 *
	 */

	public Item(String siteID, String readingType, String unit, String readingID, double readingValue, long readingDate) {
		this.siteID = siteID;
		this.readingType = readingType;
		this.readingID = readingID;
		this.readingValue = readingValue;
		this.unit = unit;
		this.readingDate = readingDate;	
	}

	
	/**
	 * getSiteID gets site ID number
	 * @return
	 * siteID
	 */
	public String getSiteID() {
		return siteID;
	}
	
	/**
	 * setSiteID sets site ID number 
	 * @param siteID
	 * @return
	 * void
	 */

	public void setSiteID(String siteID) {
		this.siteID = siteID;
	}
	
	/**
	 * getReadingType gets the type of reading the item needs.
	 * @return
	 * readingType
	 */

	public String getReadingType() {
		return readingType;
	}
	
	/**
	 * setReadingType sets the type of reading in the item.  
	 * @param readingType
	 * @return
	 * void
	 */

	public void setReadingType(String readingType) {
		this.readingType = readingType;
	}
	
	
	/**
	 * getReadingID gets the ID number of the item.
	 * @return
	 * readingID
	 */

	public String getReadingID() {
		return readingID;
	}

	/**
	 * setReadingID sets the reading ID number of the item.  
	 * @param readingID
	 * @return
	 * void
	 */
	
	public void setReadingID(String readingID) {
		this.readingID = readingID;
	}
	
	/**
	 * getReadingValue gets the value of the data in the item.
	 * @return readingValue
	 */

	public double getReadingValue() {
		return readingValue;
	}

	
	/**
	 * setReadingValue sets the value of the data in the item.  
	 * @param readingValue
	 * @return
	 * void
	 */
	
	public void setReadingValue(double readingValue) {
		this.readingValue = readingValue;
	}

	/**
	 * getReadingDate gets the date of the data entry in the item.
	 * @return
	 * readingDate
	 */
	
	public long getReadingDate() {
		return readingDate;
	}
	
	/**
	 * setReadingDate sets the date of the data entry in the item.  
	 * @param readingDate
	 * @return
	 * void
	 */

	public void setReadingDate(long readingDate) {
		this.readingDate = readingDate;
	}
	
	/**
	 * getUnit gets the unit of measurement of the data entry in the item.
	 * @return
	 * unit
	 */
	
	public String getUnit() {
		return unit;
	}
	
	/**
	 * setUnit sets the unit of measurement of the data entry in the item.  
	 * @param unit
	 * @return
	 * void
	 */

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	/**
	 * equals method checks if two items are the same
	 * @param object
	 * @return
	 * whether or not the items are the same.
	 */

	@Override
	public boolean equals(Object object) {
		Item nitem = null;
		if(object instanceof Item) {
			nitem = (Item)object;
		}
		boolean equalSiteID = this.siteID.equals(nitem.getSiteID());
		boolean equalReadingType = this.readingType.equals(nitem.getReadingType());
		boolean equalReadingID = this.readingID.equals(nitem.getReadingID());
		boolean equalReadingValue = this.readingValue == nitem.getReadingValue();
		//return true if equal and false other wise
		return equalSiteID && equalReadingType && equalReadingID && equalReadingValue;
	}
	
	/**
	 * toString method writes item to string
	 * @return
	 * void
	 */
	
	@Override
	public String toString() {
		return "Reading Id: " + readingID + "\n\n\tSite: " + siteID + "\n\tType: " + readingType +
				"\n\tValue: " + readingValue +
				"\n\tUnit: "+ unit + "\n\tDate: " + readingDate +
				"\n";
	}
	
	/**
	 * Validate that there is a date associated with this item.
	 * It there isn't one, replace 0 with the date it was imported.
	 * @return
	 * reading date is returned, if not entered it is set as current date.
	 */
	public long validateDate() {
		if(this.readingDate == 0) {
			Date date = new Date();
			this.readingDate = date.getTime();
		}
		return this.readingDate;
	}
	
	/**
	 * Validate that there is a unit associated with the item.
	 * Provide a unit if it is absent.
	 */
	public void ValidateUnit() {
		if(this.unit == null || this.unit.equals("")) {
			if(this.readingType.equals("Temperature")|| this.readingType.equals("temp")) {
				this.unit = "Fahrenheit";
			}else if(this.readingType.equals("Humidity")|| this.readingType.equals("humidity")){
				this.unit = "Percent";
			}else if(this.readingType.equals("Pressure")|| this.readingType.equals("bar_press")){
				this.unit = "Bar";
			}else if(this.readingType.equals("Particulate")|| this.readingType.equals("particulate")){
				this.unit = "PPM";
			}
			
		}
	}
}
