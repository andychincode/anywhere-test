package com.test.anywhere

import android.app.Application
import com.test.anywhere.model.RelatedTopic

class TestApplication : Application() {
    companion object {
        lateinit var appInstance: TestApplication
        fun getInstance() = appInstance

        var currentTopic : RelatedTopic? = null
    }

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }
}