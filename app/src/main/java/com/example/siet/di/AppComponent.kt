package com.example.siet.di

import com.example.siet.presentation.MainActivity
import dagger.Component


@Component(modules=[AppModule::class, DataModule::class])
interface AppComponent {
        fun inject(mainActivity: MainActivity)
}