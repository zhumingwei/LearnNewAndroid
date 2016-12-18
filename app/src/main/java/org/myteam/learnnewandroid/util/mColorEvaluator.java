package org.myteam.learnnewandroid.util;

import android.animation.TypeEvaluator;
import android.graphics.Color;

/**
 * Created by zhumingwei on 16/10/20.
 * 控制颜色变化
 */
public class mColorEvaluator implements TypeEvaluator {
    private int mCurrentRed = -1;

    private int mCurrentGreen = -1;

    private int mCurrentBlue = -1;

    private int mCurrentAlpha = -1;

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        String startColor;
        String endColor;
        if(startValue instanceof Integer && endValue instanceof Integer){
             startColor = Integer.toHexString((Integer) startValue);
             endColor = Integer.toHexString((Integer) endValue);
        }else{
             startColor = ((String) startValue);
             endColor = (String) endValue;
        }
        int startRed = Integer.parseInt(startColor.substring(startColor.length()-6, startColor.length()-4), 16);
        int startGreen = Integer.parseInt(startColor.substring(startColor.length()-4, startColor.length()-2), 16);
        int startBlue = Integer.parseInt(startColor.substring(startColor.length()-2, startColor.length()), 16);
        int endRed = Integer.parseInt(endColor.substring(startColor.length()-6, startColor.length()-4), 16);
        int endGreen = Integer.parseInt(endColor.substring(startColor.length()-4, startColor.length()-2), 16);
        int endBlue = Integer.parseInt(endColor.substring(startColor.length()-2, startColor.length()), 16);
        int startAlpha;
        try {
            startAlpha = Integer.parseInt(startColor.substring(startColor.length() - 8, startColor.length() - 6), 16);
        }catch (Exception ex){
            startAlpha = 255;
        }

        int endAlpha ;
        try {
            endAlpha = Integer.parseInt(endColor.substring(startColor.length() - 6, startColor.length() - 4), 16);
        }catch (Exception ex){
            endAlpha = 255;
        }

        // 初始化颜色的值
        if (mCurrentRed == -1) {
            mCurrentRed = startRed;
        }
        if (mCurrentGreen == -1) {
            mCurrentGreen = startGreen;
        }
        if (mCurrentBlue == -1) {
            mCurrentBlue = startBlue;
        }
        if (mCurrentAlpha == -1) {
            mCurrentAlpha = startAlpha;
        }
        // 计算初始颜色和结束颜色之间的差值
        int redDiff = Math.abs(startRed - endRed);
        int greenDiff = Math.abs(startGreen - endGreen);
        int blueDiff = Math.abs(startBlue - endBlue);
        int alphaDiff = Math.abs(startAlpha - endAlpha);
        int colorDiff = alphaDiff + redDiff + greenDiff + blueDiff;
        if (mCurrentRed != endRed) {
            mCurrentRed = getCurrentColor(startRed, endRed, colorDiff, 0,
                    fraction);
        }
        if (mCurrentGreen != endGreen) {
            mCurrentGreen = getCurrentColor(startGreen, endGreen, colorDiff,
                    0, fraction);
        }
        if (mCurrentBlue != endBlue) {
            mCurrentBlue = getCurrentColor(startBlue, endBlue, colorDiff,
                    0 , fraction);
        }
        if(mCurrentAlpha != endAlpha){
            mCurrentAlpha = getCurrentColor(startAlpha, endAlpha, colorDiff,
                    0, fraction);
        }
        // 将计算出的当前颜色的值组装返回
        String currentColor = "#" + getHexString(mCurrentRed)
                + getHexString(mCurrentGreen) + getHexString(mCurrentBlue);
        return Color.parseColor(currentColor);
    }

    private int getCurrentColor(int startColor, int endColor, int colorDiff,
                                int offset, float fraction) {
        int currentColor;
        if (startColor > endColor) {
            currentColor = (int) (startColor - (fraction * colorDiff - offset));
            if (currentColor < endColor) {
                currentColor = endColor;
            }
        } else {
            currentColor = (int) (startColor + (fraction * colorDiff - offset));
            if (currentColor > endColor) {
                currentColor = endColor;
            }
        }
        return currentColor;
    }

    /**
     * 将10进制颜色值转换成16进制。
     */
    private String getHexString(int value) {
        String hexString = Integer.toHexString(value);
        if (hexString.length() == 1) {
            hexString = "0" + hexString;
        }
        return hexString;
    }

}
