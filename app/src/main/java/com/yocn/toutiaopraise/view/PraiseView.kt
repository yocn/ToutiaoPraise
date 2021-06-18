package com.yocn.toutiaopraise.view

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Point
import android.renderscript.Int2
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.IdRes
import androidx.core.animation.doOnEnd
import androidx.core.view.setPadding
import com.yocn.toutiaopraise.BezierEvaluator
import com.yocn.toutiaopraise.Constant
import com.yocn.toutiaopraise.R
import com.yocn.toutiaopraise.bean.ProgressPoint
import com.yocn.toutiaopraise.databinding.PraiseViewBinding
import com.yocn.toutiaopraise.util.LogUtil
import com.yocn.toutiaopraise.util.ScreenUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class PraiseView @JvmOverloads constructor(
    context: Context,
    praisePoint: Point,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr) {
    val TAG = "Party"
    val praiseViewBinding: PraiseViewBinding
    var isPartying = false;
    var curtNumber = 0
    var numberViews = mutableListOf<ImageView>()

    init {
        praiseViewBinding =
            PraiseViewBinding.inflate(LayoutInflater.from(getContext()), this, true)
    }

    fun startParty() {
        handler.post {
            mockAPartyAnim()
            isPartying = true;
            GlobalScope.launch {
                do {
                    GlobalScope.launch(Dispatchers.Main) {
                        updateNumber(curtNumber++)
                    }
                    delay(100)
                } while (isPartying)
            }
        }
    }

    fun stopParty() {
        isPartying = false
    }

    @IdRes
    var currTagResID = 0
    var currTagWH = Int2()

    fun updateNumber(number: Int) {
        val numberString = number.toString()
        checkImageViewIsEnough(numberString.indices.count())
        for (i in numberString.indices) {
            val index = numberString[i] - '0'
            val drawableResId = Constant.numbers[index]
            numberViews[i].visibility = View.VISIBLE
            numberViews[i].setImageResource(drawableResId)
        }
        if (number in 0..20) {
            if (currTagResID != Constant.rightRes[0]) {
                currTagResID = Constant.rightRes[0]
                currTagWH.x = resources.getDimensionPixelOffset(R.dimen.dp58)
                currTagWH.y = resources.getDimensionPixelOffset(R.dimen.dp36)
                updateTagDrawable()
            }
        } else if (number in 21..40) {
            if (currTagResID != Constant.rightRes[1]) {
                currTagResID = Constant.rightRes[1]
                currTagWH.x = resources.getDimensionPixelOffset(R.dimen.dp74)
                currTagWH.y = resources.getDimensionPixelOffset(R.dimen.dp36)
                updateTagDrawable()
            }
        } else {
            if (currTagResID != Constant.rightRes[2]) {
                currTagResID = Constant.rightRes[2]
                currTagWH.x = resources.getDimensionPixelOffset(R.dimen.dp89)
                currTagWH.y = resources.getDimensionPixelOffset(R.dimen.dp36)
                updateTagDrawable()
            }
        }
    }

    fun checkImageViewIsEnough(needNumber: Int) {
        if (numberViews.size >= needNumber) {
            return
        }
        val imageView = ImageView(context)
        praiseViewBinding.llNumber.addView(imageView, 0)
        val lp = imageView.layoutParams as (LinearLayout.LayoutParams)
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT
        lp.width = ViewGroup.LayoutParams.WRAP_CONTENT
        lp.marginStart = resources.getDimensionPixelOffset(R.dimen.dp1)
        lp.bottomMargin = resources.getDimensionPixelOffset(R.dimen.dp10)
        lp.gravity = Gravity.BOTTOM
        imageView.layoutParams = lp
        imageView.scaleX = 1.5f
        imageView.scaleY = 1.5f
        imageView.setPadding(resources.getDimensionPixelOffset(R.dimen.dp4))
        numberViews.add(0, imageView)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthMeasureSpec = widthMeasureSpec
        var heightMeasureSpec = heightMeasureSpec
        setMeasuredDimension(
            View.getDefaultSize(0, widthMeasureSpec), View.getDefaultSize(0, heightMeasureSpec)
        )
        val childWidthSize = measuredWidth
        // 高度和宽度一样
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY)
        heightMeasureSpec = widthMeasureSpec
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    private fun updateTagDrawable() {
        praiseViewBinding.ivPraiseTag.setImageResource(currTagResID)
        val layoutParam = praiseViewBinding.ivPraiseTag.layoutParams
        layoutParam.width = currTagWH.x
        layoutParam.height = currTagWH.y
        praiseViewBinding.ivPraiseTag.layoutParams = layoutParam
    }

    var runningAnimatorList = arrayListOf<Animator>()
    var cachedViewList = arrayListOf<ImageView>()

    fun mockAPartyAnim() {
        val iv = ImageView(context)
        praiseViewBinding.flPartyContainer.addView(iv)
        iv.setImageResource(Constant.icons[0])
        val lp = iv.layoutParams as LayoutParams
        lp.width = resources.getDimensionPixelOffset(R.dimen.dp40)
        lp.height = resources.getDimensionPixelOffset(R.dimen.dp40)
        iv.layoutParams = lp

        val startPoint = ProgressPoint(measuredWidth, measuredHeight, 0f)
        val endPoint = ProgressPoint(0, measuredHeight, 0f)
        val centerPoint = ProgressPoint(measuredWidth / 2, measuredHeight / 2, 0f)
        val valueAnimator =
            ValueAnimator.ofObject(BezierEvaluator(centerPoint), startPoint, endPoint)
        valueAnimator.duration = 700
        valueAnimator.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation: ValueAnimator?) {
                val curr = animation?.animatedValue as ProgressPoint
                lp.topMargin = curr.y
                lp.leftMargin = curr.x
                iv.layoutParams = lp
                val process = curr.progress
                if (process > 0.75f) {
                    iv.alpha = (1f - curr.progress) * 4
                }
                LogUtil.d(TAG, process.toString())
            }
        })
        valueAnimator.doOnEnd {
            runningAnimatorList.remove(it)
            praiseViewBinding.flPartyContainer.removeView(iv)
        }
        valueAnimator.start()
        runningAnimatorList.add(valueAnimator)
    }

//    fun getACachedView(): View {
//        if (cachedViewList.size > 0) {
//
//        }
//    }

}