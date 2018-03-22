package me.hugomedina.codename_v.interfaces;

import me.hugomedina.codename_v.model.Results;

/**
 * Created by hugoe on 3/19/2018.
 */

/**
 * Exposes methods to save and retrieve data from the persistent model
 */
public interface PersistentDataApi {
    void saveWeatherData(Results results);

    Results getWeatherData();
}
