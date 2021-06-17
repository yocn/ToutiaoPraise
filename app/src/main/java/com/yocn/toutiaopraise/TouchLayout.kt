package com.yocn.toutiaopraise

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout

class TouchLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    var handle = false
    var onPraiseViewInterface: ShowInterface.OnPraiseShowInterface? = null;

    fun setInterface(praiseViewInterface: ShowInterface.OnPraiseShowInterface) {
        this.onPraiseViewInterface = praiseViewInterface
    }

    fun setHandled(handled: Boolean) {
        this.handle = handled
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        LogUtil.d("TouchLayout::onTouchEvent:", event?.action.toString())
        when (event?.action) {
            MotionEvent.ACTION_UP -> {
                handle = false
                onPraiseViewInterface?.show(false)
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        LogUtil.d("TouchLayout onInterceptTouchEvent:::", ev?.action.toString(), "   " + handle)
        return handle
    }
}