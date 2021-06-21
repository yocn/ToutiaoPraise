package com.yocn.toutiaopraise.bean

import android.graphics.Point

data class ProgressPoint(var x: Int, var y: Int, var progress: Float) {
    constructor(point: Point, progress: Float) : this(point.x, point.y, progress)

    override fun toString(): String {
        return "ProgressPoint(x=$x, y=$y, progress=$progress)"
    }


}