package com.gamoshi.app.fr;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;

import com.gamoshi.app.R;

public class Setting extends PreferenceFragment {
   // private PreferencesHelper mHelper;

    EditTextPreference current_request;
    EditTextPreference full_request;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        addPreferencesFromResource(R.xml.preferences_lbs);
        current_request = (EditTextPreference)findPreference("current_request");
        full_request = (EditTextPreference)findPreference("full_request");
       // current_request.setText(prefs.getString("current_request",));
        //PreferenceManager.setDefaultValues();
    }

    @Override
    public Preference findPreference(CharSequence key) {
        return super.findPreference(key);
        //set
    }
}
