package com.codewithrish.dictionaryapp

import android.app.Application
import android.content.res.Resources
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
    companion object {
        lateinit var instance: App
            private set
    }

    private lateinit var _res: Resources
    val res: Resources
        get() = _res

    override fun onCreate() {
        super.onCreate()
        instance = this
        _res = resources
    }
}