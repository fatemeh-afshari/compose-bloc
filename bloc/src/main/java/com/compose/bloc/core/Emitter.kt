package com.compose.bloc.core

import kotlinx.coroutines.flow.Flow

/**
 * Interface which can be implemented on any object which can [emit] a [State] or a [SideEffect]
 */
interface Emitter<State> {
    /**
     * Emit a new [State]
     */
    suspend fun emit(state: State)

    /**
     * Subscribes to the provided [states] and emit the states
     */
    suspend fun emitEach(states: Flow<State>)
}
