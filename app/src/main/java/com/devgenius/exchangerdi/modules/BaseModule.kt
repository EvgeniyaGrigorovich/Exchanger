package com.devgenius.exchangerdi.modules

import com.devgenius.exchanger.presentation.MainScreenViewStateReducer
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class BaseModule {

    /**
     * Предоставляет [MainScreenViewStateReducer] в граф зависимостей
     */
    @Provides
    @Singleton
    fun provideMainScreenReducer(): MainScreenViewStateReducer {
        return MainScreenViewStateReducer()
    }
}