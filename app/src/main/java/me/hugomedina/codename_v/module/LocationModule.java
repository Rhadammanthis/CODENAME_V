package me.hugomedina.codename_v.module;

import android.content.Context;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hugoe on 3/19/2018.
 */

@Module(includes =  {ContextModule.class, SharedPreferencesModule.class})
public class LocationModule {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */

    @Provides
    LocationRequest locationRequest(){
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);

        return locationRequest;
    }

    @Provides
    FusedLocationProviderClient fusedLocationProviderClient(Context context){
        return LocationServices.getFusedLocationProviderClient(context);
    }

}
