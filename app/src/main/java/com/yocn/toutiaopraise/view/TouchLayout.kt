package com.yocn.toutiaopraise.view

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import com.yocn.toutiaopraise.Constant
import com.yocn.toutiaopraise.ShowInterface
import com.yocn.toutiaopraise.util.LogUtil

class TouchLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    var showHandler: Handler? = null
    var handle = false
    var onPraiseViewInterface: ShowInterface.OnPraiseShowInterface? = null;

    fun setInterface(praiseViewInterface: ShowInterface.OnPraiseShowInterface) {
        this.onPraiseViewInterface = praiseViewInterface
    }

    fun setHandler(handler: Handler) {
        showHandler = handler
    }

    fun setHandled(handled: Boolean) {
        this.handle = handled
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        LogUtil.d("TouchLayout::onTouchEvent:", LogUtil.formatTag(event?.action))
        when (event?.action) {
            MotionEvent.ACTION_UP -> {
                handleTouchEvent()
                return true
            }
            MotionEvent.ACTION_CANCEL -> {
                handleTouchEvent()
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    fun handleTouchEvent() {
        handle = false
        showHandler?.removeMessages(Constant.MESSAGE_PRAISE_SHOW)
        onPraiseViewInterface?.show(false)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        LogUtil.d(
            "TouchLayout onInterceptTouchEvent:::",
            LogUtil.formatTag(ev?.action), handle.toString()
        )
        when (ev?.action) {
            MotionEvent.ACTION_UP -> {
                handleTouchEvent()
            }
        }
        return handle
    }
}