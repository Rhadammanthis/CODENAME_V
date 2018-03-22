package me.hugomedina.codename_v.component;

import dagger.Component;
import me.hugomedina.codename_v.adapter.NextDaysAdapter;
import me.hugomedina.codename_v.adapter.WeatherForecastAdapter;
import me.hugomedina.codename_v.interfaces.PersistentDataApi;
import me.hugomedina.codename_v.interfaces.WeatherDataApi;
import me.hugomedina.codename_v.module.PersistentDataModule;
import me.hugomedina.codename_v.module.WeatherDataModule;
import me.hugomedina.codename_v.util.BackgroundColorHashMap;
import me.hugomedina.codename_v.util.WeatherIconHashMap;

/**
 * Created by hugoe on 3/19/2018.
 */

/**
 * Dagger module to inject dependencies concerning the request and handling of the weather data
 */
@Component(modules = {WeatherDataModule.class, PersistentDataModule.class})
public interface WeatherDataComponent {
    WeatherDataApi getWeatherDataService();

    PersistentDataApi getPersistentDataApi();

    WeatherIconHashMap getWeatherIconHashMap();

    BackgroundColorHashMap getBackgroundColorHashMap();

    WeatherForecastAdapter getWeatherPredictionAdapter();

    NextDaysAdapter getNextDaysAdapter();
}
