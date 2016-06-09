package com.zheng0716.ustbnews.View;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.gc.materialdesign.views.LayoutRipple;
import com.zheng0716.ustbnews.R;
import com.zheng0716.ustbnews.activity.ActivityManager;
import com.zheng0716.ustbnews.activity.WebActivity;
import com.zheng0716.ustbnews.data.DataUtil;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 新闻/公告记录列表.
 * Created by zhengxiaoyao0716 on 2016/4/10.
 */
public class RecordList implements View.OnClickListener {
    private AppCompatActivity activity;
    public RecordList(View parent, @IdRes int id, JSONArray records, LayoutInflater inflater, ViewGroup container, AppCompatActivity activity)
    {
        LinearLayout list = (LinearLayout) parent.findViewById(id);
        list.removeAllViews();
        
        for (int index = 0; index < records.length(); index++)
        {
            JSONObject record = records.optJSONObject(index);
            
            LayoutRipple item = (LayoutRipple) inflater.inflate(R.layout.item_record, container, false);
            ((TextView) item.findViewById(R.id.text)).setText(record.optString("text"));
            ((TextView) item.findViewById(R.id.date)).setText(record.optString("date"));
            item.setTag(record.optString("href"));
            item.setOnClickListener(this);

            list.addView(item);
        }
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putString("url", DataUtil.host + "/view" + v.getTag());
        ActivityManager.jumpWithParam(activity, WebActivity.class, bundle);
    }
}
