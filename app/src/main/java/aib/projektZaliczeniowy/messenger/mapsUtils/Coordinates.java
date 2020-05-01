package aib.projektZaliczeniowy.messenger.mapsUtils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;
import static androidx.core.content.ContextCompat.getSystemService;


public class Coordinates {

    //variables
    private String[] permisions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
    private Context mainContext;
    private Activity activity;
    LocationManager locationManager;
    LocationListener locationListener;


    // Constructor
    public Coordinates(Activity activity, LocationManager locationManager) {
        this.mainContext = activity.getApplicationContext();
        this.activity = activity;
        this.locationManager = locationManager;
        locationListener = new MyLocationListener(mainContext.getSharedPreferences("Coordinates", Context.MODE_PRIVATE));
    }


    /* Public Functions */

    @SuppressLint("MissingPermission")
    public void startCoordinatesListener(){
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000,
                10,
                locationListener);
    }


    /* Private Functions */


    /* Internal Classes */

    private class MyLocationListener implements LocationListener {

        SharedPreferences preferences;

        public MyLocationListener(SharedPreferences preferences) {
            this.preferences = preferences;
        }

        @Override
        public void onLocationChanged(Location loc) {

            SharedPreferences.Editor editor = preferences.edit();
            String long_name = "Longitude";
            String lat_name = "Latitude";
            editor.putFloat(long_name, (float) loc.getLongitude());
            editor.putFloat(lat_name, (float) loc.getLatitude());



            String longitude = "Longitude: " + loc.getLongitude();
            Log.v(TAG, longitude);
            String latitude = "Latitude: " + loc.getLatitude();
            Log.v(TAG, latitude);

//            /*------- To get city name from coordinates -------- */
//            String cityName = null;
//            Geocoder gcd = new Geocoder(mainContext, Locale.getDefault());
//            List<Address> addresses;
//            try {
//                addresses = gcd.getFromLocation(loc.getLatitude(),
//                        loc.getLongitude(), 1);
//                if (addresses.size() > 0) {
//                    System.out.println(addresses.get(0).getLocality());
//                    cityName = addresses.get(0).getLocality();
//                }
//            }
//            catch (IOException e) {
//                e.printStackTrace();
//            }
        }

        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    }
}


