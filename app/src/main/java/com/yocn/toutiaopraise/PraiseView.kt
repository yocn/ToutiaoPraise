package com.yocn.toutiaopraise

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
import androidx.core.view.setPadding
import com.yocn.toutiaopraise.databinding.PraiseViewBinding
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
    val praiseViewBinding: PraiseViewBinding
    var isPartying = false;
    var curtNumber = 0
    val startPoint = praisePoint
    var numberViews = mutableListOf<ImageView>()
    var animStartPoint = Point()

    init {
        praiseViewBinding =
            PraiseViewBinding.inflate(LayoutInflater.from(getContext()), this, true)
    }

    fun startParty() {
        isPartying = true;
        GlobalScope.launch {
            do {
                GlobalScope.launch(Dispatchers.Main) {
                    updateNumber(curtNumber++)
                    praiseViewBinding.tvTest.text = curtNumber.toString()
                }
                delay(100)
            } while (isPartying)
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
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }

    private fun updateTagDrawable() {
        praiseViewBinding.ivPraiseTag.setImageResource(currTagResID)
        val layoutParam = praiseViewBinding.ivPraiseTag.layoutParams
        layoutParam.width = currTagWH.x
        layoutParam.height = currTagWH.y
        praiseViewBinding.ivPraiseTag.layoutParams = layoutParam
    }

    fun getAnim() {
        if (animStartPoint.x == 0) {
            return
        }
        var randomPoint = Point()
        var endPoint = Point(0, startPoint.y)
        var bezierEvaluator = BezierEvaluator(randomPoint)
        var valueAnimator = ValueAnimator.ofObject(bezierEvaluator, startPoint)
        valueAnimator.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation: ValueAnimator?) {
                var curr = animation?.animatedValue
                LogUtil.d(curr.toString())
            }
        })
    }

}