package com.example.DataCollection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Readings stores a collection of items that
 * may or may not be related
 */

public class Readings {
	
	@SerializedName("site_readings")
	@Expose
	private ArrayList<Item> readings = new ArrayList<>();

	public Readings() {
	}
	
	/**
	 * getReadings gets an array of readings.
	 * @return
	 * the array of readings. 
	 */
	
	public ArrayList<Item> getReadings() {
		return this.readings;
	}
	
	/**
	 * setReadings sets reading to the list array.  
     * @param list An ArrayList of items
	 */
	
	public void setReadings(ArrayList<Item> list) {
		this.readings = list;
	}
	
	/**
	 * toString writes the reading array.
	 * @return
	 * the text string of readings
	 */

	@Override
	public String toString() {
		String text = "";
		for(Item i : readings) {
			text += i.toString() + "\n\n"; // add the next item as string to text
		}
		return  text;
	}

}
