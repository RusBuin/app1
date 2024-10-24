package com.example.siet.presentation

import android.app.Application
import com.example.siet.di.AppComponent
import com.example.siet.di.AppModule
import com.example.siet.di.DaggerAppComponent
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App : Application() {
}