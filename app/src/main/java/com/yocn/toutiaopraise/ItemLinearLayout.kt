package com.yocn.toutiaopraise

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.LinearLayout

class ItemLinearLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    var showHandler: Handler? = null
    val praiseIv by lazy {
        getChildAt(0)
    }

    fun setHandler(handler: Handler) {
        showHandler = handler
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        LogUtil.d("ItemLinearLayout:::", LogUtil.formatTag(event?.action))
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> prepareShow()
        }
        return super.onTouchEvent(event)
    }

    fun prepareShow() {
        val position = IntArray(2)
        praiseIv.getLocationInWindow(position)
        LogUtil.d(position[0].toString(), position[1].toString())
        val message = showHandler?.obtainMessage(Constant.MESSAGE_PRAISE_SHOW)
        message?.what = Constant.MESSAGE_PRAISE_SHOW
        message?.arg1 = position[0]
        message?.arg2 = position[1]
        showHandler?.sendMessageDelayed(message!!, 500)
        showHandler?.sendEmptyMessage(Constant.MESSAGE_PRAISE_GIVE_UP)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return true
    }
}