package org.me.android.resmap;

public class ParsedDataSet {
	
	private String title; 
    private String latitude;
    private String longitude;
    private String rating;
    private String address;
    private String city;
    private String state;
    private String phone;
    private String distance;
    
//    public ParsedExampleDataSet(String title, String latitude, String longitude){    	
//    	this.title = title;
//    	this.latitude = latitude;
//    	this.longitude = longitude;
//    }    
     
    public ParsedDataSet(){
    	
    }
    
    public String getTitle() { return title; } 
    public void setTitle(String title) { this.title = title; } 
    
    public String getLatitude() { return latitude; } 
    public void setLatitude(String latitude) { this.latitude = latitude; }
    
    public String getLongitude() { return longitude; } 
    public void setLongitude(String longitude) { this.longitude = longitude; }
    
    public String getRating(){return rating;}
    public void setRating(String rating) {this.rating = rating;	}
    
    public String getCity() { return city; } 
    public void setCity(String city) { this.city = city; }
    
    public String getState() { return state; } 
    public void setState(String state) { this.state = state; }
    
    public String getAddress() { return address; } 
    public void setAddress(String address) { this.address = address; }
    
    public String getPhone() { return phone; } 
    public void setPhone(String phone) { this.phone = phone; } 
    
    public String getDistance() { return distance; } 
    public void setDistance(String distance) { this.distance = distance; } 
    

}
