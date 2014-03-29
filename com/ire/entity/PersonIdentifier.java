package com.ire.entity;
import java.util.HashSet;

public class PersonIdentifier {


	/**
	 * This class contains the list of the stop words
	 * @author gaurav
	 *
	 */
	public static final HashSet<String> personidentiferList = new HashSet<String>();
	static {
		personidentiferList.add("living people");
		personidentiferList.add("people");
		personidentiferList.add("living");
		personidentiferList.add("birth_date");
		personidentiferList.add("birth");
		personidentiferList.add("death_date");
		personidentiferList.add("death");
		}

	public static boolean isPerson(String word) {
		for(String person: personidentiferList) {
			if(word.contains(person)) {
				return true;
			}
		}
		return false;
	}
	
}
