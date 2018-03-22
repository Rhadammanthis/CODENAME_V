package me.hugomedina.codename_v;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.pwittchen.weathericonview.WeatherIconView;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.hugomedina.codename_v.adapter.NextDaysAdapter;
import me.hugomedina.codename_v.adapter.WeatherForecastAdapter;
import me.hugomedina.codename_v.component.DaggerLocationComponent;
import me.hugomedina.codename_v.component.DaggerMessagesComponent;
import me.hugomedina.codename_v.component.DaggerWeatherDataComponent;
import me.hugomedina.codename_v.component.LocationComponent;
import me.hugomedina.codename_v.component.MessagesComponent;
import me.hugomedina.codename_v.component.WeatherDataComponent;
import me.hugomedina.codename_v.interfaces.PersistentDataApi;
import me.hugomedina.codename_v.interfaces.WeatherDataApi;
import me.hugomedina.codename_v.model.ListItem;
import me.hugomedina.codename_v.model.Results;
import me.hugomedina.codename_v.module.ContextModule;
import me.hugomedina.codename_v.module.MessagesModule;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static me.hugomedina.codename_v.module.LocationModule.MY_PERMISSIONS_REQUEST_LOCATION;

public class MainActivity extends AppCompatActivity{

    //Dagger dependencies
    LocationComponent locationComponent;
    WeatherDataComponent weatherDataComponent;
    MessagesComponent messagesComponent;
    WeatherDataApi weatherDataApi;
    PersistentDataApi persistentDataApi;

    //View model dependencies
    @BindView(R.id.content) NestedScrollView content;
    @BindView(R.id.rl_no_permissions) RelativeLayout rlNoPermissions;
    @BindView(R.id.layout_rain) LinearLayout layoutRain;
    @BindView(R.id.button_request_permission) Button bRequestPermissions;
    @BindView(R.id.text_weather_conditions) TextView tvWeatherConditions;
    @BindView(R.id.text_city) TextView tvCity;
    @BindView(R.id.text_wind) TextView tvWind;
    @BindView(R.id.text_rain) TextView tvRain;
    @BindView(R.id.icon_view_weather) WeatherIconView ivWeather;
    @BindView(R.id.text_temperature) TextView tvTemperature;
    @BindView(R.id.rv_weather_forecast) RecyclerView rvWeatherPrediction;
    @BindView(R.id.rv_next_days) RecyclerView rvNextDays;
    @BindView(R.id.swipeRefresh) SwipeRefreshLayout swipeRefreshLayout;

    WeatherForecastAdapter weatherForecastAdapter;
    NextDaysAdapter nextDaysAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Binds layout views with ButterKnife
        ButterKnife.bind(this);

        //Builds local instances of the Dagger injected components
        locationComponent = DaggerLocationComponent.builder()
                .contextModule(new ContextModule(this))
                .build();

        weatherDataComponent = DaggerWeatherDataComponent.builder()
                .contextModule(new ContextModule(this))
                .build();

        messagesComponent = DaggerMessagesComponent.builder()
                .messagesModule(new MessagesModule(swipeRefreshLayout))
                .build();

        //Creates local instances of certain dependencies just for the sake of easier code reading
        weatherDataApi = weatherDataComponent.getWeatherDataService();
        persistentDataApi = weatherDataComponent.getPersistentDataApi();
        weatherForecastAdapter = weatherDataComponent.getWeatherPredictionAdapter();
        nextDaysAdapter = weatherDataComponent.getNextDaysAdapter();

        //Requests the user's location
        requestLocation();

