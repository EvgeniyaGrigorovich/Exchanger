package com.devgenius.exchanger.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.devgenius.exchanger.domain.action.MainScreenAction
import com.devgenius.exchanger.domain.common.SymbolsResult
import com.devgenius.exchanger.domain.common.BaseResult
import com.devgenius.exchanger.domain.entity.Rate
import com.devgenius.exchanger.domain.usecase.*
import com.devgenius.exchanger.presentation.reducer.MainScreenViewStateReducer
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
    private val deleteFromFavouriteUseCase: DeleteFromFavouriteUseCase,
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
            is MainScreenAction.Refresh -> refreshData()
            is MainScreenAction.LongClickAction -> executeLongClick(action.rate)
            is MainScreenAction.DeleteCurrency -> deleteFromFavourite(action.currency)
        }
    }

    private fun executeLongClick(rate: Rate) {
        if (states.value.internalState.isFavouriteScreen) {
            states.value = stateReducer.showDialogToDeleteRate(rate)
        }
    }

    private fun deleteFromFavourite(currency: Rate) {
        viewModelScope.launch {
            deleteFromFavouriteUseCase.deleteCurrencyFromLocal(currency)
        }
    }

    private fun refreshData() {
        states.value = stateReducer.refresh(true)
        if (states.value.internalState.isFavouriteScreen) {
            getFavouriteCurrencies()
        } else {
            getAllCurrencies()
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
            states.value = stateReducer.setupFavouritesState()

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
                        states.value = stateReducer.refresh(false)
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
            states.value = stateReducer.showAllCurrencyState()

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
                    states.value = stateReducer.refresh(false)
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
            states.value = stateReducer.saveToFavouriteState()
            saveCurrencyToFavouritesUseCase.invoke(rate)
        }
    }

    private fun setSelectedSort(newSortedState: SortedState, ratesList: List<Rate>) {
        viewModelScope.launch {
            states.value = stateReducer.setSelectedSortState(newSortedState)

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
            rates.value = newList
        }
    }

    private fun showMessage(message: String) {
        states.value = stateReducer.showMessageState(message)
    }

    companion object {
        private const val DEFAULT_CURRENCY = "EUR"
    }
}
