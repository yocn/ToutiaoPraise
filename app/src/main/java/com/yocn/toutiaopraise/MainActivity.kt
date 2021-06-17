package com.yocn.toutiaopraise

import android.graphics.Point
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.yocn.toutiaopraise.databinding.ActivityMainBinding

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
        mainAdapter.setShowistener(object : ShowInterface.OnPraiseShowPositionInterface {
            override fun showAnchor(view: View) {
                showPraiseView(view)
            }

            override fun show(isShow: Boolean) {
                activityMainBinding.root.setHandled(true)
            }
        })
        activityMainBinding.root.setInterface(object :
            ShowInterface.OnPraiseShowInterface {
            override fun show(isShow: Boolean) {
                if (!isShow) {
                    hidePraiseView()
                }
            }
        })
    }

    fun showPraiseView(anchorView: View?) {
        val point = IntArray(2)
        anchorView?.getLocationInWindow(point)
        val praiseView = PraiseView(this, Point(point[0], point[1]))
        activityMainBinding.tlContainerEffect.addView(praiseView)
        praiseView.startParty()
    }

    fun hidePraiseView() {
        LogUtil.d("hidePraiseView")
        (activityMainBinding.tlContainerEffect.getChildAt(0) as PraiseView).stopParty()
        activityMainBinding.tlContainerEffect.removeAllViews()
    }
}