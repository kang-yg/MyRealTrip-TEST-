package com.yg.myrealtrip

import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException

class ReadMetaProperty {
    fun getThumbnail(_linkArray: ArrayList<String>): ArrayList<String> {
        Log.d("getThumbnail()", "getThumbnail()")

        var resultImageLink: ArrayList<String> = ArrayList()
        try {
            for (i in 0 until _linkArray.size) {
                val doc: Document = Jsoup.connect(_linkArray[i]).get()
                val elements: Elements = doc.select("meta[property=og:image]")
                if (!elements.isNullOrEmpty()) {
                    val imageLink: String = elements[0].attr("content")
                    resultImageLink.add(imageLink)
                    Log.d("getThumbnail()", "Image: ${imageLink}")
                } else {
                    resultImageLink.add("NO DATA")
                    Log.d("getThumbnail()", "NO DATA")
                }
            }
        } catch (e: IOException) {
            resultImageLink.add("NO DATA")
//            Log.e("getThumbnail()", "error : ${e.message}")
        } finally {
            Log.d("getThumbnail()", "finish")
        }

        return resultImageLink
    }

    fun getDescription(_linkArray: ArrayList<String>): ArrayList<String> {
        Log.d("getDescription()", "getDescription()")

        var resultDescription: ArrayList<String> = ArrayList()
        try {
            for (i in 0 until _linkArray.size) {
                val doc: Document = Jsoup.connect(_linkArray[i]).get()
                val elements: Elements = doc.select("meta[property=og:description]")
                if (!elements.isNullOrEmpty()) {
                    val description: String = elements[0].attr("content")
                    resultDescription.add(description)
                    Log.d("getDescription()", "Descriotion: ${description}")
                } else {
                    resultDescription.add("NO DATA")
                    Log.d("getDescription()", "NO DATA")
                }
            }
        } catch (e: IOException) {
            resultDescription.add("NO DATA")
//            Log.e("getDescription()", "error : ${e.message}")
        } finally {
            Log.d("getDescription()", "finish")
        }

        return resultDescription
    }
}