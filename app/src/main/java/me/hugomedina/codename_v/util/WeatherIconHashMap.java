package me.hugomedina.codename_v.util;

import java.util.HashMap;

import me.hugomedina.codename_v.R;

/**
 * Created by hugoe on 3/20/2018.
 */

/**
 * Helps retrieving the accurate resource to correctly display the weather icon from
 * Pwittchen's WeatherIconView library using the weathers code provided by openweathermap
 */
public class WeatherIconHashMap extends HashMap<Integer, Integer> {
    public WeatherIconHashMap(){

        //Thunderstorm
        this.put(200, R.string.wi_storm_showers);
        this.put(201, R.string.wi_storm_showers);
        this.put(202, R.string.wi_storm_showers);
        this.put(210, R.string.wi_lightning);
        this.put(211, R.string.wi_lightning);
        this.put(212, R.string.wi_lightning);
        this.put(221, R.string.wi_lightning);
        this.put(230, R.string.wi_storm_showers);
        this.put(231, R.string.wi_storm_showers);
        this.put(232, R.string.wi_storm_showers);

        //Drizzle
        this.put(300, R.string.wi_showers);
        this.put(301, R.string.wi_showers);
        this.put(302, R.string.wi_showers);
        this.put(310, R.string.wi_showers);
        this.put(311, R.string.wi_showers);
        this.put(312, R.string.wi_showers);
        this.put(313, R.string.wi_showers);
        this.put(314, R.string.wi_showers);
        this.put(321, R.string.wi_showers);

        //Rain
        this.put(500, R.string.wi_showers);
        this.put(501, R.string.wi_showers);
        this.put(502, R.string.wi_rain);
        this.put(503, R.string.wi_rain);
        this.put(504, R.string.wi_thunderstorm);
        this.put(511, R.string.wi_snow);
        this.put(520, R.string.wi_sleet);
        this.put(521, R.string.wi_sleet);
        this.put(522, R.string.wi_rain);
        this.put(531, R.string.wi_showers);

        //Snow
        this.put(600, R.string.wi_snowflake_cold);
        this.put(601, R.string.wi_snowflake_cold);
        this.put(602, R.string.wi_snowflake_cold);
        this.put(611, R.string.wi_snowflake_cold);
        this.put(612, R.string.wi_snowflake_cold);
        this.put(615, R.string.wi_snowflake_cold);
        this.put(616, R.string.wi_snowflake_cold);
        this.put(620, R.string.wi_snowflake_cold);
        this.put(621, R.string.wi_snowflake_cold);
        this.put(622, R.string.wi_snowflake_cold);

        //Clear
        this.put(800, R.string.wi_day_sunny);

        //Clouds
        this.put(801, R.string.wi_cloud);
        this.put(802, R.string.wi_cloud);
        this.put(803, R.string.wi_cloudy);
        this.put(804, R.string.wi_cloudy);

        //Atmosphere
        this.put(701, R.string.wi_dust);
        this.put(711, R.string.wi_smoke);
        this.put(721, R.string.wi_smoke);
        this.put(741, R.string.wi_dust);
    }
}
