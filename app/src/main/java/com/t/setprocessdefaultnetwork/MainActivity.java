package com.t.setprocessdefaultnetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_test;
    private Button btn_wifi;
    private Button btn_ethernet;
    private Button btn_cellular;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //NetWorkUtil.getNetState(MainActivity.this, NetWorkUtil.NET_WIFI);
        btn_test = (Button) findViewById(R.id.btn_test);
        btn_test.setOnClickListener(this);
        btn_wifi = (Button) findViewById(R.id.btn_wifi);
        btn_wifi.setOnClickListener(this);
        btn_ethernet = (Button) findViewById(R.id.btn_ethernet);
        btn_ethernet.setOnClickListener(this);
        btn_cellular = (Button) findViewById(R.id.btn_cellular);
        btn_cellular.setOnClickListener(this);
        updateView();
    }

    private void updateView() {
        if (NetWorkUtil.CURRENT_TAG == NetWorkUtil.NET_ETHERNET) {
            btn_test.setText("test on Ethernet");
        } else if (NetWorkUtil.CURRENT_TAG == NetWorkUtil.NET_CELLULAR) {
            btn_test.setText("test on Cellular");
        } else if (NetWorkUtil.CURRENT_TAG == NetWorkUtil.NET_WIFI) {
            btn_test.setText("test on Wi-Fi");
        } else {
            btn_test.setText("test on System");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_wifi:
                NetWorkUtil.getNetState(MainActivity.this, NetWorkUtil.NET_WIFI);
                updateView();
                break;

            case R.id.btn_ethernet:
                NetWorkUtil.getNetState(MainActivity.this, NetWorkUtil.NET_ETHERNET);
                updateView();
                break;

            case R.id.btn_cellular:
                NetWorkUtil.getNetState(MainActivity.this, NetWorkUtil.NET_CELLULAR);
                updateView();
                break;

            case R.id.btn_test:
                NetWorkUtil.get("https://www.baidu.com");
                break;

            default:
                break;
        }
    }
}