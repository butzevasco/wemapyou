package org.me.android.resmap;

import java.util.Vector;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import org.me.android.resmap.ParsedDataSet;

public class ContentHandler extends DefaultHandler {
	private boolean in_titleTag = false;
    private boolean in_longitudeTag = false;
    private boolean in_latitudeTag = false;
    private boolean in_ratingTag = false;
    private boolean in_addressTag = false;
    private boolean in_cityTag = false;
    private boolean in_stateTag = false;
    private boolean in_phoneTag = false;
    private boolean in_distanceTag = false;
        
    private ParsedDataSet DataSet; 
    private Vector<ParsedDataSet> myPEDataSet; 
     
    public ContentHandler() { 
         super(); 
         this.myPEDataSet = new Vector<ParsedDataSet>(); 
    } 
    public Vector<ParsedDataSet> getPDataSet() { 
         return this.myPEDataSet; 
    } 
     
   public void startDocument() throws SAXException { } 
   public void endDocument() throws SAXException { } 
   
   public void startElement(String n, String l, String q, Attributes atts) throws SAXException { 
	   if (l.equals("Title")) { 
		   this.in_titleTag = true; 
	   }
	   if (l.equals("Latitude")) { 
		   this.in_latitudeTag = true; 
	   }
	   if (l.equals("Longitude")) {
		   this.in_longitudeTag = true; 
       }
	   if (l.equals("AverageRating")) {
		   this.in_ratingTag = true; 
       }
	   if (l.equals("Address")) {
		   this.in_addressTag = true; 
       }
	   if (l.equals("City")) {
		   this.in_cityTag = true; 
       }
	   if (l.equals("State")) {
		   this.in_stateTag = true; 
       }
	   if (l.equals("Phone")) {
		   this.in_phoneTag = true; 
       }
	   if (l.equals("Distance")) {
		   this.in_distanceTag = true; 
       }
	   if (l.equals("Result")) { 
		   DataSet = new ParsedDataSet(); 
       } 	   
   } 
   
   public void endElement(String n, String l, String q) throws SAXException { 
	   if (l.equals("Title")) { 
		   this.in_titleTag = false; 
	   }
	   if (l.equals("Latitude")) { 
		   this.in_latitudeTag = false; 
	   }
	   if (l.equals("Longitude")) { 
		   this.in_longitudeTag = false; 
	   }
	   if (l.equals("AverageRating")) {
		   this.in_ratingTag = false; 
       }
	   if (l.equals("Address")) {
		   this.in_addressTag = false; 
       }
	   if (l.equals("City")) {
		   this.in_cityTag = false; 
       }
	   if (l.equals("State")) {
		   this.in_stateTag = false; 
       }
	   if (l.equals("Phone")) {
		   this.in_phoneTag = false; 
       }
	   if (l.equals("Distance")) {
		   this.in_distanceTag = false; 
       }
	   if (l.equals("Result")) { 
		   myPEDataSet.add(DataSet); 
	   } 
   } 
   
   public void characters(char[] ch, int start, int length) throws SAXException { 
	   if (in_titleTag) { 
		   DataSet.setTitle(new String(ch, start, length));          
	   } 
	   if (in_latitudeTag) { 
		   DataSet.setLatitude(new String(ch, start, length)); 
       }	   
	   if (in_longitudeTag) { 
		   DataSet.setLongitude(new String(ch, start, length)); 
	   }
	   if (in_ratingTag) {
		   DataSet.setRating(new String(ch, start, length)); 
       }
	   if (in_addressTag) {
		   DataSet.setAddress(new String(ch, start, length)); 
       }
	   if (in_cityTag) {
		   DataSet.setCity(new String(ch, start, length)); 
       }
	   if (in_stateTag) {
		   DataSet.setState(new String(ch, start, length)); 
       }
	   if (in_distanceTag) {
		   DataSet.setDistance(new String(ch, start, length)); 
       }
	   if (in_phoneTag) {
		   DataSet.setPhone(new String(ch, start, length)); 
       }
   }
}
