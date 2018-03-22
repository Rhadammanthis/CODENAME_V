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

/**
 * Adapter that handles the data for the next available days' forecast
 */
public class NextDaysForecastAdapter extends RecyclerView.Adapter<NextDaysForecastAdapter.NextDaysForecastViewHolder>{

    private List<ListItem> resultsList = new ArrayList<>();
    private Context context;
    private SimpleDateFormat dateFormat;
    private WeatherIconHashMap iconHashMap;

    //Dependencies needed to correctly inflate adapter
    public NextDaysForecastAdapter(Context context, SimpleDateFormat dateFormat, WeatherIconHashMap iconHashMap){
        this.context = context;
        this.dateFormat = dateFormat;
        this.iconHashMap = iconHashMap;
    }

    //Boilerplate Adapter method
    @Override
    public NextDaysForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_next_days_forecast,
                parent, false);
        ButterKnife.bind(this,view);
        return new NextDaysForecastViewHolder(view);
    }

    //Boilerplate Adapter method
    @Override
    public void onBindViewHolder(NextDaysForecastViewHolder holder, int position) {
        holder.tvTemperature.setText(String.format(Locale.ENGLISH,"%.1f", resultsList.get(position).getMain().getTemp()) + context.getString(R.string.ui_degrees));
        holder.ivWeather.setIconResource(context.getString(iconHashMap.get(resultsList.get(position).getWeather().get(0).getId())));
        holder.tvTime.setText(dateFormat.format(new Date(resultsList.get(position).getDt() * (long) 1000)));
    }

    //Handles external population of resultList
    public void setItems(List<ListItem> resultsList) {
        this.resultsList = resultsList;
        notifyDataSetChanged();
    }

    //Boilerplate Adapter method
    @Override
    public int getItemCount() {
        return resultsList.size();
    }

    //Adapter's own ViewHolder
    public class NextDaysForecastViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_next_days_forecast_time) TextView tvTime;
        @BindView(R.id.text_next_days_forecast_temperature) TextView tvTemperature;
        @BindView(R.id.icon_view_next_days_forecast_weather) WeatherIconView ivWeather;

        public NextDaysForecastViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
