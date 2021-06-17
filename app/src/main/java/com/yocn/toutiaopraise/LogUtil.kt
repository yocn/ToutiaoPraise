package com.yocn.toutiaopraise

import android.util.Log

class LogUtil {
    companion object {
        val TAG = "LogUtil"
        fun d(vararg args: String) {
            val ss = StringBuilder()
            for (s in args) {
                ss.append(s).append(" ")
            }
            Log.d(TAG, ss.toString())
        }
    }

}