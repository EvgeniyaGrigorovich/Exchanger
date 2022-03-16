package com.devgenius.exchangerdi.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ConverterModule::class, NetworkModule::class])
interface AppComponent {

    fun viewModelFactory(): MainViewModelFactory
}