package com.compose.bloc.core

/**
 * A [Transition] is the change from one state to another.
 * Consists of the [currentState], an [event], and the [nextState].
 * The [Event] which triggered the current [Transition].
 */
data class Transition<Event, State>(
    val currentState: State,
    val event: Event,
    val nextState: State,
) {
    override fun toString(): String {
        return "Transition {currentState: $currentState, event: $event, nextState: $nextState}"
    }

}
