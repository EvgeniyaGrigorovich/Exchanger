package com.devgenius.exchangerdi.app

import com.devgenius.exchangerdi.modules.ApiModule
import com.devgenius.exchangerdi.modules.ConverterModule
import com.devgenius.exchangerdi.modules.MainViewModelFactory
import com.devgenius.exchangerdi.modules.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ConverterModule::class, NetworkModule::class, ApiModule::class])
interface AppComponent {

    /**
     * Провайтит [MainViewModelFactory]
     */
    fun provideViewModelFactory(): MainViewModelFactory
}