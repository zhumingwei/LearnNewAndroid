package org.myteam.learnnewandroid;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.myteam.learnnewandroid.customscoller.GetApplicationData;

public class ScrollingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        initView();
    }

    private void initView() {

    }


    public void ripple(View view) {
        startActivity(RippleActivity.class);
    }

    public void customItemAnimation(View view) {
        startActivity(CustomRecyclerViewItemAnimationActivity.class);
    }

    public void customScollerAnimation(View view) {
        startActivity(CustomScollerActivity.class);
    }

    public void getApplicationData(View view) {
        startActivity(GetApplicationData.class);
    }

    public void getClassData(View view) {
        startActivity(ClassDataActivity.class);
    }

    public void gotoTapMianActivity(View view){
        startActivity(TapMainActivity.class);
    }

    public void gotoMaterialScrollingActivity(View view){
        startActivity(MaterialScrollingActivity.class);
    }

    public void AnalysisContentResolver(View view){
        startActivity(AnalysisContentResolverActivity.class);
    }

    public void gotoDrawerActivity(View view){
        startActivity(DrawerActivity.class);
    }
}
