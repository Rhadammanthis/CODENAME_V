package me.hugomedina.codename_v.module;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import me.hugomedina.codename_v.adapter.NextDaysAdapter;
import me.hugomedina.codename_v.adapter.WeatherForecastAdapter;
import me.hugomedina.codename_v.interfaces.WeatherDataApi;
import me.hugomedina.codename_v.util.BackgroundColorHashMap;
import me.hugomedina.codename_v.util.WeatherIconHashMap;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static me.hugomedina.codename_v.module.DateFormatModule.DAY_FORMAT;
import static me.hugomedina.codename_v.module.DateFormatModule.HOUR_FORMAT;

/**
 * Created by hugoe on 3/19/2018.
 */

/**
 * Provides dependencies need to retrieve and handle the weather data
 */
@Module(includes = {OkHttpClientModule.class, DateFormatModule.class})
public class WeatherDataModule {

    @Provides
    public WeatherDataApi randomUsersApi(Retrofit retrofit){
        return retrofit.create(WeatherDataApi.class);
    }

    @Provides
    public WeatherForecastAdapter weatherPredictionAdapter(Context context, WeatherIconHashMap weatherIconHashMap, @Named(HOUR_FORMAT)SimpleDateFormat simpleDateFormat){
        return new WeatherForecastAdapter(context, weatherIconHashMap, simpleDateFormat);
    }

    @Provides
    public NextDaysAdapter nextDaysAdapter(Context context, WeatherIconHashMap weatherIconHashMap, @Named(DAY_FORMAT)SimpleDateFormat simpleDateFormatDay,
                                           @Named(HOUR_FORMAT)SimpleDateFormat simpleDateFormatHours){
        return new NextDaysAdapter(context, weatherIconHashMap, simpleDateFormatHours, simpleDateFormatDay);
    }

    @Provides
    public Retrofit retrofit(OkHttpClient okHttpClient,
                             GsonConverterFactory gsonConverterFactory, Gson gson){
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(gsonConverterFactory)
                .build();
    }

    @Provides
    public Gson gson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

    @Provides
    public GsonConverterFactory gsonConverterFactory(Gson gson){
        return GsonConverterFactory.create(gson);
    }

    @Provides
    public WeatherIconHashMap weatherIconHashMap(){
        return new WeatherIconHashMap();
    }

    @Provides
    public BackgroundColorHashMap backgroundColorHashMap(){
        return new BackgroundColorHashMap();
    }
}
