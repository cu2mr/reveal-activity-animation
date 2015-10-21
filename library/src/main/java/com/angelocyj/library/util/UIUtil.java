package com.angelocyj.library.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import java.lang.reflect.Method;

/**
 * 类描述：
 * 创建人：angelo
 * 创建时间：10/16/15 3:14 PM
 */
public class UIUtil {

    public static void showChildrenView(final ViewGroup parent) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View childAt = parent.getChildAt(i);
            if (childAt.getVisibility() != View.VISIBLE) {
                childAt.setVisibility(View.VISIBLE);
            }
        }
    }

    public static void invisibleChildrenView(ViewGroup parent) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View childAt = parent.getChildAt(i);
            if (childAt.getVisibility() != View.INVISIBLE) {
                childAt.setVisibility(View.INVISIBLE);
            }
        }
    }



}
