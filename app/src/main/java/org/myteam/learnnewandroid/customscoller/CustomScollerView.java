package org.myteam.learnnewandroid.customscoller;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.myteam.learnnewandroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhumingwei on 16/11/4.
 */
public class CustomScollerView extends RelativeLayout implements InnerListener {
    List<ViewHolder> vList = new ArrayList<ViewHolder>();

    public CustomScollerView(Context context) {
        super(context);
        init(null, 0);
    }

    public CustomScollerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CustomScollerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.custom_scoller_layout, this);
        initList();
    }

    private void initList() {

        ViewHolder viewHolder1 = new ViewHolder();
        viewHolder1.linearLayout = (LinearLayout) findViewById(R.id.layout1);
        viewHolder1.imageView = (ImageView) findViewById(R.id.imageview1);
        viewHolder1.textView = (TextView) findViewById(R.id.textview1);
        vList.add(viewHolder1);

        ViewHolder viewHolder2 = new ViewHolder();
        viewHolder2.linearLayout = (LinearLayout) findViewById(R.id.layout2);
        viewHolder2.imageView = (ImageView) findViewById(R.id.imageview2);
        viewHolder2.textView = (TextView) findViewById(R.id.textview2);
        vList.add(viewHolder2);

        ViewHolder viewHolder3 = new ViewHolder();
        viewHolder3.linearLayout = (LinearLayout) findViewById(R.id.layout3);
        viewHolder3.imageView = (ImageView) findViewById(R.id.imageview3);
        viewHolder3.textView = (TextView) findViewById(R.id.textview3);
        vList.add(viewHolder3);
    }

    public void startAnimation() {

    }


    //执行一个平移动画,执行完成后返回初始状态
    private void startScoller() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, dp2Px(getContext(), 20)).setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            int currentValue = 0;

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) valueAnimator.getAnimatedValue();
                if (value != currentValue) {
                    currentValue = value;
                    scrollTo(0, value);
                }

            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                scrollTo(0,0);
            }
        });
        valueAnimator.start();
    }


    @Override
    public void scollerEnd() {

    }

    public int dp2Px(Context context, int dp) {
        if (context == null) {
            return 0;
        }
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, dm);
        return px;
    }

    public int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}

interface InnerListener {
    void scollerEnd();
}

class ViewHolder {
    LinearLayout linearLayout;
    ImageView imageView;
    TextView textView;
}
