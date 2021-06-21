package com.yocn.toutiaopraise

import android.graphics.Point
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.Gravity
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.yocn.toutiaopraise.bean.MainBean
import com.yocn.toutiaopraise.databinding.ActivityMainBinding
import com.yocn.toutiaopraise.util.CustomDividerItemDecoration
import com.yocn.toutiaopraise.util.LogUtil
import com.yocn.toutiaopraise.util.ScreenUtil
import com.yocn.toutiaopraise.view.PraiseView

class MainActivity : AppCompatActivity() {
    private val activityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = activityMainBinding.root
        setContentView(view)
        val list = arrayListOf<MainBean>()
        for (i in 1..10) {
            list.add(MainBean(false))
        }
        val mainAdapter = MainAdapter(list)
        activityMainBinding.root.handler = showHandler
        mainAdapter.setHandler(showHandler)
        activityMainBinding.rvMain.adapter = mainAdapter
        activityMainBinding.rvMain.layoutManager = LinearLayoutManager(this)
        activityMainBinding.rvMain.addItemDecoration(
            CustomDividerItemDecoration(
                this,
                CustomDividerItemDecoration.HORIZONTAL_LIST,
                20, R.color.gray
            )
        )
        // 为什么不能用一个longTouchListener？ 因为检测不到手指抬起
        // 为什么不能只用一个touchListener? 因为移出view的范围之后会收到一个CANCEL，再也检测不到up了
        activityMainBinding.root.setInterface(object :
            ShowInterface.OnPraiseShowInterface {
            override fun show(isShow: Boolean) {
                if (!isShow) {
                    hidePraiseView()
                }
            }
        })
    }

    var showHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                Constant.MESSAGE_PRAISE_SHOW -> {
                    val x = msg.arg1
                    val y = msg.arg2
                    LogUtil.d("show", x.toString(), y.toString())
                    showPraiseView(x, y)
                }
                Constant.MESSAGE_PRAISE_GIVE_UP -> {
                    activityMainBinding.root.setHandled(true)
                }
            }
        }
    }

    fun showPraiseView(x: Int, y: Int) {
        val praiseView =
            PraiseView(this, Point(x, y))
        activityMainBinding.tlContainerEffect.addView(praiseView)
        val screenHeight = ScreenUtil.getScreenHeight(this)
        val lp = praiseView.layoutParams as FrameLayout.LayoutParams
        lp.bottomMargin = screenHeight - y
        lp.gravity = Gravity.BOTTOM
        praiseView.layoutParams = lp
        praiseView.startParty()
    }

    fun hidePraiseView() {
        val praiseView = activityMainBinding.tlContainerEffect.getChildAt(0)
        if (praiseView != null) {
            (praiseView as PraiseView).stopParty()
        }
        activityMainBinding.tlContainerEffect.removeAllViews()
    }
}