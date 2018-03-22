package me.hugomedina.codename_v.module;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.hugomedina.codename_v.R;
import me.hugomedina.codename_v.model.Results;


/**
 * Created by hugoe on 3/18/2018.
 */

/**
 * Provides a single instance of the SharedPreferences object to be latter used trough it's API
 */
@Module(includes = ContextModule.class)
@Singleton
public class SharedPreferencesModule {

    @Provides
    public SharedPreferences sharedPreferences(Context context){
        return context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
    }

}
