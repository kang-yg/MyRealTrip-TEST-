package com.yg.myrealtrip

class NewsListItem {
    lateinit var newsThumbnail: String
    lateinit var newsTitle: String
    lateinit var newsPart: String
    lateinit var newsKey0: String
    lateinit var newsKey1: String
    lateinit var newsKey2: String

    constructor(
        newsThumbnail: String,
        newsTitle: String,
        newsPart: String,
        newsKey0: String,
        newsKey1: String,
        newsKey2: String
    ) {
        this.newsThumbnail = newsThumbnail
        this.newsTitle = newsTitle
        this.newsPart = newsPart
        this.newsKey0 = newsKey0
        this.newsKey1 = newsKey1
        this.newsKey2 = newsKey2
    }
}