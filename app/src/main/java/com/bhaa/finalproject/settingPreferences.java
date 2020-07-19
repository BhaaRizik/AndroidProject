package com.bhaa.finalproject;

import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;


public class settingPreferences extends PreferenceFragmentCompat {


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preference, rootKey);

    }
}
