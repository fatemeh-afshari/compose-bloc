package com.compose.bloc.core

/**
 *  A [Change] represents the change from one [State] to another.
 *  A [Change] consists of the [currentState] and [nextState].
 */
data class Change<State>(val currentState: State, val nextState: State) {
    override fun toString(): String {
        return "Change { currentState: $currentState, nextState: $nextState }"
    }
}
