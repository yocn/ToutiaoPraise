package com.yocn.toutiaopraise

import android.animation.TypeEvaluator
import com.yocn.toutiaopraise.bean.ProgressPoint

class BezierEvaluator(private val controllPoint: ProgressPoint) : TypeEvaluator<ProgressPoint> {
    override fun evaluate(
        t: Float,
        startValue: ProgressPoint,
        endValue: ProgressPoint
    ): ProgressPoint {
        val x =
            ((1 - t) * (1 - t) * startValue.x + 2 * t * (1 - t) * controllPoint.x + t * t * endValue.x).toInt()
        val y =
            ((1 - t) * (1 - t) * startValue.y + 2 * t * (1 - t) * controllPoint.y + t * t * endValue.y).toInt()
        return ProgressPoint(x, y, t)
    }

}