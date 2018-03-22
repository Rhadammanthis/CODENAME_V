package me.hugomedina.codename_v.util;

import java.util.HashMap;

import me.hugomedina.codename_v.R;

/**
 * Created by hugoe on 3/20/2018.
 */

/**
 * Helps retrieving the accurate background color according to the weathers code provided by openweathermap
 */
public class BackgroundColorHashMap extends HashMap<Integer, Integer> {
    public BackgroundColorHashMap(){

        //Thunderstorm
        this.put(200, R.string.background_thunder);
        this.put(201, R.string.background_thunder);
        this.put(202, R.string.background_thunder);
        this.put(210, R.string.background_thunder);
        this.put(211, R.string.background_thunder);
        this.put(212, R.string.background_thunder);
        this.put(221, R.string.background_thunder);
        this.put(230, R.string.background_thunder);
        this.put(231, R.string.background_thunder);
        this.put(232, R.string.background_thunder);

        //Drizzle
        this.put(300, R.string.background_rain);
        this.put(301, R.string.background_rain);
        this.put(302, R.string.background_rain);
        this.put(310, R.string.background_rain);
        this.put(311, R.string.background_rain);
        this.put(312, R.string.background_rain);
        this.put(313, R.string.background_rain);
        this.put(314, R.string.background_rain);
        this.put(321, R.string.background_rain);

        //Rain
        this.put(500, R.string.background_rain);
        this.put(501, R.string.background_rain);
        this.put(502, R.string.background_rain);
        this.put(503, R.string.background_rain);
        this.put(504, R.string.background_rain);
        this.put(511, R.string.background_rain);
        this.put(520, R.string.background_rain);
        this.put(521, R.string.background_rain);
        this.put(522, R.string.background_rain);
        this.put(531, R.string.background_rain);

        //Snow
        this.put(600, R.string.background_snow);
        this.put(601, R.string.background_snow);
        this.put(602, R.string.background_snow);
        this.put(611, R.string.background_snow);
        this.put(612, R.string.background_snow);
        this.put(615, R.string.background_snow);
        this.put(616, R.string.background_snow);
        this.put(620, R.string.background_snow);
        this.put(621, R.string.background_snow);
        this.put(622, R.string.background_snow);

        //Clear
        this.put(800, R.string.background_clear);

        //Clouds
        this.put(801, R.string.background_clouds);
        this.put(802, R.string.background_clouds);
        this.put(803, R.string.background_clouds);
        this.put(804, R.string.background_clouds);

        //Atmosphere
        this.put(701, R.string.background_haze);
        this.put(711, R.string.background_smoke);
        this.put(721, R.string.background_haze);
        this.put(741, R.string.background_smoke);
    }
}
