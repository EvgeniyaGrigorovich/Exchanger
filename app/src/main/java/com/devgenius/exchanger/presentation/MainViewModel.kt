package com.devgenius.exchanger.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devgenius.exchanger.domain.action.MainScreenAction
import com.devgenius.exchanger.domain.base.BaseResult
import com.devgenius.exchanger.domain.entity.Rate
import com.devgenius.exchanger.domain.usecase.GetAllCurrenciesUseCase
import com.devgenius.exchanger.domain.usecase.GetFavouriteCurrenciesUseCase
import com.devgenius.exchanger.domain.usecase.SaveCurrencyToFavouritesUseCase
import com.devgenius.exchanger.presentation.states.MainScreenGlobalState
import com.devgenius.exchanger.presentation.states.MainScreenInternalState
import com.devgenius.exchanger.presentation.states.MainScreenViewState
import com.devgenius.exchanger.presentation.states.SortedState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getAllCurrenciesUseCase: GetAllCurrenciesUseCase,
    private val getFavouriteCurrenciesUseCase: GetFavouriteCurrenciesUseCase,
    private val saveCurrencyToFavouritesUseCase: SaveCurrencyToFavouritesUseCase
) : ViewModel() {

    private val states =
        MutableStateFlow<MainScreenViewState>(
            MainScreenViewState(
                globalState = MainScreenGlobalState.LOADING,
                internalState = MainScreenInternalState(
                    isAllCurrenciesShown = true,
                    isFavouritesShown = false,
                    isSorted = SortedState.Default
                )
            )
        )
    val mainScreenState: StateFlow<MainScreenViewState> = states

    private val rates =
        MutableStateFlow<List<Rate>>(mutableListOf())
    val mainScreenRates: StateFlow<List<Rate>> = rates

    private fun setLoading() {
        states.value = states.value.copy(
            globalState = MainScreenGlobalState.LOADING
        )
    }

    private fun hideLoading() {
        states.value = states.value.copy(
            globalState = MainScreenGlobalState.NORMAL
        )
    }

    fun executeAction(action: MainScreenAction) {
        when (action) {
            is MainScreenAction.OpenFavouritesScreen -> getFavouriteCurrencies()
            is MainScreenAction.OpenMainScreen -> getAllCurrencies()
            is MainScreenAction.ChangeSortedState -> changeSort(
                action.oldSortedState,
                action.newSortedState
            )
            is MainScreenAction.SaveToFavourites -> saveToFavourite(action.rate)

        }
    }

    private fun getFavouriteCurrencies() {
        viewModelScope.launch {
            getFavouriteCurrenciesUseCase.invoke().onStart {
                setLoading()
            }
                .catch { exception ->
                    hideLoading()
                    //сообщение об ошибке
                }
                .collect { result ->
                    when (result) {
                        is BaseResult.Success -> {
                            rates.value = result.data.rates
                        }
                        is BaseResult.Error -> {
                            //сообщение об ошибке
                        }
                    }
                }
        }
    }

    private fun getAllCurrencies() {
        viewModelScope.launch {
            getAllCurrenciesUseCase.invoke().onStart {
                setLoading()
            }
                .catch { exception ->
                    hideLoading()
                    //сообщение об ошибке
                }
                .collect { result ->
                    when (result) {
                        is BaseResult.Success -> {
                            rates.value = result.data.rates
                        }
                        is BaseResult.Error -> {
                            //сообщение об ошибке
                        }
                    }
                }
        }
    }

    private fun saveToFavourite(rate: Rate) {
        viewModelScope.launch {
            saveCurrencyToFavouritesUseCase.invoke(rate)
        }
    }

    private fun changeSort(oldSortedState: SortedState, newSortedState: SortedState) {
        if (oldSortedState == newSortedState) {
            return
        }

        viewModelScope.launch {
            when (newSortedState) {
                is SortedState.ByAlphabet -> if (newSortedState.isAscending) {
                    rates.value.sortedByDescending {
                        it.currency
                    }
                } else {
                    rates.value.sortedByDescending {
                        it.currency
                    }.reversed()
                }

                is SortedState.ByValue -> if (newSortedState.isAscending) {
                    rates.value.sortedByDescending {
                        it.currency
                    }
                } else {
                    rates.value.sortedByDescending {
                        it.currency
                    }.reversed()
                }
            }
        }
    }
}
