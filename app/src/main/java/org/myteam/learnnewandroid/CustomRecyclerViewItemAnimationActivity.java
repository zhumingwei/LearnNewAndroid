package org.myteam.learnnewandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.myteam.learnnewandroid.CustomRecyclerViewItemAnimation.CustomerItemAnimator;
import org.myteam.learnnewandroid.CustomRecyclerViewItemAnimation.RecyclerViewAdapter;

public class CustomRecyclerViewItemAnimationActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_recycler_view_item_animation);
        initView();
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new CustomerItemAnimator());
        recyclerView.getItemAnimator();
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.initData();
    }
}
