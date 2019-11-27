package ca.davidpellegrini.scorekeepersolution;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import androidx.preference.PreferenceManager;


public class SettingsFragment extends PreferenceFragment{
private SharedPreferences prefs;
private boolean rememberScore = true;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);

    }

}
