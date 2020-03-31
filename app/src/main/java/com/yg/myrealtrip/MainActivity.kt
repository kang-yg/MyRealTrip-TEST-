package com.yg.myrealtrip

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var myHandler: Handler
    val READXML: Int = 1
    val READMETA: Int = 2
    var metaFlag: BooleanArray = BooleanArray(2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //RecyclerView add item
        val newsList: ArrayList<NewsListItem> = ArrayList()
        myHandler = object : Handler() {
            override fun handleMessage(msg: Message?) {
                when (msg!!.what) {
                    READXML -> {
                        CallReadMetaDes().start()
                        CallReadMetaImage().start()
                    }

                    READMETA -> {
                        if (metaFlag[0] && metaFlag[1]) {
                            newsList_progress.visibility = View.GONE
                            for (i in 0 until GlobalVariable.resultTitle.size) {
                                newsList.add(
                                    NewsListItem(
                                        GlobalVariable.resultLink[i],
                                        GlobalVariable.resulImageLink[i],
                                        GlobalVariable.resultTitle[i],
                                        GlobalVariable.resultDescription[i],
                                        "KEY${i}_0",
                                        "KEY${i}_1",
                                        "KEY${i}_2"
                                    )
                                )
                            }

                            val viewManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
                            val viewAdapter: RecyclerView.Adapter<*> = NewsListAdapter(newsList) { news ->
                                Toast.makeText(applicationContext, "${news.newsTitle}", Toast.LENGTH_SHORT).show()
                                val intent: Intent = Intent(applicationContext, NewsDetailAcivity::class.java)
                                intent.putExtra("title", news.newsTitle)
                                intent.putExtra("link", news.newsLink)
                                startActivity(intent)
                            }
                            findViewById<RecyclerView>(R.id.newsList).apply {
                                layoutManager = viewManager
                                adapter = viewAdapter
                            }

                            refresh_layout.isRefreshing = false
                            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                        }
                    }
                }
            }
        }
        CallReadXML().start()

        //RecyclerView refresh
        refresh_layout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            Log.d("setOnRefreshListener()", "setOnRefreshListener()")
            GlobalVariable.resultTitle.clear()
            GlobalVariable.resultLink.clear()
            GlobalVariable.resulImageLink.clear()
            GlobalVariable.resultDescription.clear()
            newsList.clear()

            metaFlag[0] = false
            metaFlag[1] = false

            CallReadXML().start()

            window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        })
    }

    inner class CallReadXML : Thread() {
        val myMessage: Message = myHandler.obtainMessage()
        override fun run() {
            myMessage.what = READXML
            val readXML: ReadXML = ReadXML()
            GlobalVariable.resultTitle = readXML.readXML().get("title")!!
            GlobalVariable.resultLink = readXML.readXML().get("link")!!

            myHandler.sendMessage(myMessage)
        }
    }

    inner class CallReadMetaDes : Thread() {
        val myMessage: Message = myHandler.obtainMessage()
        override fun run() {
            myMessage.what = READMETA
            val readMetaProperty: ReadMetaProperty = ReadMetaProperty()
            GlobalVariable.resultDescription = readMetaProperty.getDescription(GlobalVariable.resultLink)

            metaFlag[0] = true
            myHandler.sendMessage(myMessage)
        }
    }

    inner class CallReadMetaImage : Thread() {
        val myMessage: Message = myHandler.obtainMessage()
        override fun run() {
            myMessage.what = READMETA
            val readMetaProperty: ReadMetaProperty = ReadMetaProperty()
            GlobalVariable.resulImageLink = readMetaProperty.getThumbnail(GlobalVariable.resultLink)

            metaFlag[1] = true
            myHandler.sendMessage(myMessage)
        }
    }
}
