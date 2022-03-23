package com.devgenius.exchanger.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.devgenius.exchanger.domain.action.MainScreenAction
import com.devgenius.exchanger.domain.common.SymbolsResult
import com.devgenius.exchanger.domain.common.BaseResult
import com.devgenius.exchanger.domain.entity.Rate
import com.devgenius.exchanger.domain.usecase.GetAllCurrenciesUseCase
import com.devgenius.exchanger.domain.usecase.GetAllCurrencySymbolsUseCase
import com.devgenius.exchanger.domain.usecase.GetFavouriteCurrenciesUseCase
import com.devgenius.exchanger.domain.usecase.SaveCurrencyToFavouritesUseCase
import com.devgenius.exchanger.presentation.states.MainScreenInternalState
import com.devgenius.exchanger.presentation.states.MainScreenViewState
import com.devgenius.exchanger.presentation.states.SortedState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getAllCurrenciesUseCase: GetAllCurrenciesUseCase,
    private val getFavouriteCurrenciesUseCase: GetFavouriteCurrenciesUseCase,
    private val saveCurrencyToFavouritesUseCase: SaveCurrencyToFavouritesUseCase,
    private val symbolsCurrencyUseCase: GetAllCurrencySymbolsUseCase,
    private val stateReducer: MainScreenViewStateReducer
) : AndroidViewModel(Application()) {

    init {
        viewModelScope.launch {
            symbolsCurrencyUseCase.getSymbols().collect { result ->
                when (result) {
                    is SymbolsResult.Success -> symbols.value = result.data
                    is SymbolsResult.Error -> symbols.value = listOf(DEFAULT_CURRENCY)
                }
            }
        }
    }

    private val states = MutableStateFlow(stateReducer.currentState())
    val mainScreenState: StateFlow<MainScreenViewState> = states

    private val rates = MutableStateFlow<List<Rate>>(listOf())
    val mainScreenRates: StateFlow<List<Rate>> = rates

    private val symbols = MutableStateFlow<List<String>>(listOf())
    val mainScreenSymbol: StateFlow<List<String>> = symbols

    fun executeAction(action: MainScreenAction) {
        when (action) {
            is MainScreenAction.OpenFavouritesScreen -> getFavouriteCurrencies()
            is MainScreenAction.OpenMainScreen -> getAllCurrencies()
            is MainScreenAction.ChangeSortedState -> setSelectedSort(
                action.newSortedState,
                rates.value
            )
            is MainScreenAction.SaveToFavourites -> saveToFavourite(action.rate)
            is MainScreenAction.ChangeCurrency -> changeCurrency(action.currency)
        }
    }

    private fun changeCurrency(currency: String) {
        states.value = stateReducer.changeCurrency(currency)
        if (states.value.internalState.isFavouriteScreen) {
            getFavouriteCurrencies()
        } else {
            getAllCurrencies()
        }
    }

    private fun getFavouriteCurrencies() {
        viewModelScope.launch {
            states.emit(stateReducer.setupFavouritesState())

            getFavouriteCurrenciesUseCase.getFromLocal()
                .collect { resultRate ->
                    getFavouriteCurrenciesUseCase.getFromRemote(
                        base = states.value.internalState.currency,
                        symbols = resultRate
                    ).onStart {
                        states.value = stateReducer.setLoadingState()
                    }.catch { exception ->
                        states.value = stateReducer.hideLoading()
                        showMessage(exception.message.toString())
                    }.collect { resultCurrency ->
                        states.value = stateReducer.hideLoading()
                        when (resultCurrency) {
                            is BaseResult.Success -> {
                                setSelectedSort(
                                    states.value.internalState.isSorted,
                                    resultCurrency.data.rates
                                )
                            }
                            is BaseResult.Error -> {
                                setSelectedSort(states.value.internalState.isSorted, resultRate)
                                showMessage("Cannot load new data")
                            }
                        }
                    }
                }

        }
    }

    private fun getAllCurrencies() {
        viewModelScope.launch {
            states.emit(stateReducer.showAllCurrencyState())

            getAllCurrenciesUseCase.invoke(
                base = states.value.internalState.currency
            ).onStart {
                states.value = stateReducer.setLoadingState()
            }
                .catch { exception ->
                    stateReducer.hideLoading()
                    showMessage(exception.message.toString())
                }
                .collect { result ->
                    states.value = stateReducer.hideLoading()
                    when (result) {
                        is BaseResult.Success -> {
                            setSelectedSort(states.value.internalState.isSorted, result.data.rates)
                        }
                        is BaseResult.Error -> {
                            showMessage("Cannot loading data")
                        }
                    }
                }
        }
    }

    private fun saveToFavourite(rate: Rate) {
        viewModelScope.launch {
            states.emit(stateReducer.saveToFavouriteState())
            saveCurrencyToFavouritesUseCase.invoke(rate)
        }
    }

    private fun setSelectedSort(newSortedState: SortedState, ratesList: List<Rate>) {
        viewModelScope.launch {
            states.emit(stateReducer.setSelectedSortState(newSortedState))

            val newList = mutableListOf<Rate>()
            when (newSortedState) {
                is SortedState.ByAlphabet -> if (newSortedState.isAscending) {
                    newList.addAll(ratesList.sortedByDescending {
                        it.currency
                    }
                    )
                } else {
                    newList.addAll(
                        ratesList.sortedByDescending {
                            it.currency
                        }.reversed()
                    )
                }
                is SortedState.ByValue -> if (newSortedState.isAscending) {
                    newList.addAll(
                        ratesList.sortedByDescending {
                            it.value
                        }.reversed()
                    )
                } else {
                    newList.addAll(
                        ratesList.sortedByDescending {
                            it.value
                        }
                    )
                }
            }
            rates.emit(newList)
        }
    }

    private fun showMessage(message: String) {
        states.value = stateReducer.showMessageState(message)
    }

    companion object {
        private const val DEFAULT_CURRENCY = "EUR"
    }
}
