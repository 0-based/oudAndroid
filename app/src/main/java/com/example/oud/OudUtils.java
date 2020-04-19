package com.example.oud;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class OudUtils {

    public static <T> String commaSeparatedListQueryParameter(ArrayList<T> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (T item : list) {
            stringBuilder.append(item.toString());
            stringBuilder.append(',');
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    public static <T> String commaSeparatedListQueryParameter(T[] list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (T item : list) {
            stringBuilder.append(item.toString());
            stringBuilder.append(',');
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    public static void saveUserData(View v, String token, String userId) {

        SharedPreferences prefs = v.getContext().getSharedPreferences(Constants.SHARED_PREFERENCES_FILE_NAME, MODE_PRIVATE);

        token = "Bearer " + token;

        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.SHARED_PREFERENCES_TOKEN_NAME, token);
        prefsEditor.putString(Constants.SHARED_PREFERENCES_USER_ID_NAME, userId);

        prefsEditor.apply();    //token saved in shared preferences

    }

    public static void saveUserData(Context context, String token, String userId) {

        SharedPreferences prefs = context.getSharedPreferences(Constants.SHARED_PREFERENCES_FILE_NAME, MODE_PRIVATE);

        token = "Bearer " + token;

        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.SHARED_PREFERENCES_TOKEN_NAME, token);
        prefsEditor.putString(Constants.SHARED_PREFERENCES_USER_ID_NAME, userId);
        prefsEditor.apply();    //token saved in shared preferences

    }

    public static String getToken(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.SHARED_PREFERENCES_FILE_NAME, MODE_PRIVATE);
        return prefs.getString(Constants.SHARED_PREFERENCES_TOKEN_NAME, "");

    }

    public static String getUserId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.SHARED_PREFERENCES_FILE_NAME, MODE_PRIVATE);
        return prefs.getString(Constants.SHARED_PREFERENCES_USER_ID_NAME, "");

    }


    public static boolean isAutoPlayback(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.SHARED_PREFERENCES_FILE_NAME, MODE_PRIVATE);
        if(!prefs.contains(Constants.SHARED_PREFERENCES_IS_AUTO_PLAY_NAME)){
            SharedPreferences.Editor prefsEditor = prefs.edit();
            prefsEditor.putBoolean(Constants.SHARED_PREFERENCES_IS_AUTO_PLAY_NAME,true);
            prefsEditor.commit();
        }

        return prefs.getBoolean(Constants.SHARED_PREFERENCES_IS_AUTO_PLAY_NAME,true);
    }

    public static void setIsAutoPlayback(Context context,boolean isAutoPlayback){
        SharedPreferences prefs = context.getSharedPreferences(Constants.SHARED_PREFERENCES_FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putBoolean(Constants.SHARED_PREFERENCES_IS_AUTO_PLAY_NAME,isAutoPlayback);
        prefsEditor.commit();
    }

    public static boolean isNotificationAllowed(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.SHARED_PREFERENCES_FILE_NAME, MODE_PRIVATE);
        if(!prefs.contains(Constants.SHARED_PREFERENCES_IS_NOTIFICATION_ALLOWED_NAME)){
            SharedPreferences.Editor prefsEditor = prefs.edit();
            prefsEditor.putBoolean(Constants.SHARED_PREFERENCES_IS_NOTIFICATION_ALLOWED_NAME,true);
            prefsEditor.commit();
        }

        return prefs.getBoolean(Constants.SHARED_PREFERENCES_IS_NOTIFICATION_ALLOWED_NAME,true);
    }

    public static void setIsNotificationAllowed(Context context,boolean isAllowed){
        SharedPreferences prefs = context.getSharedPreferences(Constants.SHARED_PREFERENCES_FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putBoolean(Constants.SHARED_PREFERENCES_IS_NOTIFICATION_ALLOWED_NAME,isAllowed);
        prefsEditor.commit();
    }




    public static String convertImageToFullUrl(String imageUrl) {

        if(Constants.MOCK)
            return imageUrl;

        imageUrl = ("https://oud-zerobase.me/api/" + imageUrl);

        for (int i = 0; i < imageUrl.length(); i++) {
            if (imageUrl.charAt(i) == (char) 92) {
                Log.e("profile fragment", imageUrl.charAt(i) + " at position: " + i);
                StringBuilder tempString = new StringBuilder(imageUrl);
                tempString.setCharAt(i, '/');
                imageUrl = tempString.toString();
            }
        }
        return imageUrl;
    }
}