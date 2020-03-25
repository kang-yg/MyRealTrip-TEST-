package com.yg.myrealtrip

import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException

class ReadMetaProperty {
    fun getThumbnail(_linkArray: ArrayList<String>): ArrayList<String> {
        Log.d("getThumbnail()", "getThumbnail()")

        var resulImageLink: ArrayList<String> = ArrayList()
        try {
            for (i in _linkArray) {
                val doc: Document = Jsoup.connect(i).get()
                val elements: Elements = doc.select("meta[property=og:image]")
                if (!elements.isNullOrEmpty()) {
                    val imageLink: String = elements.first().attr("content")
                    resulImageLink.add(imageLink)
                    Log.d("getThumbnail()", "Image: ${imageLink}")
                } else {
                    resulImageLink.add("NO DATA")
                }
            }
        } catch (e: IOException) {
            Log.e("getThumbnail()", e.printStackTrace().toString())
        } finally {
            Log.d("getThumbnail()", "finish")
        }

        return resulImageLink
    }

    fun getDescription(_linkArray: ArrayList<String>): ArrayList<String> {
        Log.d("getDescription()", "getDescription()")

        var resultDescription: ArrayList<String> = ArrayList()
        try {
            for (i in _linkArray) {
                val doc: Document = Jsoup.connect(i).get()
                val elements: Elements = doc.select("meta[property=og:description]")
                if (!elements.isNullOrEmpty()) {
                    val description: String = elements.first().attr("content")
                    resultDescription.add(description)
                    Log.d("getDescription()", "Descriotion: ${description}")
                } else {
                    resultDescription.add("NO DATA")
                }
            }
        } catch (e: IOException) {
            Log.e("getDescription()", e.printStackTrace().toString())
        } finally {
            Log.d("getDescription()", "finish")

        }

        return resultDescription
    }
}