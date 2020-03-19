package com.yg.myrealtrip

import android.util.Log
import android.util.Xml
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.xmlpull.v1.XmlPullParser
import java.io.IOException
import java.io.InputStream
import java.net.URL

class ReadNews{
    val job: Job = Job()
    val scope: CoroutineScope = CoroutineScope(Dispatchers.IO + job)

    fun readNews() {
        Log.d("readNews()", "call")
        scope.launch {
            Log.d("readNews()", "launch")
            var title : String
            var link : String
            var description : String

            try {
                val url: URL = URL("https://news.google.com/rss")
                val inputStream: InputStream = url.openConnection().getInputStream()

                var myParser: XmlPullParser = Xml.newPullParser()
                myParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
                myParser.setInput(inputStream, null)

                myParser.nextTag()
                while (myParser.next() != XmlPullParser.END_DOCUMENT) {
                    val name : String = myParser.name
                    var result : String = ""
                    if (myParser.next() == XmlPullParser.TEXT){
                        result = myParser.text
                        myParser.nextTag()
                    }

                    if (name.equals("title")){
                        title = result
                        Log.d("readNews()", "title : ${title}")

                    }
                    if (name.equals("link")){
                        link = result
                        Log.d("readNews()", "link : ${link}")

                    }
                    if (name.equals("description")){
                        description = result
                        Log.d("readNews()", "description : ${description}")

                    }

                    Log.d("readNews()", "CheckPoint")
                }

            } catch (e: IOException) {
                Log.e("readNews()", e.toString())
            }
        }
    }

}