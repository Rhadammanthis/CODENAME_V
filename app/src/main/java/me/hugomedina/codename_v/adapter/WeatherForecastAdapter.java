package me.hugomedina.codename_v.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.pwittchen.weathericonview.WeatherIconView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.hugomedina.codename_v.R;
import me.hugomedina.codename_v.model.ListItem;
import me.hugomedina.codename_v.util.WeatherIconHashMap;

/**
 * Created by hugoe on 3/20/2018.
 */

public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.WeatherPredictionViewHolder>{

    private List<ListItem> resultsList = new ArrayList<>();
    private Context context;
    private WeatherIconHashMap weatherIconHashMap;
    private SimpleDateFormat simpleDateFormat;

    public WeatherForecastAdapter(Context context, WeatherIconHashMap weatherIconHashMap, SimpleDateFormat simpleDateFormat){
        this.context = context;
        this.weatherIconHashMap = weatherIconHashMap;
        this.simpleDateFormat = simpleDateFormat;
    }

    @Override
    public WeatherPredictionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_weather_forecast,
                parent, false);
        ButterKnife.bind(this,view);
        return new WeatherPredictionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WeatherPredictionViewHolder holder, int position) {

        int roundedTemperature = round(resultsList.get(position).getMain().getTemp());

        holder.tvTemperature.setText(String.format(Locale.ENGLISH,"%d", roundedTemperature) + context.getString(R.string.ui_degrees));
        holder.ivWeather.setIconResource(context.getString(weatherIconHashMap.get(resultsList.get(position).getWeather().get(0).getId())));
        holder.tvTime.setText(simpleDateFormat.format(new Date(resultsList.get(position).getDt() * (long) 1000)));
    }

    //Handles external population of resultList
    public void setItems(List<ListItem> resultsList) {
        this.resultsList = resultsList;
        notifyDataSetChanged();
    }

    //Boilerplate Adapter method
    @Override
    public int getItemCount() {
        return resultsList.size() - 1;
    }

    //Adapter's own ViewHolder
    public class WeatherPredictionViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_prediction_time) TextView tvTime;
        @BindView(R.id.text_prediction_temperature) TextView tvTemperature;
        @BindView(R.id.icon_view_prediction_weather) WeatherIconView ivWeather;

        public WeatherPredictionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * Rounds the temperature to it's closest int for a more accurate read
     * @param d temperature
     * @return rounded temperature
     */
    private int round(double d){
        double dAbs = Math.abs(d);
        int i = (int) dAbs;
        double result = dAbs - (double) i;
        if(result<0.5){
            return d<0 ? -i : i;
        }else{
            return d<0 ? -(i+1) : i+1;
        }
    }
}
