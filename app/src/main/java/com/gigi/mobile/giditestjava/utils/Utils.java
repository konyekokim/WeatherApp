package com.gigi.mobile.giditestjava.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Utils {

    private static Toast mToast;

    public static void showToast(Context context, String message){
        if(mToast != null)  mToast.cancel();
        mToast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        mToast.show();
    }

    public static Boolean checkConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        return nInfo != null && nInfo.isConnected();
    }

    public static String getDateString(long date) {
        return new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                .format(new Date(date * 1000L));
    }

    public static String getTimeString(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date newDate = dateFormat.parse(date);
            dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            dateFormat.setTimeZone(TimeZone.getDefault());
            return dateFormat.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getDay(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date newDate = dateFormat.parse(date);
            dateFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
            dateFormat.setTimeZone(TimeZone.getDefault());
            return dateFormat.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static Single<Boolean> hasInternet() {
        return Single.fromCallable(() -> {
            try {
                int timeoutMs = 1500;
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress("api.openweathermap.org", 443), timeoutMs);
                socket.close();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
