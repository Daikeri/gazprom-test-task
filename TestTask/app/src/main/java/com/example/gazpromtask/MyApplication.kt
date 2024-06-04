package com.example.gazpromtask

import android.app.Application
import com.example.gazpromtask.dependcy.AppContainer

class MyApplication: Application() {
    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer()
    }
}