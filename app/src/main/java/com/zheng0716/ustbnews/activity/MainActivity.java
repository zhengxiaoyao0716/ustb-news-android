package com.zheng0716.ustbnews.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import com.zheng0716.ustbnews.View.SnackBar;
import com.zheng0716.ustbnews.fragment.FragmentManager;
import com.zheng0716.ustbnews.fragment.HomeFragment;
import com.zheng0716.ustbnews.R;
import com.zheng0716.ustbnews.fragment.NewFragment;

public class MainActivity extends AppCompatActivity implements FragmentManager.OnFragmentInteractionListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initButtons();
        FragmentManager.replace(this, R.id.fragment, new HomeFragment());
    }

    /** 配置按钮栏动画 */
    private LinearLayout buttonBar;
    private Animation showAnimation;
    private Animation hideAnimation;
    private Handler handler = new Handler();
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            buttonBar.startAnimation(hideAnimation);
            buttonBar.setVisibility(View.GONE);
            handler = null;
        }
    };
    private void initButtons() {
        buttonBar = (LinearLayout) findViewById(R.id.buttonBar);
        showAnimation = AnimationUtils.loadAnimation(this, R.anim.show);
        hideAnimation = AnimationUtils.loadAnimation(this, R.anim.hide);
        handler.postDelayed(runnable, 3000);
    }
    private void arouseButtons() {
        if (handler == null)
        {
            handler = new Handler();
            buttonBar.startAnimation(showAnimation);
            buttonBar.setVisibility(View.VISIBLE);
        }
        else handler.removeCallbacks(runnable);

        handler.postDelayed(runnable, 3000);
    }
    public void onButtonsClick(View view) {
        arouseButtons();
        switch (view.getId()) {
            case R.id.newButton:
                new SnackBar(MainActivity.this, "Sorry, this just a demo.", "ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO: 2016/4/11 动态
                        FragmentManager.replace(MainActivity.this, R.id.fragment, new NewFragment());
                    }
                }).show();
                break;
            case R.id.homeButton:
                FragmentManager.replace(this, R.id.fragment, new HomeFragment());
                break;
            case R.id.studyButton:
                new SnackBar(MainActivity.this, "Sorry, this just a demo.", "ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO: 2016/4/11 留学
                        FragmentManager.replace(MainActivity.this, R.id.fragment, new NewFragment());
                    }
                }).show();
                break;
        }
    }
    @Override
    public void onFragmentInteraction() {
        arouseButtons();
    }

    @Override
    public AppCompatActivity getActivity() {
        return this;
    }
}
