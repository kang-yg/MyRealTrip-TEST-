package com.yg.myrealtrip

import android.app.Application

class GlobalVariable : Application() {
    companion object {
        lateinit var resultLink: ArrayList<String>
        lateinit var resulImageLink: ArrayList<String>
        lateinit var resultDescription: ArrayList<String>
    }
}