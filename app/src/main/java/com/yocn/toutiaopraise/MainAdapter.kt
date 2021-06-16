package com.yocn.toutiaopraise

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.yocn.toutiaopraise.databinding.ItemMainBinding

class MainAdapter(val list: List<MainBean>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    inner class ViewHolder(itemMainBinding: ItemMainBinding) :
        RecyclerView.ViewHolder(itemMainBinding.root) {
        val containerFl: FrameLayout
        val praiseLl: LinearLayout
        val praiseIv: ImageView

        init {
            containerFl = itemMainBinding.llContainter
            praiseLl = itemMainBinding.llPraise
            praiseIv = itemMainBinding.ivPraise
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
    }
}