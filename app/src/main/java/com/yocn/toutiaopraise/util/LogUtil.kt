package com.yocn.toutiaopraise.util

import android.util.Log
import android.view.MotionEvent

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

        fun formatTag(action: Int?): String {
            var result = ""
            when (action) {
                MotionEvent.ACTION_DOWN -> result = "ACTION_DOWN"
                MotionEvent.ACTION_MOVE -> result = "ACTION_MOVE"
                MotionEvent.ACTION_CANCEL -> result = "ACTION_CANCEL"
                MotionEvent.ACTION_UP -> result = "ACTION_UP"
                else -> "NULL"
            }
            return result
        }
    }

}