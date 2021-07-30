package com.raja.internetspeed.Support;

import android.content.Context;
import android.net.TrafficStats;

import java.text.DecimalFormat;
import java.util.*;

import android.net.wifi.WifiManager;

public class TrafficUtils {

    static long GB = 1000000000;
    static long MB = 1000000;
    static long KB = 1000;

    public static String getNetworkSpeed() {

        String downloadSpeedOutput = "";
        String units = "";
        long mBytesPrevious = TrafficStats.getTotalRxBytes() + TrafficStats.getTotalTxBytes();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long mBytesCurrent = TrafficStats.getTotalRxBytes() + TrafficStats.getTotalTxBytes();

        long mNetworkSpeed = mBytesCurrent - mBytesPrevious;

        float mDownloadSpeedWithDecimals;

        if (mNetworkSpeed >= GB) {
            mDownloadSpeedWithDecimals = (float) (mNetworkSpeed / GB);
            units = " GB";
        } else if (mNetworkSpeed >= MB) {
            mDownloadSpeedWithDecimals = (float) mNetworkSpeed / MB;
            units = " MB";

        } else {
            mDownloadSpeedWithDecimals = (float) mNetworkSpeed / KB;
            units = " KB";
        }

//        DecimalFormat df = new DecimalFormat("###.##");
//
//        long l = Long.parseLong(df.format(mDownloadSpeedWithDecimals));

        downloadSpeedOutput = "" + String.format("%.2f", mDownloadSpeedWithDecimals);

        /*downloadSpeedOutput = if (units != " KB" && mDownloadSpeedWithDecimals < 100) {
            String.format(Locale.US, "%.1f", mDownloadSpeedWithDecimals);
        } else {
            Integer.toString(mDownloadSpeedWithDecimals.toInt());
        }*/

        return (downloadSpeedOutput + units);

    }

    public static float getNetworkSpeedwithoutUnit() {

        long mBytesPrevious = TrafficStats.getTotalRxBytes() + TrafficStats.getTotalTxBytes();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long mBytesCurrent = TrafficStats.getTotalRxBytes() + TrafficStats.getTotalTxBytes();

        long mNetworkSpeed = mBytesCurrent - mBytesPrevious;

        float mDownloadSpeedWithDecimals;

        if (mNetworkSpeed >= GB) {
            mDownloadSpeedWithDecimals = (float) (mNetworkSpeed / GB);
        } else if (mNetworkSpeed >= MB) {
            mDownloadSpeedWithDecimals = (float) mNetworkSpeed / MB;

        } else {
            mDownloadSpeedWithDecimals = (float) mNetworkSpeed / KB;
        }

        /*downloadSpeedOutput = if (units != " KB" && mDownloadSpeedWithDecimals < 100) {
            String.format(Locale.US, "%.1f", mDownloadSpeedWithDecimals);
        } else {
            Integer.toString(mDownloadSpeedWithDecimals.toInt());
        }*/

        return mDownloadSpeedWithDecimals;

    }

    public static String getUnits() {

        String downloadSpeedOutput = "";
        String units = "";
        long mBytesPrevious = TrafficStats.getTotalRxBytes() + TrafficStats.getTotalTxBytes();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long mBytesCurrent = TrafficStats.getTotalRxBytes() + TrafficStats.getTotalTxBytes();

        long mNetworkSpeed = mBytesCurrent - mBytesPrevious;

        float mDownloadSpeedWithDecimals;

        if (mNetworkSpeed >= GB) {
            mDownloadSpeedWithDecimals = (float) (mNetworkSpeed / GB);
            units = " GB";
        } else if (mNetworkSpeed >= MB) {
            mDownloadSpeedWithDecimals = (float) mNetworkSpeed / MB;
            units = " MB";

        } else {
            mDownloadSpeedWithDecimals = (float) mNetworkSpeed / KB;
            units = " KB";
        }

        downloadSpeedOutput = "" + mDownloadSpeedWithDecimals;

        /*downloadSpeedOutput = if (units != " KB" && mDownloadSpeedWithDecimals < 100) {
            String.format(Locale.US, "%.1f", mDownloadSpeedWithDecimals);
        } else {
            Integer.toString(mDownloadSpeedWithDecimals.toInt());
        }*/

        return units;

    }


    public static long convertToBytes(float value, String unit) {

        switch (unit) {
            case "KB":
                return (long) value * KB;
            case "MB":
                return (long) value * MB;
            case "GB":
                return (long) value * GB;
        }
        return 0;
    }

    public static String getMetricData(long bytes) {


        float dataWithDecimals;
        String units;
        if (bytes >= GB) {
            dataWithDecimals = (float) bytes / GB;
            units = " GB";
        } else if (bytes >= MB) {
            dataWithDecimals = (float) bytes / MB;
            units = " MB";

        } else {
            dataWithDecimals = (float) bytes / KB;
            units = " KB";
        }

        float output = dataWithDecimals;

        /*var output = if (units != " KB" && dataWithDecimals < 100) {
            String.format(Locale.US, "%.1f", dataWithDecimals)
        } else {
            Integer.toString(dataWithDecimals.toInt())
        }*/

        return output + units;
    }

}
