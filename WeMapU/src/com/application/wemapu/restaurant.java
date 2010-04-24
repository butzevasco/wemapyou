package com.application.wemapu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import java.net.*;
import java.io.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.MapView.LayoutParams;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;


public class restaurant extends MapActivity
{
	
	private MapView mapView = null;
	private MapController mapcontroller;
	
	 //setting initial area for map to be Chicago metropolitan area
    String coordinates[] = {"41.888988", "-87.622833"};
    double lat = Double.parseDouble(coordinates[0]);
    double lng = Double.parseDouble(coordinates[1]);

    GeoPoint chicago = new GeoPoint(
    		(int) (lat * 1E6), 
    		(int) (lng * 1E6));

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mapView = (MapView) findViewById(R.id.mapview);
        
      //This places the opening map centered on Chicago, IL
        MapController mc = mapView.getController();
        mc.animateTo(chicago);
        mc.setZoom(11);
        
        
//        overlayController oc = mapView.createOverlayController();
        TableLayout zoomLayout = (TableLayout)findViewById(R.id.zoom);  
        View zoomView = mapView.getZoomControls(); 
 
        zoomLayout.addView(zoomView, 
            new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, 
                LayoutParams.WRAP_CONTENT)); 
        mapView.displayZoomControls(true);
      
        final EditText et = (EditText)findViewById(R.id.entry);
        
        Button btns = (Button) findViewById(R.id.btn);
        btns.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	try {
					loctest(et.getEditableText().toString());
					((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))  
	                .hideSoftInputFromWindow(et.getWindowToken(), 0); 
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });                

    }
             
        
        private void loctest(String text) throws IOException
        {        	
        	 List<GeoPoint> gplist = new ArrayList<GeoPoint>();
        	 TextView tv = new TextView(this); 
             try { 
//            	 double latitude = 41.85; 
//               double longitude =  -87.65;
//                 
//            	 
//            	   mapcontroller.;
//                 mapcontroller.setZoom(17);
                    URL url = new URL ("http://local.yahooapis.com/LocalSearchService/V3/localSearch?appid=YahooDemo&query=restaurant&zip=60611&radius=2&results=4");
                    Vector<ParsedExampleDataSet> parsedList = read(url.openStream()); 
//                  String text1 = ""; 
                    for (int i=0; i<parsedList.size(); i++) { 
//                         text1 += parsedList.get(i).getTitle()+"\n" 
//                         			+ parsedList.get(i).getLatitude()+"\n"
//                         			+ parsedList.get(i).getLongitude()+"\n"; 
                         
                         double latitude = Double.valueOf(parsedList.get(i).getLatitude().trim()).doubleValue() * 1E6; 
                         double longitude = Double.valueOf(parsedList.get(i).getLongitude().trim()).doubleValue() * 1E6;
                         
                         final GeoPoint p = new GeoPoint((int) latitude, (int) longitude);
                         mapView.getOverlays().add(new MyLocationOverlay(p, getResources()));
                        
                         gplist.add(p);                         
                    }                     
 //                   tv.setText(text1);               
//                    for (int i = 0; i < gplist.size(); i++) {                     	  
//                    	GeoPoint point = (GeoPoint)gplist.get(i);
//                    	
//                    }
                    
               } catch (MalformedURLException e) { 
               } catch (IOException e) { 
               } 
             this.setContentView(tv); 
             
         } 
         
         public Vector<ParsedExampleDataSet> read(InputStream in) { 
          SAXParserFactory spf = SAXParserFactory.newInstance(); 
          SAXParser sp; 
          try { 
                    sp = spf.newSAXParser(); 
                    XMLReader xr = sp.getXMLReader(); 
                    ContentHandler ch = new ContentHandler(); 
                    xr.setContentHandler(ch); 
                    xr.parse(new InputSource(in)); 
                    return ch.getPDataSet(); 
               } catch (Exception e) { 
               } 
               return null; 
         }


		@Override
		protected boolean isRouteDisplayed() {
			// TODO Auto-generated method stub
			return false;
		} 
     } 

     class ParsedExampleDataSet { 
          private String Title; 
          private String Latitude;
          private String Longitude;
          
           
          public String getTitle() { return Title; } 
          public void setTitle(String Title) { this.Title = Title; } 
          
          public String getLatitude() { return Latitude; } 
          public void setLatitude(String Latitude) { this.Latitude = Latitude; }
          
          public String getLongitude() { return Longitude; } 
          public void setLongitude(String Longitude) { this.Longitude = Longitude; }
     } 

     class ContentHandler extends DefaultHandler { 
          private boolean in_titleTag = false;
          private boolean in_longitudeTag = false;
          private boolean in_latitudeTag = false;
           
          private ParsedExampleDataSet DataSet; 
          private Vector<ParsedExampleDataSet> myPEDataSet; 
           
          public ContentHandler() { 
               super(); 
               this.myPEDataSet = new Vector<ParsedExampleDataSet>(); 
          } 
          public Vector<ParsedExampleDataSet> getPDataSet() { 
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
        	 if (l.equals("Result")) { 
               DataSet = new ParsedExampleDataSet(); 
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
          
         
        	
        	/*
          Geocoder geoCoder = new Geocoder(this, Locale.getDefault()); 
          
          List<Address> ads = geoCoder.getFromLocationName("Sears",10);
          final GeoPoint p = new GeoPoint(
                  (int) ( ads.get(0).getLatitude() * 1E6), 
                  (int) ( ads.get(0).getLongitude() * 1E6));
          MapController mc = mapView.getController();
          
          class MapOverlay extends com.google.android.maps.Overlay
          {
              public boolean draw(Canvas canvas, MapView mapView, 
              boolean shadow, long when) 
              {
                  super.draw(canvas, mapView, shadow);                   
       
                  //---translate the GeoPoint to screen pixels---
                  Point screenPts = new Point();
                  mapView.getProjection().toPixels(p, screenPts);
       
                  //---add the marker---
                  Bitmap bmp = BitmapFactory.decodeResource(
                      getResources(), R.drawable.pushpin);            
                  canvas.drawBitmap(bmp, screenPts.x, screenPts.y, null); 
                  
                  return true;
              }
          } 
          
          mc.animateTo(p);
          mc.setZoom(7);
          
          //add a location marker
          MapOverlay mapOverlay = new MapOverlay();
          List<Overlay> listOfOverlays = mapView.getOverlays();
          listOfOverlays.clear();
          listOfOverlays.add(mapOverlay);
          //add a location marker
          mapView.getMapCenter();
          mapView.invalidate(); 
              */
        }    
}
   
    
  


