package com.example.DataCollection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * This class is a collection of studies in the program. 
 * It stores a study object along with the sites and 
 * the readings associated with the study.
 */
public class Record {
	private static final Object LOCK = new Object();
	private static Record  recordSelf = null;
	private static Study unknownStudy = new Study("UnknownStudy", "UnknownStudy");
	@SerializedName("Record_Studies")
	@Expose
	private static List<Study> studySet;
	
	private Record() {}
	
	/**
	 * Singleton instance method
	 * 
	 */
	public static Record getInstance() {
		if(recordSelf == null) {
			synchronized (LOCK) {
				if(recordSelf == null) {
					unknownStudy = new Study("UnknownStudy", "UnknownStudy");
					recordSelf = new Record();
					studySet = new ArrayList<Study>(Arrays.asList(unknownStudy));
				}
			}
		}
		return recordSelf;
	}
	
	/**
	 * Appends the specified Study to the end of the list of studies.
	 * @return 
	 */
	public boolean importStudy(Study s) {
		if(!studySet.contains(s)) {
			return studySet.add(s);
		}
		else {
			Readings importedReadings = new Readings();
			ArrayList<Item> itemList = new ArrayList<Item>();
			System.out.println(s.getStudyName() + "   " + s.getStudyID());
			Study initializedStudy = this.getStudyByID(s.getStudyID());
			for(Site site : s.getAllSites()) {
				itemList.addAll(site.getItems());
			}
			importedReadings.setReadings(itemList);
			initializedStudy.setSiteForReading(importedReadings);
			initializedStudy.addReadings(importedReadings);
		}
		return false;
	}
	
	public boolean createAndAddNewStudy(String studyID, String studyName) {
		Study newStudy = new Study(studyID, studyName);
		if(!this.getStudies().contains(newStudy)) {
			return this.addStudy(newStudy);
		}
		return false;
	}
	
	public boolean addStudy(Study s) {
		if(!this.getStudies().contains(s)) {
			return studySet.add(s);
		}
		return false;
	}
	
	/**
	 * @param arg0 - Integer position to add to
	 * @param arg1- Study to be added
	 * Inserts the specified element at the specified position in
	 *  this list(optional operation).
	 */
	public boolean addStudy(int arg0, Study s) {
		studySet.add(arg0, s);
		return studySet.contains(s);
	}

	/**
	 * Adds a site s to the record, associating it with the correct study
	 * @param s
	 * @return
	 */
	public boolean addSite(Site s) {
		for(Study study : studySet) {
			if(s.getStudyID().equals(study.getStudyID())) {
				study.addSite(s);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Adds a set of readings to the record.
	 * @param r
	 */
	public void addReadings(Readings r) {
		for(Study s : studySet) {
			s.addReadings(r);
		}
	}

	/**
	 * @param c- collection containing elements to be added to this list
	 * @return
	 * Appends all of the elements in the specified collection to 
	 * the end of this list, in the order that they are returned 
	 * by the specifiedcollection's iterator (optional operation). 
	 */
	public boolean addAllStudy(Collection<? extends Study> c) {
		return studySet.addAll(c);
	}

	/**
	 * Removes all of the elements from this list (optional operation).
	 * The list will be empty after this call returns
	 */
	public void clearStudy() {
		studySet.clear();
	}

	/**
	 * Returns true if this list contains the specified element.
	 * @param o
	 * @return
	 */
	public boolean contains(Object o) {
		return studySet.contains(o);
	}
	
	/**
	 * @param String name, ID
	 * Find a study by study name and ID
	 * @return 
	 * Study
	 * Null if not found
	 */
	public static Study findByAttributes(String name, String Id) {
		Study found = null;
		//it the studyList is not empty 
		if (!studySet.isEmpty()) {
			Iterator<Study> iterate = studySet.iterator();
			while (iterate.hasNext()) {
				Study st = iterate.next();
				if (st.getStudyID().equals(Id) && st.getStudyName().equals(name)) {
					found = st;
					break;
				}
			} 
		}
		return found;
	}

	/**
	 * Returns true if this list contains all of the elements
	 * of the specified collection.
	 */
	public boolean containsAll(Collection<?> c) {
		return studySet.containsAll(c);
	}

	/**
	 * Returns the element at the specified position in this list.
	 * @param index
	 * @return
	 */
	public Study get(int index) {
		return studySet.get(index);
	}
	
	/**
	 * Returns the studies associated with the record object
	 * @return
	 */
	public List<Study> getStudies() {
		return studySet;
	}
	
	/**
	 * Returns a list of sites associated with the record.
	 * @return
	 */
	public List<Site> getSites(){
		List<Site> allSites = new ArrayList<Site>();
		for(Study s : studySet) {
			allSites.addAll(s.getAllSites());
		}
		return allSites;
	}
	
	/**
	 * Return a study that matches the input parameters
	 * @param studyName
	 * @return
	 */
	public Study getStudyByName(String studyName) {
		for(Study s : studySet) {
			if(s.getStudyName().equals(studyName)) {
				return s;
			}
		}
		return null;
	}
	
	/**
	 * Return a study that matches the input parameters
	 * @param studyID
	 * @param studyName
	 * @return
	 */
	public Study getStudy(String studyID, String studyName) {
		for(Study s : studySet) {
			if(s.getStudyID().equals(studyID) && s.getStudyName().equals(studyName)) {
				return s;
			}
		}
		return null;
	}
	
	/**
	 * Return a study that matches the input parameters
	 * @param studyID
	 * @return
	 */
	public Study getStudyByID(String studyID) {
		for(Study s : studySet) {
			if(s.getStudyID().equals(studyID)) {
				return s;
			}
		}
		return null;
	}

	/**
	 * Returns the index of the first occurrence of the specified
	 *  element in this list, or -1 if this list does not contain 
	 *  the element.
	 */
	public int indexOf(Object o) {
		return studySet.indexOf(o);
	}

	/**
	 * Returns true if this list contains no elements.
	 * @return
	 */
	public boolean isEmpty() {
		return studySet.isEmpty();
	}

	 /**
	  *  Returns an iterator over the elements in this list in proper sequence
	  * @return
	  */
	public Iterator<Study> iterator() {
		return studySet.iterator();
	}

	/**
	 *  Remove a study from the list by passing in a study object to be remove
	 * @param o
	 * @return
	 */
	public boolean remove(Object o) {
		return studySet.remove(o);
	}

	/**
	 *  Remove a study from the list by index
	 * @param index
	 * @return
	 */
	public Study remove(int index) {
		return studySet.remove(index);
	}

	/**
	 * Removes from this list all of its elements that are contained
	 * in the specified collection (optional operation)
	 * @param c
	 * @return
	 */
	public boolean removeAll(Collection<?> c) {
		return studySet.removeAll(c);
	}

    /**
     * Replaces the element at the specified position in this list with the specified element (optional operation).
     * @param index
     * @param element
     * @return
     */
	public Study set(int index, Study element) {
		return studySet.set(index, element);
	}

	/**
	 *  Returns the number of elements in this list.
	 * @return
	 */
	public int size() {
		return studySet.size();
	}

	/**
	 * Returns an array containing all of the elements in this list in proper sequence (from first to last element).
	 * @return
	 */
	public Object[] toArray() {
		return studySet.toArray();
	}
}
