package com.yg.myrealtrip

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val readNews : ReadNews = ReadNews()

        val newsList: ArrayList<NewsListItem> = ArrayList()
        newsList.add(NewsListItem("http://flexible.img.hani.co.kr/flexible/normal/960/720/imgdb/original/2020/0323/20200323501445.jpg", "제목0", "일부0", "KEY0_0", "KEY0_1", "KEY0_2"))
        newsList.add(NewsListItem("사진1", "제목1", "일부1", "KEY1_0", "KEY1_1", "KEY1_2"))

        val viewManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        val viewAdapter: RecyclerView.Adapter<*> = NewsListAdapter(newsList)
        val recyclerView: RecyclerView = findViewById<RecyclerView>(R.id.newsList).apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
}
