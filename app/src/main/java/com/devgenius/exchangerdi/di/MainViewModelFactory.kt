package com.devgenius.exchangerdi.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devgenius.exchanger.presentation.MainViewModel
import javax.inject.Inject
import javax.inject.Provider


class MainViewModelFactory @Inject constructor(
    viewModelProvider: Provider<MainViewModel>
) : ViewModelProvider.Factory {

    private val provides = mapOf<Class<*>, Provider<out ViewModel>>(
        MainViewModel::class.java to viewModelProvider
    )


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return provides[modelClass]?.get() as T
    }
}