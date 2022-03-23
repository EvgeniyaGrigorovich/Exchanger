package com.devgenius.exchanger.utils

import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

abstract class BaseViewStateReducer<State : Any>(private var state: State) {

    private val lock: ReentrantReadWriteLock = ReentrantReadWriteLock()

    fun currentState(): State = lock.read { state }

    protected fun modify(applyModifier: State.() -> State): State = state.applyModifier().also (::updateState)

    private fun updateState(newState: State): Unit = lock.write {
        state = newState
    }
}