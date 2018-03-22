package me.hugomedina.codename_v.module;

import android.content.Context;

import java.io.File;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by hugoe on 3/19/2018.
 */
@Module
public class OkHttpClientModule {

    @Provides
    public OkHttpClient okHttpClient(){
        return new OkHttpClient()
                .newBuilder()
                .build();
    }

}