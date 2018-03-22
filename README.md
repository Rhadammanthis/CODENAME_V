# Njord - Just The Weather

## Purpose

Weather app for Android built with the intention of being user friendly and presenting a clean interface with just the relevant information that users might need.

## Overview

It's fairly simple! The app just initially ask for the user's permission to access their location and when granted uses the public API from [Open Weather Map](https://openweathermap.org/forecast5) to fetch weather data for the next 5 days. 
This data is given as a collection of 40 items each one representing the weather information in a 3 hour interval so additional adjustments have to be made in order to present them in an orderly fashion.

The newest information is stored and then presented in the app's main view. It is paramount that the most relevant information can be delivered in a clear way to be make sure the user is up-to-date.
The additional information can be found below were the user is free to scroll trough today's full forecast as well as that of the next few days.

Additionally, new weather information can be requested by swiping down the application's main view.

## Libraries

### [Dagger 2](https://github.com/google/dagger)
Provides a modern approach to dependency injection facilitating the development of individual modules that can easily be implemented.

### [ButterKnife](http://jakewharton.github.io/butterknife/)
Fast and simple field and method binding for Android views. 

### [Retrofit](https://github.com/square/retrofit)
Used to build HTTP requests.

### [GSON](https://github.com/google/gson)
Allows the easy serialization of POJOs into JSON strings and vice versa.

### [Weather Icon View](https://github.com/pwittchen/WeatherIconView)
A nice library that provides a custom view for displaying weather icons.


## Try it out!

Fork or clone repository, open in Android Studio and run it! Just make sure your `minSDKVersion` is `21`
