package org.myteam.learnnewandroid.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import org.myteam.learnnewandroid.R;


/**
 * Created by zhumingwei on 16/7/29.
 * 时间控制控件
 */
public class TimeIncreaseView extends View {

    private Paint paintL;
    private Paint paintM;
    private int largeColor;
    private int smallColor;
    private int hours;
    private int min;
    private int largeTextSize;
    private int smallTextSize;
    private int wordspacing;
    private float minimumWidth;
    private float largeWidth, minWidth, hourWidth;
    ;
    boolean huddle, needHour, needMin;

    private final int TEXT_STYLE_NORMAL = 0;
    private final int TEXT_STYLE_LOWERCASE = 1;
    private final int TEXT_STYLE_CAPITAL = 2;

    private int text_style = TEXT_STYLE_CAPITAL;
    private boolean ltr = true;

    public TimeIncreaseView(Context context) {
        super(context);
        initView(null, 0);
    }

    public TimeIncreaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs, 0);
    }

    public TimeIncreaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs, defStyleAttr);
    }

    private void initView(AttributeSet attrs, int defStyle) {
        paintL = new Paint();
        paintL.setTypeface(Typeface.DEFAULT_BOLD);
        paintM = new Paint();
        paintL.setAntiAlias(true);
        paintM.setAntiAlias(true);
        paintM.setTextAlign(Paint.Align.LEFT);
        paintL.setTextAlign(Paint.Align.LEFT);
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.TimeIncreaseView, defStyle, 0);
        largeColor = a.getColor(R.styleable.TimeIncreaseView_largeColor, Color.parseColor("#000"));
        smallColor = a.getColor(R.styleable.TimeIncreaseView_smallColor, Color.parseColor("#000"));
        largeTextSize = a.getDimensionPixelSize(R.styleable.TimeIncreaseView_largeSize, DisplayUtils.dp2Px(getContext(), 30));
        wordspacing = a.getDimensionPixelSize(R.styleable.TimeIncreaseView_wordspacing, 0);
        smallTextSize = a.getDimensionPixelSize(R.styleable.TimeIncreaseView_smallSize, DisplayUtils.dp2Px(getContext(), 5));
        needMin = a.getBoolean(R.styleable.TimeIncreaseView_needMin, true);
        setTextStyle(a.getString(R.styleable.TimeIncreaseView_textstyle));
        huddle = a.getBoolean(R.styleable.TimeIncreaseView_huddle, false);
        needHour = a.getBoolean(R.styleable.TimeIncreaseView_needHour, true);
        setHours(a.getInteger(R.styleable.TimeIncreaseView_hour, 0));
        setMin(a.getInteger(R.styleable.TimeIncreaseView_min, 0));
        a.recycle();
        paintL.setColor(largeColor);
        paintM.setColor(smallColor);
        paintL.setTextSize(largeTextSize);
        paintM.setTextSize(smallTextSize);

        largeWidth = paintL.measureText("0");
        paintL.setShadowLayer(3, 0, 2, Color.parseColor("#cc141620"));
        paintM.setShadowLayer(3, 0, 2, Color.parseColor("#cc141620"));
        setMinWidth();
        setMinimum();
        log("ltr ====" + ltr);
    }

    private void setMinimum() {
        minimumWidth = paintL.measureText("0000") + paintM.measureText("HMIN");
        setMinimumWidth((int) minimumWidth);
    }


    private void setMinWidth() {
        switch (text_style) {
            case TEXT_STYLE_NORMAL:
                minWidth = paintM.measureText("Min");
                hourWidth = paintM.measureText("H");
                break;
            case TEXT_STYLE_LOWERCASE:
                minWidth = paintM.measureText("min");
                hourWidth = paintM.measureText("h");
                break;
            case TEXT_STYLE_CAPITAL:
            default:
                minWidth = paintM.measureText("MIN");
                hourWidth = paintM.measureText("H");
                break;
        }


    }

    private void log(String s) {
        Log.d("TimeIncreaseView", s);
    }

    private void setTextStyle(String textStyle) {
        if (textStyle == null || textStyle.equals("")) {
            text_style = TEXT_STYLE_CAPITAL;
        } else if (textStyle.equals("capital")) {
            text_style = TEXT_STYLE_CAPITAL;
        } else if (textStyle.equals("lowercase")) {
            text_style = TEXT_STYLE_LOWERCASE;
        } else if (textStyle.equals("normal")) {
            text_style = TEXT_STYLE_NORMAL;
        }
    }

    public void setHours(int hours) {
        if (hours < 0 || hours >= 100) {
            return;
        }
        this.hours = hours;
        invalidate();
    }

    public void setMin(int min) {
        if (min < 0 || min >= 60) {
            return;
        }
        this.min = min;
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (huddle) {
            if (ltr) {
                drawHuddleText(canvas);
            } else {
                drawHuddleTextRTL(canvas);
            }
        } else {
            if (ltr) {
                drawDefaultText(canvas);
            } else {
                drawDefaultTextRTL(canvas);
            }
        }


    }

    private void drawHuddleTextRTL(Canvas canvas) {
        log("drawHuddleTextRTL");
        float distanceX = 0;

        if (min != 0 || needMin) {
            drawMinDesc(canvas, distanceX, getHeight() / 2 + largeWidth / 2, paintM);
            distanceX = distanceX + minWidth + wordspacing;
            if (min >= 10) {
                canvas.drawText(min + "", distanceX, getHeight() / 2 + largeWidth / 2, paintL);
                distanceX = distanceX + largeWidth * 2 + wordspacing;
            } else {
                canvas.drawText(min + "", distanceX, getHeight() / 2 + largeWidth / 2, paintL);
                distanceX = distanceX + largeWidth + wordspacing;
            }

        }

        if (hours != 0 || needHour) {
            drawHDesc(canvas, distanceX, getHeight() / 2 + largeWidth / 2, paintM);
            distanceX = distanceX + hourWidth + wordspacing;
            if (hours >= 10) {
                canvas.drawText(hours + "", distanceX, getHeight() / 2 + largeWidth / 2, paintL);
            } else {
                canvas.drawText(hours + "", distanceX, getHeight() / 2 + largeWidth / 2, paintL);
            }
        }
        canvas.save();
    }


    private void drawHuddleText(Canvas canvas) {
        log("drawHuddleText");
        float distanceX = 0;
        if (hours != 0 || needHour) {
            if (hours >= 10) {
                canvas.drawText(hours + "", distanceX, getHeight() / 2 + largeWidth / 2, paintL);
                distanceX += largeWidth * 2 + wordspacing;
            } else {
                canvas.drawText(hours + "", distanceX, getHeight() / 2 + largeWidth / 2, paintL);
                distanceX += largeWidth + wordspacing;
            }
            drawHDesc(canvas, distanceX, getHeight() / 2 + largeWidth / 2, paintM);
            distanceX += hourWidth + wordspacing;
        }
        if (min != 0 || needMin) {
            if (min >= 10) {
                canvas.drawText(min + "", distanceX, getHeight() / 2 + largeWidth / 2, paintL);
                distanceX += largeWidth * 2 + wordspacing;
            } else {
                canvas.drawText(min + "", distanceX, getHeight() / 2 + largeWidth / 2, paintL);
                distanceX += largeWidth + wordspacing;
            }
            drawMinDesc(canvas, distanceX, getHeight() / 2 + largeWidth / 2, paintM);
        }
        canvas.save();
    }

    private void drawDefaultText(Canvas canvas) {
        log("drawDefaultText");
        float distanceX = 0;
        if (hours != 0 || needHour) {
            if (hours >= 10) {
                canvas.drawText(hours + "", distanceX, getHeight() / 2 + largeWidth / 2, paintL);
            } else {
                canvas.drawText(hours + "", distanceX + largeWidth, getHeight() / 2 + largeWidth / 2, paintL);
            }
            distanceX = 2 * largeWidth + wordspacing;
            drawHDesc(canvas, distanceX, getHeight() / 2 + largeWidth / 2, paintM);
            distanceX = distanceX + hourWidth + wordspacing;
        }
        if (min != 0 || needMin) {
            if (min >= 10) {
                canvas.drawText(min + "", distanceX, getHeight() / 2 + largeWidth / 2, paintL);
            } else {
                canvas.drawText(min + "", distanceX + largeWidth * 1, getHeight() / 2 + largeWidth / 2, paintL);
            }
            distanceX = distanceX + largeWidth * 2 + wordspacing;
            drawMinDesc(canvas, distanceX, getHeight() / 2 + largeWidth / 2, paintM);
        }
        canvas.save();
    }

    private void drawDefaultTextRTL(Canvas canvas) {
        log("drawDefaultTextRTL");
        float distanceX = 0;


        if (min != 0 || needMin) {
            drawMinDesc(canvas, distanceX, getHeight() / 2 + largeWidth / 2, paintM);
            distanceX = distanceX + minWidth + wordspacing;
            if (min >= 10) {
                canvas.drawText(min + "", distanceX, getHeight() / 2 + largeWidth / 2, paintL);
            } else {
                canvas.drawText(min + "", distanceX + largeWidth * 1, getHeight() / 2 + largeWidth / 2, paintL);
            }
            distanceX = distanceX + largeWidth * 2 + wordspacing;

        }
        if (hours != 0 || needHour) {
            drawHDesc(canvas, distanceX, getHeight() / 2 + largeWidth / 2, paintM);
            distanceX = distanceX + hourWidth + wordspacing;

            if (hours >= 10) {
                canvas.drawText(hours + "", distanceX, getHeight() / 2 + largeWidth / 2, paintL);
            } else {
                canvas.drawText(hours + "", distanceX + largeWidth, getHeight() / 2 + largeWidth / 2, paintL);
            }
            distanceX = distanceX + 2 * largeWidth + wordspacing;

        }
        canvas.save();
    }

    private void setBigAlpha(float a) {
        int alpha = (int) (a * 255);
        if (alpha < 0) {
            alpha = 0;
        } else if (alpha > 255) {
            alpha = 255;
        }
        paintL.setAlpha(alpha);
    }

    private void setSmallAlpha(float a) {
        int alpha = (int) (a * 255);
        if (alpha < 0) {
            alpha = 0;
        } else if (alpha > 255) {
            alpha = 255;
        }
        paintM.setAlpha(alpha);
    }

    ValueAnimator va;

    public void setTime2(final int hours, final int min, long duration) {
        va = ValueAnimator.ofInt(0, hours * 60 + min);
        va.setDuration(duration);

        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            long lastUpdateTime = 0;

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int aValue = (int) animation.getAnimatedValue();
                if (System.currentTimeMillis() - 40 > lastUpdateTime) {
                    lastUpdateTime = System.currentTimeMillis();
                    setHours(aValue / 60);
                    setMin(aValue % 60);
                }
            }
        });
        va.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                setHours(hours);
                setMin(min);
            }
        });
        va.setInterpolator(new AccelerateDecelerateInterpolator());
        va.start();
    }

    //同时控制大字的透明度
    public void setTime2WithBigAlpha(final int hours, final int min, long duration) {
        va = ValueAnimator.ofInt(0, hours * 60 + min);
        va.setDuration(duration);

        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            long lastUpdateTime = 0;

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int aValue = (int) animation.getAnimatedValue();
                if (System.currentTimeMillis() - 40 > lastUpdateTime) {
                    lastUpdateTime = System.currentTimeMillis();
                    setHours(aValue / 60);
                    setMin(aValue % 60);
                }
            }
        });
        va.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                setHours(hours);
                setMin(min);
            }
        });
        va.setInterpolator(new AccelerateDecelerateInterpolator());
        va.start();

        ObjectAnimator v1 = ObjectAnimator.ofFloat(this, "bigAlpha", 0.4f, 1).setDuration(duration);
        v1.start();

    }


    public boolean isAnimation() {
        return va != null && va.isRunning();
    }

    public void setTime(int hours, int min, long duration) {

        va = ValueAnimator.ofInt(0, hours);
        va.setDuration(duration);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int aValue = (int) animation.getAnimatedValue();
                setHours(aValue);
            }
        });
        va.setInterpolator(new AccelerateDecelerateInterpolator());
        va.start();

        final ValueAnimator vb = ValueAnimator.ofInt(0, min);
        vb.setDuration(duration);
        vb.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int aValue = (int) animation.getAnimatedValue();
                setMin(aValue);
            }
        });
        vb.setInterpolator(new AccelerateDecelerateInterpolator());
        vb.start();

    }

    public void setIncreaseTime(int basehours, int basemin, int hours, int min, long duration) {
//        if (basehours > hours) {
//            hours = hours + 24;
//        }
        if (basemin > min) {
            min = min + 60;
        }
        va = ValueAnimator.ofInt(basehours, hours);
        va.setDuration(duration);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int aValue = (int) animation.getAnimatedValue();
                setHours(aValue);
            }
        });
        va.setInterpolator(new AccelerateDecelerateInterpolator());
        va.start();


        final ValueAnimator vb = ValueAnimator.ofInt(basemin, min);
        vb.setDuration(duration);
        vb.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int aValue = (int) animation.getAnimatedValue();
                if (aValue > 60) {
                    aValue = aValue - 60;
                }
                setMin(aValue);
            }
        });
        vb.setInterpolator(new AccelerateDecelerateInterpolator());
        vb.start();

    }

    public void setIncreaseTimeDelay(int basehours, int basemin, int hours, int min, long duration, long delay) {

        va = ValueAnimator.ofInt(basehours, hours);
        va.setDuration(duration);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int aValue = (int) animation.getAnimatedValue();
                setHours(aValue);
            }
        });
        va.setInterpolator(new AccelerateDecelerateInterpolator());
        va.setStartDelay(delay);
        va.start();


        final ValueAnimator vb = ValueAnimator.ofInt(basemin, min);
        vb.setDuration(duration);
        vb.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int aValue = (int) animation.getAnimatedValue();
                setMin(aValue);
            }
        });
        vb.setInterpolator(new AccelerateDecelerateInterpolator());
        vb.setStartDelay(delay);
        vb.start();

    }

    private void drawHDesc(Canvas canvas, float x, float y, Paint paint) {
        switch (text_style) {
            case TEXT_STYLE_NORMAL:
                canvas.drawText("H", x, y, paint);
                break;
            case TEXT_STYLE_LOWERCASE:
                canvas.drawText("h", x, y, paint);
                break;
            case TEXT_STYLE_CAPITAL:
                canvas.drawText("H", x, y, paint);
                break;
        }
    }

    private void drawMinDesc(Canvas canvas, float x, float y, Paint paint) {
        switch (text_style) {
            case TEXT_STYLE_NORMAL:
                canvas.drawText("Min", x, y, paint);
                break;
            case TEXT_STYLE_LOWERCASE:
                canvas.drawText("min", x, y, paint);
                break;
            case TEXT_STYLE_CAPITAL:
                canvas.drawText("MIN", x, y, paint);
                break;
        }
    }
}
