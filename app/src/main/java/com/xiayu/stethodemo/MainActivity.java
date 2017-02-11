package com.xiayu.stethodemo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sp = getSharedPreferences("config", 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("name", "xiayu");
        edit.putBoolean("handsome?", true);
        edit.commit();
    }
}
