package bashar.astifan.ismart.smart.mobi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

/**
 *
 *
 * @author Bashar Astifan <br>
 *         <a href=
 *         "astifan.online"
 *         >Read More</a> <br>
 * @version 2.0
 *
 */
public class iGMS extends Service implements LocationListener{

	   private final Context mContext;

	    // Flag for GPS status
	    boolean isGPSEnabled = false;

	    // Flag for network status
	    boolean isNetworkEnabled = false;

	    // Flag for GPS status
	    boolean canGetLocation = false;

	    Location location; // Location
	    double latitude; // Latitude
	    double longitude; // Longitude

	    // The minimum distance to change Updates in meters
	    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

	    // The minimum time between updates in milliseconds
	    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

	    // Declaring a Location Manager
	    protected LocationManager locationManager;

	    public iGMS(Context context) {
	        this.mContext = context;
	        getLocation();
	    }

	    public Location getLocation() {
	        try {
	            locationManager = (LocationManager) mContext
	                    .getSystemService(LOCATION_SERVICE);

	            // Getting GPS status
	            isGPSEnabled = locationManager
	                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

	            // Getting network status
	            isNetworkEnabled = locationManager
	                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

	            if (!isGPSEnabled && !isNetworkEnabled) {
	                // No network provider is enabled
	            } else {
	                this.canGetLocation = true;
	                if (isNetworkEnabled) {
	                    locationManager.requestLocationUpdates(
	                            LocationManager.NETWORK_PROVIDER,
	                            MIN_TIME_BW_UPDATES,
	                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
	                    Log.d("Network", "Network");
	                    if (locationManager != null) {
	                        location = locationManager
	                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	                        if (location != null) {
	                            latitude = location.getLatitude();
	                            longitude = location.getLongitude();
	                        }
	                    }
	                }
	                // If GPS enabled, get latitude/longitude using GPS Services
	                if (isGPSEnabled) {
	                    if (location == null) {
	                        locationManager.requestLocationUpdates(
	                                LocationManager.GPS_PROVIDER,
	                                MIN_TIME_BW_UPDATES,
	                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
	                        Log.d("GPS Enabled", "GPS Enabled");
	                        if (locationManager != null) {
	                            location = locationManager
	                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
	                            if (location != null) {
	                                latitude = location.getLatitude();
	                                longitude = location.getLongitude();
	                            }
	                        }
	                    }
	                }
	            }
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }

	        return location;
	    }


	    /**
	     * Stop using GPS listener
	     * Calling this function will stop using GPS in your app.
	     * */
	    public void stopUsingGPS(){
	        if(locationManager != null){
	            locationManager.removeUpdates(iGMS.this);
	        }
	    }


	    /**
	     * Function to get latitude
	     * */
	    public double getLatitude(){
	        if(location != null){
	            latitude = location.getLatitude();
	        }

	        // return latitude
	        return latitude;
	    }


	    /**
	     * Function to get longitude
	     * */
	    public double getLongitude(){
	        if(location != null){
	            longitude = location.getLongitude();
	        }

	        // return longitude
	        return longitude;
	    }

	    /**
	     * Function to check GPS/Wi-Fi enabled
	     * @return boolean
	     * */
	    public boolean canGetLocation() {
	        return this.canGetLocation;
	    }


	    /**
	     * Function to show settings alert dialog.
	     * On pressing the Settings button it will launch Settings Options.
	     * */
	    public void showSettingsAlert(){
	        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

	        // Setting Dialog Title
	        alertDialog.setTitle("GPS is settings");

	        // Setting Dialog Message
	        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

	        // On pressing the Settings button.
	        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog,int which) {
	                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	                mContext.startActivity(intent);
	            }
	        });

