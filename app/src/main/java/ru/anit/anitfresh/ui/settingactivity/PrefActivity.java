package ru.anit.anitfresh.ui.settingactivity;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import ru.anit.anitfresh.R;


/**
 * Created by 79900 on 19.09.2016.
 */
public class PrefActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting_preference);
    }

}