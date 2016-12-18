package org.myteam.learnnewandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.myteam.learnnewandroid.customscoller.CustomScollerView;

public class CustomScollerActivity extends AppCompatActivity {
    CustomScollerView customScollerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //scoller
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_scoller);
        customScollerView= (CustomScollerView) findViewById(R.id.customScollerView);
    }
}
