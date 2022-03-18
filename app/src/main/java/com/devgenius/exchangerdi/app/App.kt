package com.devgenius.exchangerdi.app

import android.app.Application

class App : Application() {

    /**
     * Создание app component
     */
    val appComponent: AppComponent = DaggerAppComponent.create()
}