package me.hugomedina.codename_v.interfaces;

import android.support.design.widget.Snackbar;

/**
 * Created by hugoe on 3/21/2018.
 */

/**
 * Passes along a string resource to present a message to the user
 */
public interface MessagesApi {
    Snackbar sendMessage(int resId);
}
