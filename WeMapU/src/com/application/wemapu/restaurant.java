package com.application.wemapu;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.MapView.LayoutParams;

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
import android.widget.Button;
import android.widget.LinearLayout;


public class restaurant extends MapActivity
{
	
	private MapView mapView = null;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mapView = (MapView) findViewById(R.id.mapview);
        LinearLayout zoomLayout = (LinearLayout)findViewById(R.id.zoom);  
        View zoomView = mapView.getZoomControls(); 
 
        zoomLayout.addView(zoomView, 
            new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, 
                LayoutParams.WRAP_CONTENT)); 
        mapView.displayZoomControls(true);
      
        Button btns = (Button) findViewById(R.id.btnS);
        btns.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	try {
					loctest();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });                

    }
    
    
    
        
        
        private void loctest() throws IOException
        {
          
          Geocoder geoCoder = new Geocoder(this, Locale.getDefault()); 
          
          List<Address> ads = geoCoder.getFromLocationName("empire state building", 10);
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
                  canvas.drawBitmap(bmp, screenPts.x, screenPts.y-200, null);         
                  return true;
              }
          } 
          
          mc.animateTo(p);
          mc.setZoom(17);
          
          //add a location marker
          MapOverlay mapOverlay = new MapOverlay();
          List<Overlay> listOfOverlays = mapView.getOverlays();
          listOfOverlays.clear();
          listOfOverlays.add(mapOverlay);
          //add a location marker
          
          mapView.invalidate(); 
              
        }
		
    
   
    
    protected boolean isRouteDisplayed() {
        return false;
    }
    
}
   
    
  


