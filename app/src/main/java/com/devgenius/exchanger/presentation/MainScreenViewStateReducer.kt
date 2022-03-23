package com.devgenius.exchanger.presentation

import com.devgenius.exchanger.presentation.states.MainScreenGlobalState
import com.devgenius.exchanger.presentation.states.MainScreenInternalState
import com.devgenius.exchanger.presentation.states.MainScreenViewState
import com.devgenius.exchanger.presentation.states.SortedState
import com.devgenius.exchanger.utils.BaseViewStateReducer

class MainScreenViewStateReducer(
    initState: MainScreenViewState = initViewState()
) : BaseViewStateReducer<MainScreenViewState>(initState) {

    fun setLoadingState(): MainScreenViewState = modify {
        copy(globalState = MainScreenGlobalState.LOADING(true))
    }

    fun hideLoading(): MainScreenViewState = modify {
        copy(
            globalState = MainScreenGlobalState.LOADING(false)
        )
    }

    fun setupFavouritesState(): MainScreenViewState =
        modify {
            copy(
                internalState = MainScreenInternalState(
                    isFavouriteScreen = true,
                    isSorted = currentState().internalState.isSorted,
                    currency = currentState().internalState.currency
                )
            )
        }

    fun showAllCurrencyState(): MainScreenViewState = modify {
        copy(
            internalState = MainScreenInternalState(
                currency = currentState().internalState.currency,
                isSorted = currentState().internalState.isSorted,
                isFavouriteScreen = false
            )
        )
    }

    fun saveToFavouriteState(): MainScreenViewState = modify {
        copy(
            globalState = MainScreenGlobalState.SHOW_MESSAGE("Добавлено")
        )
    }

    fun setSelectedSortState(newSortedState: SortedState): MainScreenViewState = modify {
        copy(
            internalState = MainScreenInternalState(
                isFavouriteScreen = currentState().internalState.isFavouriteScreen,
                isSorted = newSortedState,
                currency = currentState().internalState.currency
            )
        )
    }

    fun showMessageState(message: String): MainScreenViewState = modify {
       copy(
            globalState = MainScreenGlobalState.SHOW_MESSAGE(message)
        )
    }

    fun changeCurrency(currency: String): MainScreenViewState = modify {
        copy(
            internalState = MainScreenInternalState(
                isFavouriteScreen = currentState().internalState.isFavouriteScreen,
                isSorted = currentState().internalState.isSorted,
                currency = currency
            )
        )
    }

    fun refresh(isRefreshing: Boolean): MainScreenViewState = modify {
        copy(
            globalState = MainScreenGlobalState.REFRESHING(isRefreshing),
            internalState = MainScreenInternalState(
                isFavouriteScreen = currentState().internalState.isFavouriteScreen,
                isSorted = currentState().internalState.isSorted,
                currency = currentState().internalState.currency
            )

        )
    }
}

private fun initViewState(): MainScreenViewState = MainScreenViewState(
    globalState = MainScreenGlobalState.INIT,
    internalState = MainScreenInternalState(
        isSorted = SortedState.ByAlphabet(false),
        currency = "EUR",
        isFavouriteScreen = false
    )
)