	        // On pressing the cancel button
	        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	            dialog.cancel();
	            }
	        });

	        // Showing Alert Message
	        alertDialog.show();
	    }


	    @Override
	    public void onLocationChanged(Location location) {
	    }


	    @Override
	    public void onProviderDisabled(String provider) {
	    }


	    @Override
	    public void onProviderEnabled(String provider) {
	    }


	    @Override
	    public void onStatusChanged(String provider, int status, Bundle extras) {
	    }


	    @Override
	    public IBinder onBind(Intent arg0) {
	        return null;
	    }
	    
	    public GoogleMap addMarker(GoogleMap gMapView,double[] latitudes,double[] longitudes,String[] titles,String[] desc,int[] marker_icons){
	    	for(int i=0;i<titles.length;i++){
	    		LatLng mylocation;
		    	mylocation=new LatLng (latitudes[i],longitudes[i]);
    	    gMapView.addMarker(new MarkerOptions()
    	        .position(mylocation)
    	        .title(titles[i])
    	        .snippet(desc[i])
    	        .icon(BitmapDescriptorFactory
    	            .fromResource(marker_icons[i])));
	    	}
	    	return gMapView;
	    }
	    
	    public GoogleMap addMarker(GoogleMap gMapView,double[] latlong,String title,String desc,int marker_icon){
	    		LatLng mylocation;
		    	mylocation=new LatLng (latlong[0],latlong[1]);
    	    gMapView.addMarker(new MarkerOptions()
    	        .position(mylocation)
    	        .title(title)
    	        .snippet(desc)
    	        .icon(BitmapDescriptorFactory
    	            .fromResource(marker_icon))).showInfoWindow();
	    	return gMapView;
	    }
	    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
		@SuppressLint("NewApi")
		public GoogleMap link_map_layer(Context ctx,int id){
	    	return ((MapFragment) ((Activity) ctx).getFragmentManager().findFragmentById(id)).getMap();
	    }
	    
	    public String makeURL(double[] sourcelatlng, double[] destlatlng) {
	        StringBuilder urlString = new StringBuilder();
	        urlString.append("http://maps.googleapis.com/maps/api/directions/json");
	        urlString.append("?origin=");// from
	        urlString.append(Double.toString(sourcelatlng[0]));
	        urlString.append(",");
	        urlString.append(Double.toString(sourcelatlng[1]));
	        urlString.append("&destination=");// to
	        urlString.append(Double.toString(destlatlng[0]));
	        urlString.append(",");
	        urlString.append(Double.toString(destlatlng[1]));
	        urlString.append("&sensor=false&mode=driving&alternatives=true");
	        return urlString.toString();
	    }
	    public String makeURL(double[] sourcelatlng, double[] destlatlng,boolean alternatives) {
	        StringBuilder urlString = new StringBuilder();
	        urlString.append("http://maps.googleapis.com/maps/api/directions/json");
	        urlString.append("?origin=");// from
	        urlString.append(Double.toString(sourcelatlng[0]));
	        urlString.append(",");
	        urlString.append(Double.toString(sourcelatlng[1]));
	        urlString.append("&destination=");// to
	        urlString.append(Double.toString(destlatlng[0]));
	        urlString.append(",");
	        urlString.append(Double.toString(destlatlng[1]));
	        urlString.append("&sensor=false&mode=driving&alternatives="+(alternatives?"true":"false"));
	        return urlString.toString();
	    }
	    public HashMap<String, String> getRouteInfo(String json_data,String[] infos){
	    	iJSON js=new iJSON();
    		HashMap<String, ArrayList<String>> jso=js.get_json_object_data( json_data, "routes");
    		HashMap<String, ArrayList<String>> info=js.get_json_array_data(jso.get("legs").get(0));
    		HashMap<String, String> data=new HashMap<String, String>();
	    	for(int i=0;i<infos.length;i++){
	    		String key=infos[i];
	    		String da="";
	    		if(info.get(key)!=null){
	    			da=js.get_json_simple_object_data(info.get(infos[i]).get(0), "text");
	    		}
	    		else{
	    			da=	info.get(infos[i]).get(0);
	    		}
	    		data.put(key, da);
	    		Log.i(key, da);
	    	}
	    	return data;
	    }
	    public void drawDirectRoute(GoogleMap gMapView,double[][] list){
	    	PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
	    	for (int z = 0; z < list.length; z++) {
	    	    LatLng point = new LatLng(list[z][0],list[z][1]);
	    	    options.add(point);
	    	}
	    	 gMapView.addPolyline(options);
	    }
	    public void drawGoogleRoute(GoogleMap gMapView,String json_path){
	    	PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
	    	// Tranform the string into a json object
            iJSON js=new iJSON();
            String data=js.get_map_route_data(json_path);
            List<LatLng> list = decodePoly(data);
	    	for (int z = 0; z < list.size(); z++) {
	    	    LatLng point = list.get(z);
	    	    options.add(point);
	    	}
	    	 gMapView.addPolyline(options);
	    }
	    public void drawGoogleRoute(GoogleMap gMapView,String json_path,int color){
	    	PolylineOptions options = new PolylineOptions().width(5).color(color).geodesic(true);
	    	// Tranform the string into a json object
            iJSON js=new iJSON();
            String data=js.get_map_route_data(json_path);
            List<LatLng> list = decodePoly(data);
	    	for (int z = 0; z < list.size(); z++) {
	    	    LatLng point = list.get(z);
	    	    options.add(point);
	    	}
	    	 gMapView.addPolyline(options);
	    }

	    private List<LatLng> decodePoly(String encoded) {

	        List<LatLng> poly = new ArrayList<LatLng>();
	        int index = 0, len = encoded.length();
	        int lat = 0, lng = 0;

	        while (index < len) {
	            int b, shift = 0, result = 0;
	            do {
	                b = encoded.charAt(index++) - 63;
	                result |= (b & 0x1f) << shift;
	                shift += 5;
	            } while (b >= 0x20);
	            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
	            lat += dlat;

	            shift = 0;
	            result = 0;
	            do {
	                b = encoded.charAt(index++) - 63;
	                result |= (b & 0x1f) << shift;
	                shift += 5;
	            } while (b >= 0x20);
	            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
	            lng += dlng;

	            LatLng p = new LatLng((((double) lat / 1E5)),
	                    (((double) lng / 1E5)));
	            poly.add(p);
	        }

	        return poly;
	    }
	    public void mapZoom(GoogleMap gMapView,double[] latlong){
	    	LatLng location=new LatLng (latlong[0],latlong[1]);
	    	// Move the camera instantly to hamburg with a zoom of 15.
		    gMapView.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
		    // Zoom in, animating the camera.
		    gMapView.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
	    }
	    public void mapZoom(GoogleMap gMapView,double[] latlong,int zoom_from,int zoom_to){
	    	LatLng location=new LatLng (latlong[0],latlong[1]);
	    	// Move the camera instantly to hamburg with a zoom of 15.
		    gMapView.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoom_from));
		    // Zoom in, animating the camera.
		    gMapView.animateCamera(CameraUpdateFactory.zoomTo(zoom_to), 2000, null);
	    }
	    public void mapZoom(GoogleMap gMapView,double[] latlong,int zoom_from,int zoom_to,int call_back){
	    	LatLng location=new LatLng (latlong[0],latlong[1]);
	    	// Move the camera instantly to hamburg with a zoom of 15.
		    gMapView.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoom_from));
		    // Zoom in, animating the camera.
		    gMapView.animateCamera(CameraUpdateFactory.zoomTo(zoom_to), call_back, null);
	    }
	
	   public static class iMarker{
	    	private String markerTitle="";
	    	private String markerDetailes="";
	    	private double[] markerLocation={0,0};
	    	private int markerIcon=0;
	    	public void setMarkerData(String title,String details,double[] location,int icon){
	    		markerTitle=title;
	    		markerDetailes=details;
	    		markerLocation=location;
	    		markerIcon=icon;
	    	}
	    	public String getTitle(){
	    		return markerTitle;
	    	}
	    	public String getDetails(){
	    		return markerDetailes;
	    	}
	    	public double[] getLocation(){
	    		return markerLocation;
	    	}
	    	public int getIcon(){
	    		return markerIcon;
	    	}
	    	
	    }
	   public static  class  iZoom{
		   private int izoomFrom=5;
		   private int izoomTo=15;
		   private double[] ilocation={0,0};
		   public void setZoomData(double[] location,int zoomfrom,int zoomto){
			   ilocation=location;
			   izoomFrom=zoomfrom;
			   izoomTo=zoomto;
		   }
		   public double[] getLocation(){
			   return ilocation;
		   }
		   public int getZoomFrom(){
			   return izoomFrom;
		   }
		   public int getZoomTo(){
			   return izoomTo;
		   }
	   }
	   public static class iRoute{
		   private String irouteData="";
		   private int irouteColor=Color.BLUE;
		   
		   public void setRouteDate(String routeData,int routeColor){
			   irouteData=routeData;
			   irouteColor=routeColor;
		   }
            public void setRouteDate(String routeData){
            	irouteData=routeData;
		   }
            
            public String getRouteData(){
            	return irouteData;
            }
            public int getRouteColor(){
            	return irouteColor;
            }
	   }
	   
	   public static String getStaticMap(double[] location){
		  String mainURL="https://maps.googleapis.com/maps/api/staticmap?center="+location[0]+","+location[1]+"&zoom=18&size=600x400&maptype=roadmap";
		   String Markers="";
		   Markers+="&markers=color:red%7Clabel:S%7C"+location[0]+","+location[1]+"";   
		   return mainURL+Markers;
		   
	   }
	   public static void openNavigation(Context ctx,double[] location){
		   Intent i = new Intent(Intent.ACTION_VIEW, 
				   Uri.parse("google.navigation:q="+location[0]+","+location[1]+"")); 
				  ((Activity)ctx).startActivity(i);
	   }
}
