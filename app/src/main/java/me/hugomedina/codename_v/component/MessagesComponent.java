package me.hugomedina.codename_v.component;

import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;

import dagger.Component;
import me.hugomedina.codename_v.interfaces.MessagesApi;
import me.hugomedina.codename_v.module.LocationModule;
import me.hugomedina.codename_v.module.MessagesModule;

/**
 * Created by hugoe on 3/19/2018.
 */

/**
 * Dagger module to inject dependencies concerning presentation of Snackbar messages to the user
 */
@Component(modules = {MessagesModule.class})
public interface MessagesComponent {
    MessagesApi getMessagesApi();
}
