package org.myteam.learnnewandroid.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.myteam.learnnewandroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhumingwei on 16/11/13.
 */
public class CustomScrollerView extends LinearLayout {
    private View rootView;
    private LinearLayout linearLayout1;
    private LinearLayout linearLayout2;
    private LinearLayout linearLayout3;
    private TextView tv1, tv2, tv3;
    private ImageView iv1, iv2, iv3;
    List<String> stringList = new ArrayList<String>();
    int index = 0;
    int itemHeight;

    public CustomScrollerView(Context context) {
        super(context);
        initView(null, 0);
    }

    public CustomScrollerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs, 0);
    }

    public CustomScrollerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs, defStyleAttr);
    }

    private void initView(AttributeSet attrs, int defStyleAttr) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_scroller_view, this);
        rootView = findViewById(R.id.rootview);
        linearLayout1 = (LinearLayout) findViewById(R.id.layout1);
        linearLayout2 = (LinearLayout) findViewById(R.id.layout2);
        linearLayout3 = (LinearLayout) findViewById(R.id.layout3);
        tv1 = (TextView) findViewById(R.id.textview1);
        tv2 = (TextView) findViewById(R.id.textview2);
        tv3 = (TextView) findViewById(R.id.textview3);
        iv1 = (ImageView) findViewById(R.id.imageview1);
        iv2 = (ImageView) findViewById(R.id.imageview2);
        iv3 = (ImageView) findViewById(R.id.imageview3);

        log(rootView.getClass().getSimpleName());
        stringList.add("string1");
        stringList.add("string2");
        stringList.add("string3");
        stringList.add("string4");
        itemHeight = dp2Px(20);
    }

    public void startAnimation() {
        index = 0;
        iv1.setAlpha(1f);
        iv1.setRotation(0);
        iv2.setAlpha(1f);
        iv2.setRotation(0);
        tv1.setText(stringList.get(0));
        tv2.setText(stringList.get(1));
        tv3.setText(stringList.get(2));
        rootView.scrollTo(0, -itemHeight);
        rotateAnimation();
    }

    public void rotateAnimation() {
        float count = 1.75f;//0.8秒转多少圈
        ObjectAnimator objectAnimator;
        final ImageView iv;
        if (index == 0) {
            iv = iv1;
        } else if (index == stringList.size() - 1) {
            iv = iv2;
        } else {
            iv = iv2;
        }

        objectAnimator = ObjectAnimator.ofFloat(iv, "rotation", 0, count * 360);
        objectAnimator.setDuration(800);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

        final ObjectAnimator ob1 = ObjectAnimator.ofFloat(iv, "alpha", 0, 1f);
        ob1.setDuration(200);
        ob1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                scrollerToTop();
            }
        });

        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                iv.setRotation(0);
                iv.setImageResource(R.mipmap.fragment_main_head_style1_check_img);
                ob1.start();
            }
        });
        objectAnimator.start();
    }

    private void scrollerToTop() {
        int duration = 200;
        ValueAnimator valueAnimator;
        if (index == 0) {
            valueAnimator = ValueAnimator.ofInt(-itemHeight, 0).setDuration(duration);
        } else if (index == stringList.size() - 1) {
            valueAnimator = ValueAnimator.ofInt(0, itemHeight).setDuration(duration);
        } else {
            valueAnimator = ValueAnimator.ofInt(0, itemHeight).setDuration(duration);
        }

        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                scrollerToTopEnd();
            }
        });
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int i = (int) valueAnimator.getAnimatedValue();
                rootView.scrollTo(0, i);
            }
        });
        valueAnimator.start();
    }

    private void scrollerToTopEnd() {
        if (index == stringList.size() - 1) {
            log("结束======");
            if(resultViewListener!=null){
                resultViewListener.animationEnd();
            }
            return;
        }
        rootView.scrollTo(0, 0);
        index++;
        log("index====" + index);
        if (index >= 1 && index <= stringList.size() - 1) {
            tv1.setText(stringList.get(index - 1));
            tv2.setText(stringList.get(index));
            if (index + 1 < stringList.size()) {
                tv3.setText(stringList.get(index + 1));
            } else {
                linearLayout3.setVisibility(GONE);
            }

        }
        iv1.setImageResource(R.mipmap.fragment_main_head_style1_check_img);
        iv2.setImageResource(R.mipmap.fragment_main_head_style1_loading_img);
        iv3.setImageResource(R.mipmap.fragment_main_head_style1_loading_img);
        rotateAnimation();
    }


    public int dp2Px(int dp) {
        Context context = getContext();
        if (context == null) {
            return 0;
        }
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, dm);
        return px;
    }

    public int px2dp(Context context, float pxValue) {
        if (getContext() == null) {
            return 0;
        }
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public void log(String s) {
        Log.d("CustomScollerView", s);
    }

    public interface ResultViewListener {
        void animationEnd();
    }

    ResultViewListener resultViewListener;

    public void setResultViewListener(ResultViewListener resultViewListener) {
        this.resultViewListener = resultViewListener;
    }
}
