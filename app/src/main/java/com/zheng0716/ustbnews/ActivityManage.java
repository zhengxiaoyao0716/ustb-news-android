package com.zheng0716.ustbnews;

import android.app.Activity;
import android.content.Intent;

/**
 * 界面管理.
 * Created by zhengxiaoyao0716 on 2016/2/28.
 */
class ActivityManage {
    static void jumpNew(Activity activity, Class<?> activityClass) { jumpNew(activity, activityClass, true); }
    private static void jumpNew(Activity activity, Class<?> activityClass, boolean finishNow)
    {
        activity.startActivity(new Intent(activity, activityClass));
        if (finishNow) activity.finish();
    }
}
