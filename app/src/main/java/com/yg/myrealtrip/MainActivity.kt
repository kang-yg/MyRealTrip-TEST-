package com.yg.myrealtrip

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    lateinit var myHandler: Handler
    val READXML: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myHandler = object : Handler() {
            override fun handleMessage(msg: Message?) {
                when (msg!!.what) {
                    READXML -> {
                        CallReadMetaDes().start()
                        CallReadMetaImage().start()
                    }
                }
            }
        }
        CallReadXML().start()

        //TODO 쓰레드 작업 완료 후 newsList생성
        val newsList: ArrayList<NewsListItem> = ArrayList()
        newsList.add(
            NewsListItem(
                "http://flexible.img.hani.co.kr/flexible/normal/960/720/imgdb/original/2020/0323/20200323501445.jpg",
                "제목0",
                "일부0",
                "KEY0_0",
                "KEY0_1",
                "KEY0_2"
            )
        )
        newsList.add(NewsListItem("사진1", "제목1", "일부1", "KEY1_0", "KEY1_1", "KEY1_2"))

        val viewManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        val viewAdapter: RecyclerView.Adapter<*> = NewsListAdapter(newsList)
        val recyclerView: RecyclerView = findViewById<RecyclerView>(R.id.newsList).apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    inner class CallReadXML : Thread() {
        val myMessage: Message = myHandler.obtainMessage()
        override fun run() {
            myMessage.what = READXML
            val readXML: ReadXML = ReadXML()
            GlobalVariable.resultLink = readXML.readXML()

            myHandler.sendMessage(myMessage)
        }
    }

    inner class CallReadMetaDes : Thread() {
        override fun run() {
            val readMetaProperty: ReadMetaProperty = ReadMetaProperty()
            GlobalVariable.resultDescription = readMetaProperty.getDescription(GlobalVariable.resultLink)
        }
    }

    inner class CallReadMetaImage : Thread() {
        override fun run() {
            val readMetaProperty: ReadMetaProperty = ReadMetaProperty()
            GlobalVariable.resulImageLink = readMetaProperty.getThumbnail(GlobalVariable.resultLink)
        }
    }
}
