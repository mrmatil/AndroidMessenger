package aib.projektZaliczeniowy.messenger.mapsUtils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;



public class Coordinates {

    //variables
    private String[] permisions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
    private Context mainContext;
    private Activity activity;


    // Constructor
    public Coordinates(Activity activity) {
        this.mainContext = activity.getApplicationContext();
        this.activity = activity;
    }


    /* Public Functions */




    /* Private Functions */


}


