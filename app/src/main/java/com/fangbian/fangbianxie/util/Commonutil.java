package com.fangbian.fangbianxie.util;

import android.content.Context;

/**
 * Created by sunxipeng on 2016/10/12.
 */
public class Commonutil {
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
