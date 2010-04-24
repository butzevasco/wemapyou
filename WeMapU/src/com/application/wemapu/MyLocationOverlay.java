package com.application.wemapu;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.test.ActivityUnitTestCase;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MyLocationOverlay extends Overlay{
	private GeoPoint point;
	private Resources resources;
	public MyLocationOverlay(GeoPoint point, Resources resources){
		this.point = point;
		this.resources = resources;
	}
	public boolean draw(Canvas canvas, MapView mapView, 
            boolean shadow, long when) 
            {
                super.draw(canvas, mapView, shadow);                   
     
                //---translate the GeoPoint to screen pixels---
                Point screenPts = new Point();
                mapView.getProjection().toPixels(point, screenPts);
     
                //---add the marker---
               Bitmap bmp = BitmapFactory.decodeResource(resources , R.drawable.pushpin);            
                canvas.drawBitmap(bmp, screenPts.x, screenPts.y, null); 
                
                return true;
            }

}
