package com.example.genesis_trainer_device_improved.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Locale;

public class LocaleHelper {
    private static final String TAG = "LocaleHelper";

    private static final String SELECTED_LANGUAGE = "Locale.Helper.Selected.Language";

    public static void onCreate(Context context) {

        String lang;
        if(getLanguage(context).isEmpty()){
            lang = getPersistedData(context, Locale.getDefault().getLanguage());
        }else {
            lang = getLanguage(context);
        }

        setLocale(context, lang);
        Log.d(TAG, "onCreate: lang= "+lang);
    }

    public static void onCreate(Context context, String defaultLanguage) {
        String lang = getPersistedData(context, defaultLanguage);
        Log.d(TAG, "onCreate: lang  = "+lang);
        setLocale(context, lang);
    }

    public static String getLanguage(Context context) {
        return getPersistedData(context, Locale.getDefault().getLanguage());
    }

    public static void setLocale(Context context, String language) {
        Log.d(TAG, "setLocale: "+ language);
        persist(context, language);
        updateResources(context, language);

    }

    private static String getPersistedData(Context context, String defaultLanguage) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(SELECTED_LANGUAGE, defaultLanguage);
    }

    private static void persist(Context context, String language) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        Log.d(TAG, "persist: language ="+language);
        editor.putString(SELECTED_LANGUAGE, language);
        editor.apply();
    }

    private static void updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        Log.d(TAG, "updateResources: configuration.getLocales() "+configuration.getLocales().toLanguageTags());
        Log.d(TAG, "updateResources: "+locale.getLanguage() );


    }

}
