package com.yocn.toutiaopraise

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.yocn.toutiaopraise.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val list = arrayListOf<MainBean>()
        for (i in 1..10) {
            list.add(MainBean(false))
        }
        val mainAdapter = MainAdapter(list)
        binding.rvMain.adapter = mainAdapter
        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.addItemDecoration(
            CustomDividerItemDecoration(
                this,
                CustomDividerItemDecoration.HORIZONTAL_LIST,
                20, R.color.gray
            )
        )
    }
}