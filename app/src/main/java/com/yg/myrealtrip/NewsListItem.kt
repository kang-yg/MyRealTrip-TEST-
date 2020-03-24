package com.yg.myrealtrip

class NewsListItem {
    lateinit var newsLink : String
    lateinit var newsThumbnailLink: String
    lateinit var newsTitle: String
    lateinit var newsDescription: String
    lateinit var newsKey0: String
    lateinit var newsKey1: String
    lateinit var newsKey2: String

    constructor(
        newsThumbnailLink: String,
        newsTitle: String,
        newsDescription: String,
        newsKey0: String,
        newsKey1: String,
        newsKey2: String
    ) {
        this.newsThumbnailLink = newsThumbnailLink
        this.newsTitle = newsTitle
        this.newsDescription = newsDescription
        this.newsKey0 = newsKey0
        this.newsKey1 = newsKey1
        this.newsKey2 = newsKey2
    }

    constructor()
}