package com.ire.entity;
import java.util.HashSet;

public class OrgIdentifier {


	/**
	 * This class contains the list of the stop words
	 * @author gaurav
	 *
	 */
	public static final HashSet<String> orgidentiferList = new HashSet<String>();
	static {
		orgidentiferList.add("establishment");
		orgidentiferList.add("established");
		orgidentiferList.add("establish");
		}

	public static boolean isOrg(String word) {
		for(String person: orgidentiferList) {
			if(word.contains(person)) {
				return true;
			}
		}
		return false;
	}
	
}
