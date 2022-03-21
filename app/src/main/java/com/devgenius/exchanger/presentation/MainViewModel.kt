package com.devgenius.exchanger.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.devgenius.exchanger.R
import com.devgenius.exchanger.domain.action.MainScreenAction
import com.devgenius.exchanger.domain.common.base.BaseResult
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
    private val saveCurrencyToFavouritesUseCase: SaveCurrencyToFavouritesUseCase,
) : AndroidViewModel(Application()) {

    private val states =
        MutableStateFlow<MainScreenViewState>(
            MainScreenViewState(
                globalState = MainScreenGlobalState.INIT,
                internalState = MainScreenInternalState(
                    isAllCurrenciesShown = true,
                    isFavouritesShown = false,
                    isSorted = SortedState.Default
                )
            )
        )
    val mainScreenState: StateFlow<MainScreenViewState> = states

    private val rates =
        MutableStateFlow<List<Rate>>(listOf())
    val mainScreenRates: StateFlow<List<Rate>> = rates

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
            is MainScreenAction.OpenMainScreen -> getAllCurrencies()
            is MainScreenAction.ChangeSortedState -> changeSort(
                action.newSortedState
            )
            is MainScreenAction.SaveToFavourites -> saveToFavourite(action.rate)

        }
    }

    private fun getFavouriteCurrencies() {
        viewModelScope.launch {
            getFavouriteCurrenciesUseCase.getFromLocal()
                .collect { resultRate ->
                    getFavouriteCurrenciesUseCase.getFromRemote(
                        resultRate
                    ).onStart {
                        setLoading()
                    }.catch { exception ->
                        hideLoading()
                        showMessage(exception.message.toString())
                    }.collect { resultCurrency ->
                        hideLoading()
                        when (resultCurrency) {
                            is BaseResult.Success -> {
                                rates.value = resultCurrency.data.rates
                            }
                            is BaseResult.Error -> {
                                rates.value = resultRate
                                showMessage(
                                    resource = R.string.cannot_download_favourites
                                )
                            }
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
                    showMessage(exception.message)
                }
                .collect { result ->
                    hideLoading()
                    when (result) {
                        is BaseResult.Success -> {
                            rates.value = result.data.rates
                        }
                        is BaseResult.Error -> {
                            showMessage(resource = R.string.cannot_download_currency)
                        }
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

    private fun changeSort(newSortedState: SortedState) {
        viewModelScope.launch {
            val newList = mutableListOf<Rate>()
            when (newSortedState) {
                is SortedState.ByAlphabet -> if (newSortedState.isAscending) {
                    newList.addAll(rates.value.sortedByDescending {
                        it.currency
                    }
                    )

                } else {
                    newList.addAll(
                        rates.value.sortedByDescending {
                            it.currency
                        }.reversed()
                    )
                }
                is SortedState.ByValue -> if (newSortedState.isAscending) {
                    newList.addAll(
                        rates.value.sortedByDescending {
                            it.value
                        }.reversed()
                    )
                } else {
                    newList.addAll(
                        rates.value.sortedByDescending {
                            it.value
                        }
                    )
                }
            }
            rates.emit(newList)
        }
    }

    private fun showMessage(message: String? = null, resource: Int? = null) {
        if (!message.isNullOrEmpty()) {
            states.value = states.value.copy(
                globalState = MainScreenGlobalState.SHOW_MESSAGE(message)
            )
        } else if (resource != null) {
            states.value = states.value.copy(
                globalState = MainScreenGlobalState.SHOW_MESSAGE(
                    getApplication<Application>().resources.getString(resource)
                )
            )
        }
    }
}
