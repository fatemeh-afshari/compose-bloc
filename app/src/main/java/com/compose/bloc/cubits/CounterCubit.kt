package com.compose.bloc.cubits

import com.compose.bloc.core.Cubit
import kotlinx.coroutines.launch

class CounterCubit : Cubit<Int>(0) {
    fun increment() {
        blocScope.launch { emit(state + 1) }
    }
}
