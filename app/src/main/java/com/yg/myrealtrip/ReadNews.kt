package com.yg.myrealtrip

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.net.URL

val STEP_NON: Int = 0
val STEP_TITLE: Int = 1
val STEP_LINK: Int = 2

class ReadNews {


    fun readNews() {
        Log.d("readNews()", "start")

    }

    fun readXML() {
        val job: Job = Job()
        val scope: CoroutineScope = CoroutineScope(Dispatchers.IO + job)

        scope.launch {
            Log.d("readXML()", "launch")

            var title: String
            var link: String

            val url: URL = URL("https://news.google.com/rss?hl=ko&gl=KR&ceid=KR:ko")
            val parserFactory: XmlPullParserFactory = XmlPullParserFactory.newInstance()
            val parser: XmlPullParser = parserFactory.newPullParser()

            var insideItem: Boolean = false
            var step: Int = 0
            try {
                parser.setInput(url.openConnection().getInputStream(), "UTF-8")

                var eventType: Int = parser.eventType
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_DOCUMENT) {

                    } else if (eventType == XmlPullParser.START_TAG) {
                        var startTag: String = parser.name
                        if (startTag.equals("item")) {
                            insideItem = true
                        } else if (startTag.equals("title")) {
                            step = STEP_TITLE
                        } else if (startTag.equals("link")) {
                            step = STEP_LINK
                        } else {
                            insideItem = false
                            step = STEP_NON
                        }
                    } else if (eventType == XmlPullParser.TEXT) {
                        var text: String = parser.text
                        if (insideItem) {
                            if (step == STEP_TITLE) {
                                title = text
                                Log.d("readXML()", "TITLE: ${text}")
                            } else if (step == STEP_LINK) {
                                link = text
                                Log.d("readXML()", "LINK: ${text}")
                            }
                        }
                    }

                    eventType = parser.next()
                }
            } catch (e: IOException) {
                Log.e("readXML()", e.printStackTrace().toString())
            }
        }
    }

    //TODO Use Glide → get Image
    fun getThumbnail(_link: String) {
        val job: Job = Job()
        val scope: CoroutineScope = CoroutineScope(Dispatchers.IO + job)

        scope.launch {
            try {
                val doc: Document = Jsoup.connect(_link).get()
                val element: Elements = doc.select("meta[property=og:image]")
                val imageLink: String = element.first().attr("content")
                Log.d("getThumbnail()", "${imageLink}")

            } catch (e: IOException) {
                Log.e("getThumbnail()", e.printStackTrace().toString())
            }
        }
    }

    fun getDescription(_link: String) {
        val job: Job = Job()
        val scope: CoroutineScope = CoroutineScope(Dispatchers.IO + job)

        scope.launch {
            try {
                val doc: Document = Jsoup.connect(_link).get()
                val element: Elements = doc.select("meta[property=og:description]")
                val description: String = element.first().attr("content")
                Log.d("getThumbnail()", "${description}")

            } catch (e: IOException) {
                Log.e("getThumbnail()", e.printStackTrace().toString())
            }
        }
    }
}