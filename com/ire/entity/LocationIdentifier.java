package com.ire.entity;
import java.util.HashSet;

public class LocationIdentifier {


	/**
	 * This class contains the list of the stop words
	 * @author gaurav
	 *
	 */
	public static final HashSet<String> locidentiferList = new HashSet<String>();
	static {
		locidentiferList.add("location");
		locidentiferList.add("map");
		locidentiferList.add("longitude");
		locidentiferList.add("latitude");
		locidentiferList.add("latitude");
		locidentiferList.add("latitude");
		}

	public static boolean isLocation(String word) {
		for(String person: locidentiferList) {
			if(word.contains(person)) {
				return true;
			}
		}
		return false;
	}
	
}
