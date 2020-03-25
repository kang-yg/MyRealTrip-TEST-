package com.yg.myrealtrip

import android.util.Log
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.net.URL

val STEP_NON: Int = 0
val STEP_TITLE: Int = 1
val STEP_LINK: Int = 2

class ReadXML {
    fun readXML(): HashMap<String, ArrayList<String>> {
        Log.d("readXML()", "readXML()")

        var resultLink: ArrayList<String> = ArrayList()
        var resultTitle: ArrayList<String> = ArrayList()
        var finalResult: HashMap<String, ArrayList<String>> = hashMapOf()

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
                            resultTitle.add(text)
                            Log.d("readXML()", "TITLE: ${text}")
                        } else if (step == STEP_LINK) {
                            resultLink.add(text)
                            Log.d("readXML()", "LINK: ${text}")
                        }
                    }
                }

                eventType = parser.next()
            }
        } catch (e: IOException) {
            Log.e("readXML()", e.printStackTrace().toString())
        }

        finalResult.put("title", resultTitle)
        finalResult.put("link", resultLink)

        return finalResult
    }
}