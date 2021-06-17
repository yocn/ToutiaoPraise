package com.yocn.toutiaopraise

import android.view.View

interface ShowInterface {
    interface OnPraiseShowInterface {
        fun show(isShow: Boolean)
    }

    interface OnPraiseShowPositionInterface : OnPraiseShowInterface {
        fun showAnchor(view: View)
    }
}