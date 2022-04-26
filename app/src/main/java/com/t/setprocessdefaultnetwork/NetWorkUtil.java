package com.t.setprocessdefaultnetwork;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class NetWorkUtil {
    private static final String TAG = "NetWorkUtil";
    public static final int NET_ETHERNET = 0;
    public static final int NET_CELLULAR = 1;
    public static final int NET_WIFI = 2;
    public static int CURRENT_TAG = -1;

    public static void getNetState(Context context, int tag) {
        CURRENT_TAG = tag;
        Log.e(TAG, "getNetState: " + tag);
        final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkRequest.Builder builder = new NetworkRequest.Builder();
        builder.addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
//            builder.addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET);
//            builder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI);
        if (tag == NET_ETHERNET) {
            builder.addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET);
        } else if (tag == NET_CELLULAR) {
            builder.addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR);
        } else if (tag == NET_WIFI) {
            builder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI);
        }
        NetworkRequest request = builder.build();
        ConnectivityManager.NetworkCallback callback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                super.onAvailable(network);
                Log.e(TAG, "onAvailable");
                connectivityManager.bindProcessToNetwork(network);
                connectivityManager.unregisterNetworkCallback(this);
            }
        };
        connectivityManager.requestNetwork(request, callback);
    }
    
    public static String get(final String url) {
        Log.e(TAG, "=========get==========");
        final StringBuilder sb = new StringBuilder();
        FutureTask<String> task = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                BufferedReader br = null;
                InputStreamReader isr = null;
                URLConnection conn;
                try {
                    URL geturl = new URL(url);
                    conn = geturl.openConnection();//创建连接
                    conn.connect();//get连接
                    isr = new InputStreamReader(conn.getInputStream());//输入流
                    br = new BufferedReader(isr);
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        sb.append(line);//获取输入流数据
                    }
                    Log.e(TAG, "==" + sb.toString());
                } catch (Exception e) {
                    Log.e(TAG, "=====6666666==============");
                    e.printStackTrace();
                } finally {//执行流的关闭
                    if (br != null) {
                        try {
                            if (br != null) {
                                br.close();
                            }
                            if (isr != null) {
                                isr.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return sb.toString();
            }
        });
        new Thread(task).start();
        String s = null;
        try {
            s = task.get();//异步获取返回值
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }
}
