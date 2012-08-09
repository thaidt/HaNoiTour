package com.example.hanoitours;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ToggleButton;

public class SettingActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ToggleButton button = (ToggleButton) findViewById(R.id.toggleSatelline);
        button.setChecked(Setting.satelline);
        button = (ToggleButton) findViewById(R.id.toggleTraffic);
        button.setChecked(Setting.traffic);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }
    
    public void setSatelline(View view){
    	ToggleButton button = (ToggleButton) findViewById(R.id.toggleSatelline);
    	Setting.setSatelline(button.isChecked());
    }

    public void setTraffic(View view){
    	ToggleButton button = (ToggleButton) findViewById(R.id.toggleTraffic);
    	Setting.setTraffic(button.isChecked());
    }
}
