package com.compose.bloc.blocs

import android.os.Parcelable
import com.compose.bloc.core.Bloc
import kotlinx.parcelize.Parcelize

enum class CounterEvent { Increment, Decrement }

@Parcelize
class CounterBloc(private val initial: Int) : Bloc<CounterEvent, Int>(initial), Parcelable {

    init {
        on<CounterEvent> { event ->
            when (event) {
                CounterEvent.Increment -> emit(currentState + 1)
                CounterEvent.Decrement -> emit(currentState - 1)
            }
        }
    }
}
