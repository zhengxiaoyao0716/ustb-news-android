package com.zheng0716.ustbnews;

import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GuideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences("config", MODE_PRIVATE);
                String homeFlag = preferences.getString("homeFlag", null);

                //ActivityManage.jumpNew(GuideActivity.this, );
            }
        }, 1500);
    }
}
