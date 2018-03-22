package me.hugomedina.codename_v.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;
import me.hugomedina.codename_v.R;
import me.hugomedina.codename_v.interfaces.PersistentDataApi;
import me.hugomedina.codename_v.interfaces.WeatherDataApi;
import me.hugomedina.codename_v.model.Results;
import retrofit2.Retrofit;

/**
 * Created by hugoe on 3/19/2018.
 */

/**
 * Provides an API with methods to save and retrieve the last Weather data from the persistent model
 * A SharedPreferences object is used to store whatever is needed.
 */
@Module(includes = {SharedPreferencesModule.class, WeatherDataModule.class})
public class PersistentDataModule {
    @Provides
    public PersistentDataApi persistentDataApi(final SharedPreferences sharedPreferences, final Context context, final Gson gson){
        return new PersistentDataApi() {
            @Override
            public void saveWeatherData(Results results) {

                //Casts the Object into a JSON string so it becomes easier to store
                String json = gson.toJson(results);
                sharedPreferences.edit().putString(context.getString(R.string.local_weather_data),
                        json).apply();
            }

            @Override
            public Results getWeatherData(){

                //Retrieves JSON string and casts it into a Results Object
                Results results = gson.fromJson(sharedPreferences.getString(context.getString(R.string.local_weather_data), ""), Results.class);
                return results;
            }
        };
    }
}
