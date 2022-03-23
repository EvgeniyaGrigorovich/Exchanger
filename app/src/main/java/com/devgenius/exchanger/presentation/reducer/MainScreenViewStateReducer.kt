package com.devgenius.exchanger.presentation.reducer

import com.devgenius.exchanger.domain.entity.Rate
import com.devgenius.exchanger.presentation.states.MainScreenGlobalState
import com.devgenius.exchanger.presentation.states.MainScreenInternalState
import com.devgenius.exchanger.presentation.states.MainScreenViewState
import com.devgenius.exchanger.presentation.states.SortedState
import com.devgenius.exchanger.utils.BaseViewStateReducer

class MainScreenViewStateReducer(
    initState: MainScreenViewState = initViewState()
) : BaseViewStateReducer<MainScreenViewState>(initState) {

    /**
     * Устанавливает состояние начала загрузки
     */
    fun setLoadingState(): MainScreenViewState = modify {
        copy(globalState = MainScreenGlobalState.LOADING(true))
    }

    /**
     * Устанавливает состояние окончания загрузки
     */
    fun hideLoading(): MainScreenViewState = modify {
        copy(
            globalState = MainScreenGlobalState.LOADING(false)
        )
    }

    /**
     * Устанавливает состояние нахождения во вкладке и сзбранным
     */
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

    /**
     * Устанавливает состояние нахождения на экране со всеми валютами
     */
    fun showAllCurrencyState(): MainScreenViewState = modify {
        copy(
            internalState = MainScreenInternalState(
                currency = currentState().internalState.currency,
                isSorted = currentState().internalState.isSorted,
                isFavouriteScreen = false
            )
        )
    }

    /**
     * Устанавливает состояние показа успешного добавления в избранное
     */
    fun saveToFavouriteState(): MainScreenViewState = modify {
        copy(
            globalState = MainScreenGlobalState.SHOW_MESSAGE("Добавлено")
        )
    }

    /**
     * Устанавливает состояние сортировки
     */
    fun setSelectedSortState(newSortedState: SortedState): MainScreenViewState = modify {
        copy(
            internalState = MainScreenInternalState(
                isFavouriteScreen = currentState().internalState.isFavouriteScreen,
                isSorted = newSortedState,
                currency = currentState().internalState.currency
            )
        )
    }

    /**
     * Устанавливает состояние показа сообщения
     */
    fun showMessageState(message: String): MainScreenViewState = modify {
       copy(
            globalState = MainScreenGlobalState.SHOW_MESSAGE(message)
        )
    }

    /**
     * Изменяет состояние выбранной валюты
     */
    fun changeCurrency(currency: String): MainScreenViewState = modify {
        copy(
            internalState = MainScreenInternalState(
                isFavouriteScreen = currentState().internalState.isFavouriteScreen,
                isSorted = currentState().internalState.isSorted,
                currency = currency
            )
        )
    }

    /**
     * Устанавливает состояние установки и окончания обновления экрана
     */
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

    /**
     * Устанавливает состояние отображения дилогового окна
     */
    fun showDialogToDeleteRate(rate: Rate): MainScreenViewState = modify {
        copy(
            globalState = MainScreenGlobalState.SHOW_DIALOG_TO_DELETE(rate),
            internalState = MainScreenInternalState(
                isFavouriteScreen = currentState().internalState.isFavouriteScreen,
                isSorted = currentState().internalState.isSorted,
                currency = currentState().internalState.currency,
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

