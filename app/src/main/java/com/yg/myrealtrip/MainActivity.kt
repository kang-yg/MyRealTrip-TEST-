package com.yg.myrealtrip

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var myHandler: Handler
    val READXML: Int = 1
    val READMETA: Int = 2
    var metaFlag: BooleanArray = BooleanArray(2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
                            val viewAdapter: RecyclerView.Adapter<*> = NewsListAdapter(newsList)
                            findViewById<RecyclerView>(R.id.newsList).apply {
                                layoutManager = viewManager
                                adapter = viewAdapter
                            }
                        }
                    }
                }
            }
        }
        CallReadXML().start()
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
