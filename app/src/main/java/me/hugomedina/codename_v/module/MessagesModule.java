package me.hugomedina.codename_v.module;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

import javax.xml.datatype.Duration;

import dagger.Module;
import dagger.Provides;
import me.hugomedina.codename_v.interfaces.MessagesApi;

/**
 * Created by hugoe on 3/21/2018.
 */

/**
 * Provides a Snackbar built with a selected String resource ready to be shown
 */
@Module
public class MessagesModule {

    private View view;

    public MessagesModule(View view){
        this.view = view;
    }

    @Provides
    MessagesApi messagesApi(){
        return new MessagesApi() {
            @Override
            public Snackbar sendMessage(int resId) {
                return Snackbar.make(view, resId, Snackbar.LENGTH_LONG);
            }
        };
    }

}
