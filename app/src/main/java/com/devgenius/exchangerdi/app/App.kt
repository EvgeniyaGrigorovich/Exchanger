package com.devgenius.exchangerdi.app

import android.app.Application
import com.devgenius.exchangerdi.modules.DatabaseModule


class App : Application() {

    /**
     * Создание app component
     */
    val appComponent: AppComponent =
        DaggerAppComponent.builder()
            .databaseModule(DatabaseModule(this))
            .build()

}