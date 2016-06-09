package com.zheng0716.ustbnews.View;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.zheng0716.ustbnews.R;

/**
 * 包装一下SnackBar.
 * Created by zhengxiaoyao0716 on 2016/4/10.
 */
public class SnackBar extends com.gc.materialdesign.widgets.SnackBar {
    private AppCompatActivity activity;
    public SnackBar(AppCompatActivity activity, String text, String buttonText, View.OnClickListener onClickListener) {
        super(activity, text, buttonText, onClickListener);
        this.activity = activity;
    }

    @Override
    public void show() {
        if (Build.VERSION.SDK_INT > 23) {
            this.setBackgroundSnackBar(activity.getResources().getColor(R.color.primary_dark, null));
            this.setColorButton(activity.getResources().getColor(R.color.accent, null));
        }
        else {
            this.setBackgroundSnackBar(activity.getResources().getColor(R.color.primary_dark));
            this.setColorButton(activity.getResources().getColor(R.color.accent));
        }
        super.show();
    }
}
