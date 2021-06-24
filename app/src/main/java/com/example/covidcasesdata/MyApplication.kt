package com.example.covidcasesdata

import android.app.Application
import android.content.SharedPreferences

class MyApplication: Application() {
    companion object{
        lateinit var sharedPreferences: SharedPreferences
    }

    override fun onCreate() {
        sharedPreferences = getSharedPreferences("my_shared_preferences", MODE_PRIVATE)
        super.onCreate()
    }
}