        //Initial configuration of views that require so
        initViews();
    }

    private void initViews() {

        //On click initializes the request location flow again
        bRequestPermissions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestLocation();
            }
        });

        //Initializes RecycleView
        rvWeatherPrediction.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvNextDays.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //So the RecyclerView can work as intended with it's NestedScrollView parent
        rvNextDays.setNestedScrollingEnabled(false);

        //Allows the user to initialize the data gathering flow when refreshing
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestLocation();
            }
        });

        //Loads the data from the persistent model into the views
        dumpDataToView(persistentDataApi.getWeatherData());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        //Launches the location flow if the permissions have been granted
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //Hide the "No permissions" views, presents spinner and initializes the location flow
                    rlNoPermissions.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(true);
                    requestLocation();

                }
            }

        }
    }

    /**
     * Starts the flow request the user's location starting with checking if the permissions have been/are granted
     */
    public void requestLocation() {

        //Perform initial check
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user
                new AlertDialog.Builder(this)
                        .setTitle(R.string.system_alert)
                        .setMessage(R.string.system_request_permissions)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user back again once explanation has been shown
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        } else {

            //Now that we know that permissions have been granted, start listening for location updates
            locationComponent.getFusedLocationProvider().requestLocationUpdates(locationComponent.getLocationRequest(), new LocationCallback() {
                        @Override
                        public void onLocationResult(LocationResult locationResult) {

                            //Query for weather data based on the users location
                            getWeatherData(locationResult.getLastLocation());
                        }
                    },
                    Looper.myLooper());

            //Checks if there's a location in the provider
            locationComponent.getFusedLocationProvider().getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {

                            // Got last known location
                            if (location != null) {

                                //Query for weather data based on the users location
                                getWeatherData(location);
                            }
                            else {

                                //If no location has been retrieved, stops refreshing layout and prompts a message to the user
                                swipeRefreshLayout.setRefreshing(false);
                                messagesComponent.getMessagesApi().sendMessage(R.string.system_no_location).show();
                            }
                        }
                    }).addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    //If there's a failure retrieving the location prompts the user with a message
                    messagesComponent.getMessagesApi().sendMessage(R.string.system_no_location).show();
                }
            });
        }
    }

    /**
     * Loads the weather data into the main views
     * @param results The Results gathered from the OpenWeatherMap API
     */
    public void dumpDataToView(Results results){

        //If somehow the results are null, return
        if(results == null)
            return;

        //Hides the "No location" views and shows those concerning the weather data
        rlNoPermissions.setVisibility(View.GONE);
        content.setVisibility(View.VISIBLE);

        //Stops the RefreshLayout spinner
        swipeRefreshLayout.setRefreshing(false);

        //The first item of the results list is the current weather information
        ListItem currentWeather = results.getList().get(0);

        //Rounds the temperature to it's closes int for the sake of user experience as it's hard to actually perceive decimals when it comes to temperature
        int roundedTemperature = round(currentWeather.getMain().getTemp());

        //Updated the app's background color based on the current weather's sate
        swipeRefreshLayout.setBackgroundColor(Color.parseColor(getString(weatherDataComponent.getBackgroundColorHashMap().get(currentWeather.getWeather().get(0).getId()))));

        //Prints the current weather's data into the main views
        tvWeatherConditions.setText(currentWeather.getWeather().get(0).getDescription());
        tvCity.setText(results.getCity().getName());
        tvTemperature.setText(String.format(Locale.ENGLISH,"%d", roundedTemperature) + getString(R.string.ui_degrees));
        ivWeather.setIconResource(getString(weatherDataComponent.getWeatherIconHashMap().get(currentWeather.getWeather().get(0).getId())));
        tvWind.setText(String.format(Locale.ENGLISH,"%.1f", currentWeather.getWind().getSpeed()) + " m/s");

        //If there's no rain, hide the whole view for continuity
        if(currentWeather.getRain() != null)
            tvRain.setText(String.format(Locale.ENGLISH,"%.2f", currentWeather.getRain().getJsonMember3h()) + " mm");
        else
            layoutRain.setVisibility(View.GONE);

        //Supplies the weather data to the adapter and updates the view accordingly
        weatherForecastAdapter.setItems(results.getList().subList(0,9));
        rvWeatherPrediction.setAdapter(weatherForecastAdapter);

        nextDaysAdapter.setItems(results.getList());
        rvNextDays.setAdapter(nextDaysAdapter);

    }

    /**
     * Sends an async request for the weather data based on the user's location
     * @param location Users location
     */
    public void getWeatherData(Location location){

        //Builds a call trough the API
        Call<Results> weatherDataCall = weatherDataApi.getWeatherData(String.valueOf(location.getLatitude()),
                String.valueOf(location.getLongitude()),
                getString(R.string.request_units),
                getString(R.string.request_weather_api_key));

        //Enqueues the call
        weatherDataCall.enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {

                //Checks if result code is successful
                if(response.isSuccessful()) {

                    //Saves data to persistent data model
                    persistentDataApi.saveWeatherData(response.body());

                    //Updates views with the fetched data
                    dumpDataToView(response.body());
                }
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {

                //If the request fails, hide the spinner and prompt message to user
                swipeRefreshLayout.setRefreshing(false);
                messagesComponent.getMessagesApi().sendMessage(R.string.system_no_internet).show();
            }
        });

    }

    /**
     * Rounds the temperature to it's closest int
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
