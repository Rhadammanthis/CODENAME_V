package me.hugomedina.codename_v.model;

import android.content.Context;
import android.content.SharedPreferences;

import me.hugomedina.codename_v.R;

/**
 * Created by hugoe on 3/19/2018.
 */

public class Persistent {
    SharedPreferences sharedPreferences;
    Context context;

    public Persistent(Context context){
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
    }

    public void saveWeatherModel(Results results){
        sharedPreferences.edit().putString(context.getString(R.string.persisten_lat_long),
                results.toString()).apply();
    }
}
