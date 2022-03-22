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
    private val saveCurrencyToFavouritesUseCase: SaveCurrencyToFavouritesUseCase,
    private val symbolsCurrencyUseCase: GetAllCurrencySymbolsUseCase
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

    private val states =
        MutableStateFlow<MainScreenViewState>(
            MainScreenViewState(
                globalState = MainScreenGlobalState.INIT,
                internalState = MainScreenInternalState(
                    isSorted = SortedState.ByAlphabet(false),
                    currency = DEFAULT_CURRENCY,
                    isFavouriteScreen = false
                )
            )
        )
    val mainScreenState: StateFlow<MainScreenViewState> = states

    private val rates =
        MutableStateFlow<List<Rate>>(listOf())
    val mainScreenRates: StateFlow<List<Rate>> = rates

    private val symbols = MutableStateFlow<List<String>>(listOf())
    val mainScreenSymbol: StateFlow<List<String>> = symbols

    private fun setLoading() {
        states.value = states.value.copy(
            globalState = MainScreenGlobalState.LOADING(true)
        )
    }

    private fun hideLoading() {
        states.value = states.value.copy(
            globalState = MainScreenGlobalState.LOADING(false)
        )
    }

    fun executeAction(action: MainScreenAction) {
        when (action) {
            is MainScreenAction.OpenFavouritesScreen -> getFavouriteCurrencies()
            is MainScreenAction.OpenMainScreen -> getAllCurrencies(states.value.internalState.currency)
            is MainScreenAction.ChangeSortedState -> setSelectedSort(
                action.newSortedState,
                rates.value
            )
            is MainScreenAction.SaveToFavourites -> saveToFavourite(action.rate)
            is MainScreenAction.ChangeCurrency -> if (states.value.internalState.isFavouriteScreen) {
                getFavouriteCurrencies()
            } else {
                getAllCurrencies(action.currency)
            }
        }
    }

    private fun getFavouriteCurrencies() {
        states.value = states.value.copy(
            internalState = MainScreenInternalState(
                isFavouriteScreen = true,
                isSorted = states.value.internalState.isSorted,
                currency = states.value.internalState.currency
            )
        )
        viewModelScope.launch {
            getFavouriteCurrenciesUseCase.getFromLocal()
                .collect { resultRate ->
                    getFavouriteCurrenciesUseCase.getFromRemote(
                        base = states.value.internalState.currency,
                        symbols = resultRate
                    ).onStart {
                        setLoading()
                    }.catch { exception ->
                        hideLoading()
                        showMessage(exception.message.toString())
                    }.collect { resultCurrency ->
                        hideLoading()
                        when (resultCurrency) {
                            is BaseResult.Success -> {
                                setSelectedSort(
                                    states.value.internalState.isSorted,
                                    resultCurrency.data.rates
                                )
                            }
                            is BaseResult.Error -> {
                                setSelectedSort(states.value.internalState.isSorted, resultRate)
//                                showMessage(
////                                    resource = R.string.cannot_download_favourites
//                                )
                            }
                        }
                    }
                }

        }
    }

    private fun getAllCurrencies(base: String) {
        states.value = states.value.copy(
            internalState = MainScreenInternalState(
                currency = base,
                isSorted = states.value.internalState.isSorted,
                isFavouriteScreen = false
            )
        )
        viewModelScope.launch {
            getAllCurrenciesUseCase.invoke(
                base = base
            ).onStart {
                setLoading()
            }
                .catch { exception ->
                    hideLoading()
                    showMessage(exception.message.toString())
                }
                .collect { result ->
                    hideLoading()
                    when (result) {
                        is BaseResult.Success -> {
                            setSelectedSort(states.value.internalState.isSorted, result.data.rates)
                        }

//                        is BaseResult.Error -> {
//                            showMessage(resource = R.string.cannot_download_currency)
//                        }
                    }
                }
        }
    }

    private fun saveToFavourite(rate: Rate) {
        viewModelScope.launch {
            saveCurrencyToFavouritesUseCase.invoke(rate)
        }

        states.value = states.value.copy(
            globalState = MainScreenGlobalState.SHOW_MESSAGE("Добавлено")
        )
    }

    private fun setSelectedSort(newSortedState: SortedState, ratesList: List<Rate>) {
        states.value = states.value.copy(
            internalState = MainScreenInternalState(
                isFavouriteScreen = states.value.internalState.isFavouriteScreen,
                isSorted = newSortedState,
                currency = states.value.internalState.currency
            )
        )

        viewModelScope.launch {
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
        states.value = states.value.copy(
            globalState = MainScreenGlobalState.SHOW_MESSAGE(message)
        )
    }

    companion object {
        private const val DEFAULT_CURRENCY = "EUR"
    }
}
