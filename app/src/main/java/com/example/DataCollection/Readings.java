package com.example.DataCollection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * This class embody the input JSON file
 * An object which represent the collection of items
 *
 */

public class Readings {
	
	@SerializedName("site_readings")
	@Expose
	private ArrayList<Item> readings = new ArrayList<>();
	
	/**
	 * Readings method does nothing.  
	 * @param void
	 * 
	 * @return
	 * void
	 */
	
	public Readings() {
	}
	
	/**
	 * getReadings gets an array of readings.  
	 * @param void
	 * 
	 * @return
	 * the array of readings. 
	 */
	
	public ArrayList<Item> getReadings() {
		return this.readings;
	}
	
	/**
	 * setReadings sets reading to the list array.  
	 * @param list
	 * 
	 * @return
	 * void
	 */
	
	public void setReadings(ArrayList<Item> list) {
		this.readings = list;
	}
	
	/**
	 * toString writes the reading array.  
	 * @param void
	 * 
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
