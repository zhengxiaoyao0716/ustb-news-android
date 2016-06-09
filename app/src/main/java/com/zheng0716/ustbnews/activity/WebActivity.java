package com.zheng0716.ustbnews.activity;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.zheng0716.ustbnews.R;

public class WebActivity extends AppCompatActivity {
    private WebView webview;
    private SwipeRefreshLayout refreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        webview = (WebView) findViewById(R.id.webView);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);

        webview.loadUrl(getIntent().getStringExtra("url"));
        initWebView();
    }

    private void initWebView() {
        //支持javascript
        webview.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        webview.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        webview.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        webview.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webview.getSettings().setLoadWithOverviewMode(true);

        //取消滚动条
        webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        //触摸焦点起作用
        webview.requestFocus();
        //点击链接继续在当前browser中响应
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        //设置进度条
        webview.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    //隐藏进度条
                    refreshLayout.setRefreshing(false);
                } else {
                    if (!refreshLayout.isRefreshing())
                        refreshLayout.setRefreshing(true);
                }

                super.onProgressChanged(view, newProgress);
            }
        });

        refreshLayout.setProgressBackgroundColorSchemeResource(R.color.primary_light);
        refreshLayout.setColorSchemeColors(Color.WHITE);
        SwipeRefreshLayout.OnRefreshListener listener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webview.loadUrl(webview.getUrl());
                refreshLayout.setRefreshing(false);
            }
        };
    }
}
