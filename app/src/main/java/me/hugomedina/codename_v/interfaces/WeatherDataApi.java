package me.hugomedina.codename_v.interfaces;

import me.hugomedina.codename_v.model.Results;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by hugoe on 3/19/2018.
 */

/**
 * Exposes method to make an http request with retrofit
 */
public interface WeatherDataApi {
    @GET("forecast")
    Call<Results> getWeatherData(@Query("lat") String lat, @Query("lon") String lon, @Query("units") String units,@Query("appid") String appId);
}
