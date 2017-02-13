package com.xiayu.stethodemo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class MainActivity extends Activity {

    private SharedPreferences mSp;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView)findViewById(R.id.tv);
    }

    public void net(View v) {
        OkHttpUtils.get()
                .url("https://api.douban.com/v2/movie/top250")
                .addParams("start","0")
                .addParams("count","10")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                System.out.println("请求失败");
            }

            @Override
            public void onResponse(String response, int id) {
                tv.setText(response);
                System.out.println("请求成功");
            }
        });

    }
    public void sp(View v) {
        mSp = getSharedPreferences("config", 0);
        SharedPreferences.Editor edit = mSp.edit();
        edit.putString("name", "xiayu");
        edit.putBoolean("handsome?", true);
        edit.commit();
    }
}
