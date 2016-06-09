package com.zheng0716.ustbnews.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.zheng0716.ustbnews.R;
import com.zheng0716.ustbnews.data.DataUtil;
import org.json.JSONObject;

public class GuideActivity extends AppCompatActivity {
    private Runnable jumpRunnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        final Runnable jumpControlRunnable = new Runnable() {
            @Override
            public synchronized void run() {
                if (jumpRunnable == null) jumpRunnable = new Runnable() {
                    @Override
                    public void run() {
                        ActivityManager.jump(GuideActivity.this, MainActivity.class);
                    }
                };
                else jumpRunnable.run();
            }
        };
        new Handler().postDelayed(jumpControlRunnable, 1500);
        DataUtil.INSTANCE.getData(GuideActivity.this, "home", new DataUtil.DataHandler() {
            @Override
            public void handleData(JSONObject data) {
                jumpControlRunnable.run();
            }
        });
    }
}
