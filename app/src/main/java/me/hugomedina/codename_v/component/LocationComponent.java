package me.hugomedina.codename_v.component;

import android.content.SharedPreferences;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;

import dagger.Component;
import me.hugomedina.codename_v.module.LocationModule;

/**
 * Created by hugoe on 3/19/2018.
 */

/**
 * Dagger module to inject dependencies concerning the location acquisition
 */
@Component(modules = {LocationModule.class})
public interface LocationComponent {
    LocationRequest getLocationRequest();

    FusedLocationProviderClient getFusedLocationProvider();
}
