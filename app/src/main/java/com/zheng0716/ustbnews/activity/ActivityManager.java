package com.zheng0716.ustbnews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * 界面管理.
 * Created by zhengxiaoyao0716 on 2016/2/28.
 */
public class ActivityManager {
    public static void jump(AppCompatActivity activity, Class<? extends AppCompatActivity> activityClass) { jump(activity, activityClass, true); }
    public static void jump(AppCompatActivity activity, Class<? extends AppCompatActivity> activityClass, boolean finishNow)
    {
        activity.startActivity(new Intent(activity, activityClass));
        if (finishNow) activity.finish();
    }
    public static void jumpWithParam(AppCompatActivity activity, Class<? extends AppCompatActivity> activityClass, Bundle extras)
    {
        Intent intent = new Intent(activity, activityClass);
        intent.putExtras(extras);
        activity.startActivity(intent);
    }
}
