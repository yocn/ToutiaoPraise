package com.yocn.toutiaopraise

import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yocn.toutiaopraise.databinding.ItemMainBinding
import com.yocn.toutiaopraise.view.ItemLinearLayout

class MainAdapter(val list: List<MainBean>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    var showHandler: Handler? = null
    private var showListener: ShowInterface.OnPraiseShowPositionInterface? = null

    fun setShowistener(longClickListener: ShowInterface.OnPraiseShowPositionInterface) {
        this.showListener = longClickListener
    }

    fun setHandler(handler: Handler) {
        showHandler = handler
    }

    inner class ViewHolder(itemMainBinding: ItemMainBinding) :
        RecyclerView.ViewHolder(itemMainBinding.root) {
        val containerFl: FrameLayout
        val praiseLl: ItemLinearLayout
        val praiseIv: ImageView
        val praiseTv: TextView

        init {
            containerFl = itemMainBinding.llContainter
            praiseLl = itemMainBinding.llPraise
            praiseIv = itemMainBinding.ivPraise
            praiseTv = itemMainBinding.tvPraise
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemMainBinding =
            ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemMainBinding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MainAdapter.ViewHolder, position: Int) {
        holder.praiseLl.setOnClickListener {
            holder.praiseIv.setImageResource(if (list[position].isPraise) R.drawable.praise_off else R.drawable.praise_on)
        }
        holder.praiseLl.handler = showHandler
//        holder.praiseLl.setShowInterface(object : ShowInterface.OnPraiseShowInterface {
//            override fun show(isShow: Boolean) {
//                showListener?.show(isShow)
//                showListener?.showAnchor(holder.praiseIv)
//            }
//        })
    }
}