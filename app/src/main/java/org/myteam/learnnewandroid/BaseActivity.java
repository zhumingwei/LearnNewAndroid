package org.myteam.learnnewandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by zhumingwei on 16/10/26.
 */
public class BaseActivity extends AppCompatActivity {
    public void startActivity(Class clazz) {
        startActivity(new Intent(this,clazz));
    }
}
