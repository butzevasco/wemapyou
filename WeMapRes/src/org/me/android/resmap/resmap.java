package org.me.android.resmap;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;



import org.me.android.resmap.ContentHandler;
import org.me.android.resmap.ParsedDataSet;

import org.me.android.resmap.OverlayAdder;

import org.me.android.resmap.R;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.MapView.LayoutParams;

public class resmap extends MapActivity {
		
	private MapView mapView;
	private MapController mapController;
	private List<Overlay> mapOverlays;
	private OverlayAdder overlayadder;
	private ParsedDataSet parsedData;
	private Spinner spinner;
	ArrayList<String> locations;
	
	
    /** Called when the activity is first created. */
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        locations = new ArrayList<String>();
    	locations.add("Restaurant");
    	locations.add("Bar");
    	locations.add("Shop");
    	locations.add("Cafe");
    	locations.add("Tea");
    	locations.add("Mall");
    	locations.add("Toys");
    	locations.add("Pizza");
   
    	mapView = (MapView) findViewById(R.id.mapview);
        mapController = mapView.getController();
        spinner = (Spinner) findViewById(R.id.spinner1);
       
        
        mapView = (MapView) findViewById(R.id.mapview);
        LinearLayout zoomLayout = (LinearLayout)findViewById(R.id.zoom);  
        View zoomView = mapView.getZoomControls(); 
 
        zoomLayout.addView(zoomView, 
            new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, 
                LayoutParams.WRAP_CONTENT)); 
        mapView.displayZoomControls(true);
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, locations);
       	
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(selectListener);
        showAlert();
        gotoSelected();     
        mapOverlays.clear();
        overlayadder.clearOverlay();
        mapView.invalidate();
      
      //  mapView.updateViewLayout(mapView, 0);

    }

	@Override
	protected boolean isRouteDisplayed() {
		return true;
	}
	
	 
	protected void showAlert(){
	    	Builder welcome = new AlertDialog.Builder(this);
	    	welcome.setTitle("Restaurant Finder");
	    	welcome.setMessage("Welcome to Restaurant Finder");
	    	welcome.setNeutralButton("START",  new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dlg, int sumthin) {
	                // do nothing - it will close on its own
	              }
	            });
	    	welcome.show();	    	
	    }
	
	//parsing methods
	public Vector<ParsedDataSet> read(InputStream in) { 
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
	
	 private Spinner.OnItemSelectedListener selectListener = new Spinner.OnItemSelectedListener() { 
		      public void onItemSelected(AdapterView parent, View v, int position, long id) { 
		        gotoSelected();
		      } 
		      public void onNothingSelected(AdapterView parent) { }
			           
	};
	
	public void gotoSelected(){
		ArrayList<GeoPoint> geopointarray = null;
		int pos = spinner.getSelectedItemPosition();
		URL url = null;
		
		
		 geopointarray = new ArrayList<GeoPoint>();
	     try {
	    	 if (pos==0){
	        	url = new URL("http://local.yahooapis.com/LocalSearchService/V3/localSearch?appid=YahooDemo&query=restaurant&zip=60611&radius=2&results=4");
	         } else if (pos==1)	{
	    		 url = new URL ("http://local.yahooapis.com/LocalSearchService/V3/localSearch?appid=YahooDemo&query=bar&zip=60611&radius=2&results=4");
	    	 } else if (pos==2)	{
	    		 url = new URL ("http://local.yahooapis.com/LocalSearchService/V3/localSearch?appid=YahooDemo&query=shop&zip=60611&radius=2&results=4");
	    	 } else if (pos==3)	{
	    		 url = new URL ("http://local.yahooapis.com/LocalSearchService/V3/localSearch?appid=YahooDemo&query=cafe&zip=60611&radius=2&results=4");
	    	 } else if (pos==4)	{
	    		 url = new URL ("http://local.yahooapis.com/LocalSearchService/V3/localSearch?appid=YahooDemo&query=tea&zip=60611&radius=2&results=4");
	    	 } else if (pos==5)	{	
	    		 url = new URL ("http://local.yahooapis.com/LocalSearchService/V3/localSearch?appid=YahooDemo&query=mall&zip=60611&radius=2&results=4");
	    	 } else if (pos==6)	{
	    		 url = new URL ("http://local.yahooapis.com/LocalSearchService/V3/localSearch?appid=YahooDemo&query=toys&zip=60611&radius=2&results=4");
	    	 } else if (pos==7)	{ 
	    		 url = new URL ("http://local.yahooapis.com/LocalSearchService/V3/localSearch?appid=YahooDemo&query=pizza&zip=60611&radius=2&results=4");
	    	 }
	    		 
	    	 
	    	 Vector<ParsedDataSet> parsedList = read(url.openStream());
	        	
	        	for (int i=0; i<parsedList.size(); i++) {
	        		double latitude = Double.valueOf(parsedList.get(i).getLatitude().trim()).doubleValue() * 1E6; 
	                double longitude = Double.valueOf(parsedList.get(i).getLongitude().trim()).doubleValue() * 1E6;
	                GeoPoint p = new GeoPoint((int) latitude, (int) longitude);
	            //    Lats.add(latitude);
	           //     Long.add(longitude);
	                geopointarray.add(p);                  
	           
	        	}       
	        
	        mapController.setCenter(geopointarray.get(1));
	        mapController.setZoom(17);	        
	        
	        
		    Drawable drawable = resmap.this.getResources().getDrawable(R.drawable.food_icon);
	        overlayadder = new OverlayAdder(drawable, mapView.getContext());
	        int i =0;
	        for (GeoPoint point: geopointarray){        	
	        	OverlayItem temp = new OverlayItem(point, parsedList.get(i).getTitle(),"Rating : "+ parsedList.get(i).getRating()+"\n"+ 
	        			"Address: "+parsedList.get(i).getAddress()+"\t"+parsedList.get(i).getCity()+"\t"+parsedList.get(i).getState()+"\n"+
	        			"Phone: "+parsedList.get(i).getPhone());
	        	overlayadder.addOverlay(temp);
	        	i++;
	        }
	        } catch (MalformedURLException e) { 
	        } catch (IOException e) { 
	        } 
	        
	        mapOverlays = mapView.getOverlays();
	        mapOverlays.add(overlayadder);
	        spinner = (Spinner) findViewById(R.id.spinner1);
	        mapController.setCenter(geopointarray.get(2));
	        mapController.setZoom(15); 
	        geopointarray.clear();
	        System.out.println(geopointarray.size());
	      	
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	        switch (keyCode) {
	        case KeyEvent.KEYCODE_I:
	        	mapController.zoomIn();
	                break;
	        case KeyEvent.KEYCODE_O:
	        	mapController.zoomOut();
	                break;
	        }
	        return super.onKeyDown(keyCode, event);
	}
}