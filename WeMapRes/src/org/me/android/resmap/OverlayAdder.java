package org.me.android.resmap;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class OverlayAdder extends ItemizedOverlay<OverlayItem> {

	private Context mContext;
	private ArrayList<OverlayItem> overlayitems =  new ArrayList<OverlayItem>();
	
	public OverlayAdder(Drawable defaultMarker) {
		super(defaultMarker);		
	}
	
	public OverlayAdder(Drawable defaultMarker, Context context) {
		//	  super(defaultMarker);
			  super(boundCenterBottom(defaultMarker));			 
			  mContext = context;
		}

	
	public void addOverlay(OverlayItem overlay) {
	    overlayitems.add(overlay);
	    populate();
	}
	@Override
	protected OverlayItem createItem(int arg0) {
		return overlayitems.get(arg0);
	}

	@Override
	public int size() {
		return overlayitems.size();
	}
	
	public void clearOverlay(){
		overlayitems.clear();
	}
	
	protected boolean onTap(int index) {
		  AlertDialog.Builder dialog;
		  OverlayItem item = overlayitems.get(index);
		  dialog = new AlertDialog.Builder(mContext);
		  dialog.setTitle(item.getTitle());
		  dialog.setMessage(item.getSnippet());
		  dialog.setNeutralButton("OK",  new DialogInterface.OnClickListener() {
	          public void onClick(DialogInterface dlg, int sumthin) {
	              // do nothing - it will close on its own
	            }
	          });
		  dialog.show();
		  return true;		  
    }

}
