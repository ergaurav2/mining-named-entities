package com.ire.entity;


/**
 * This class represent a page in the wikipedia
 * @author gaurav
 *
 */
public class Page {

	private StringBuffer infobox = new StringBuffer();
	private StringBuffer bodytext = new StringBuffer();
	private StringBuffer title = new StringBuffer();
	private StringBuffer category = new StringBuffer();
	private StringBuffer references = new StringBuffer();
	private StringBuffer externallinks = new StringBuffer();
	
	private String id = "";
	private char [] text = new char[100];
	public char[] getText() {
		return text;
	}
	public void setText(char[] text) {
		this.text = text;
	}
	public StringBuffer getInfobox() {
		return infobox;
	}
	public void setInfobox(StringBuffer infobox) {
		this.infobox = infobox;
	}
	public StringBuffer getBodytext() {
		return bodytext;
	}
	public void setBodytext(StringBuffer bodytext) {
		this.bodytext = bodytext;
	}
	public StringBuffer getTitle() {
		return title;
	}
	public void setTitle(StringBuffer title) {
		this.title = title;
	}
	public StringBuffer getCategory() {
		return category;
	}
	public void setCategory(StringBuffer category) {
		this.category = category;
	}
	public StringBuffer getReferences() {
		return references;
	}
	public void setReferences(StringBuffer references) {
		this.references = references;
	}
	public StringBuffer getExternallinks() {
		return externallinks;
	}
	public void setExternallinks(StringBuffer externallinks) {
		this.externallinks = externallinks;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
