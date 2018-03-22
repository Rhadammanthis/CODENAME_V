package me.hugomedina.codename_v.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
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
 * Adapter that handles the data for the next available days
 */
public class NextDaysAdapter extends RecyclerView.Adapter<NextDaysAdapter.NextDaysViewHolder>{

    private List<List<ListItem>> resultsList = new ArrayList<>();
    private Context context;
    private SimpleDateFormat dateFormatHours;
    private SimpleDateFormat dateFormatDays;
    private WeatherIconHashMap iconHashMap;

    //Dependencies needed to correctly inflate adapter
    public NextDaysAdapter(Context context, WeatherIconHashMap iconHashMap, SimpleDateFormat dateFormatHours, SimpleDateFormat dateFormatDays){
        this.context = context;
        this.dateFormatDays = dateFormatDays;
        this.dateFormatHours = dateFormatHours;
        this.iconHashMap = iconHashMap;
    }

    //Boilerplate Adapter method
    @Override
    public NextDaysViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_next_days,
                parent, false);
        ButterKnife.bind(this,view);
        return new NextDaysViewHolder(view);
    }

    //Boilerplate Adapter method
    @Override
    public void onBindViewHolder(final NextDaysViewHolder holder, int position) {

        //Load data into views
        holder.tvDay.setText(dateFormatDays.format(new Date(resultsList.get(position).get(0).getDt() * (long) 1000)));
        holder.ivWeather.setIconResource(context.getString(iconHashMap.get(resultsList.get(position).get(0).getWeather().get(0).getId())));

        holder.rvNextDaysForecast.setLayoutManager(new LinearLayoutManager(context));
        NextDaysForecastAdapter adapter = new NextDaysForecastAdapter(context, dateFormatHours, iconHashMap);
        adapter.setItems(resultsList.get(position));
        holder.rvNextDaysForecast.setAdapter(adapter);


        holder.rlDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Checks if the Next Days Forecast list should be showed or hidden
                holder.rvNextDaysForecast.setVisibility(holder.rvNextDaysForecast.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE );
            }
        });

    }

    //Handles external population of resultList
    public void setItems(List<ListItem> resultsList) {
        List<List<ListItem>> daysList = new ArrayList<>();

        //Loop to separate the results list into other list with items that share the same day
        String savedDay;
        List<ListItem> dayItems = new ArrayList<>();
        for(int i=0; i < resultsList.size() - 1; i++){

            savedDay = resultsList.get(i).getDtTxt().substring(8,10);
            String newDay = resultsList.get(i+1).getDtTxt().substring(8,10);

            if(savedDay.equals(newDay)){
                dayItems.add(resultsList.get(i));
            }
            else{
                dayItems.add(resultsList.get(i));
                daysList.add(dayItems);
                dayItems = new ArrayList<>();
            }
        }

        this.resultsList = daysList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return resultsList.size();
    }

    //Adapter's own ViewHolder
    public class NextDaysViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_next_day_day) TextView tvDay;
        @BindView(R.id.icon_view_next_day_forecast) WeatherIconView ivWeather;
        @BindView(R.id.rv_next_days_forecast) RecyclerView rvNextDaysForecast;
        @BindView(R.id.layout_next_days_day) RelativeLayout rlDay;

        public NextDaysViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
