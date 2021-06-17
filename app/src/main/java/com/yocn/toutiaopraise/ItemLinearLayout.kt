package com.yocn.toutiaopraise

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.LinearLayout

class ItemLinearLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val TRIGGER_NUMBER = 10
    var handle = true
    var touchTs = 0L

    var onShowInterface: ShowInterface.OnPraiseShowInterface? = null

    fun setShowInterface(onShowInterface: ShowInterface.OnPraiseShowInterface) {
        this.onShowInterface = onShowInterface
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        LogUtil.d("ItemLinearLayout:::", event?.action.toString())
        when (event?.action) {
            MotionEvent.ACTION_MOVE -> {
                if (handle && (System.currentTimeMillis() - touchTs) >= 1000) {
                    handle = false
                    onShowInterface?.show(true)
                }
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        LogUtil.d("ItemLinearLayout:: onInterceptTouchEvent:", ev?.action.toString())
        touchTs = System.currentTimeMillis()
        handle = true
        return handle
    }
}