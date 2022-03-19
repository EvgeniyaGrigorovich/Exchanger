package com.devgenius.exchangerdi.app

import com.devgenius.exchangerdi.modules.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ConverterModule::class,
        NetworkModule::class,
        ApiModule::class,
        DatabaseModule::class]
)
interface AppComponent {

    /**
     * Провайтит [MainViewModelFactory]
     */
    fun provideViewModelFactory(): MainViewModelFactory

}