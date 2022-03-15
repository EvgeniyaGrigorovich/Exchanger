package com.devgenius.exchangerdi.di

import android.app.Application

class App : Application() {

    val appComponent: AppComponent = DaggerAppComponent.create()
}