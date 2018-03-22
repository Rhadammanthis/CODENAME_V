package me.hugomedina.codename_v.module;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import me.hugomedina.codename_v.R;

/**
 * Created by hugoe on 3/20/2018.
 */

/**
 * Provides two different Data Formatters
 */
@Module(includes = ContextModule.class)
public class DateFormatModule {

    public static final String HOUR_FORMAT = "hour_format";
    public static final String DAY_FORMAT = "day_format";

    //Formats a Date object into a String with the hours of the day and am or pm just like "12 pm"
    @Provides
    @Named(HOUR_FORMAT)
    public SimpleDateFormat simpleDateFormatHour(Context context){
        SimpleDateFormat format = new SimpleDateFormat(context.getString(R.string.formatter_hours));
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        return format;
    }

    //Formats a Date object into a String with name of the day just like "Thursday"
    @Provides
    @Named(DAY_FORMAT)
    public SimpleDateFormat simpleDateFormatDayName(Context context){
        SimpleDateFormat format = new SimpleDateFormat(context.getString(R.string.formatter_days));
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        return format;
    }

}
