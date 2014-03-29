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
		locidentiferList.add("area_code");
		locidentiferList.add("area code");
		locidentiferList.add("latd");
		locidentiferList.add("latm");
		locidentiferList.add("currency");
		locidentiferList.add("coords");
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
