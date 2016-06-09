package com.zheng0716.ustbnews.data;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.zheng0716.ustbnews.View.SnackBar;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;

/**
 * 数据工具类.
 * Created by zhengxiaoyao0716 on 2016/4/10.
 */
public enum DataUtil {
    INSTANCE;

    public static final String host = "http://ustbnews.zheng0716.com";
    public static boolean checkNet(Context context)
    {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) return false;

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public interface DataHandler {
        /**
         * 处理回调数据.
         * @param data 数据
         */
        void handleData(JSONObject data);
    }
    private String flag;
    public void getData(final AppCompatActivity activity, final String page, final DataHandler handler) {
        if (!checkNet(activity)) {
            SnackBar snackBar = new SnackBar(activity, "无网络连接", "OK", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            snackBar.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    handler.handleData(getDataFromCache(activity, page));
                }
            });
            snackBar.show();
            return;
        }

        OkHttpClient client = new OkHttpClient();
        flag = activity.getSharedPreferences("config", MODE_PRIVATE).getString(page + "Flag", "");
        Request request = new Request.Builder()
                .url(host + "/data/" + page + "?flag=" + flag)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                new SnackBar(activity, "获取数据失败", "OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO: 2016/4/10 收集发送错误日志
                    }
                }).show();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        handler.handleData(null);
                    }
                });
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                JSONObject respObject;
                try {
                    respObject = new JSONObject(response.body().string());
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }
                if (!respObject.optBoolean("success", false)) {
                    new SnackBar(activity, respObject.optString("message"), "OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    }).show();
                    return;
                }
                JSONObject content = respObject.optJSONObject("content");
                String flag = content.optString("flag");
                if (flag.equals(DataUtil.this.flag))
                {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            handler.handleData(getDataFromCache(activity, page));
                        }
                    });
                    return;
                }
                DataUtil.this.flag = flag;
                final JSONObject data = content.optJSONObject("data");
                cache(activity, page, data);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        handler.handleData(data);
                    }
                });
            }
        });
    }

    private JSONObject getDataFromCache(Context context, String page) {
        String cacheDataStr = context.getSharedPreferences("config", MODE_PRIVATE).getString(page, null);
        if (cacheDataStr == null) return null;

        JSONObject data;
        try {
            data = new JSONObject(cacheDataStr);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return data;
    }
    private void cache(Context context, String page, JSONObject data)
    {
        context.getSharedPreferences("config", MODE_PRIVATE).edit()
                .putString(page + "Flag", flag)
                .putString(page, data.toString()).apply();
    }
}
