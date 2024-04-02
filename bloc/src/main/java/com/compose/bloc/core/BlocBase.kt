package com.compose.bloc.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

/**
 * This class contains common functionality for [Bloc] and [Cubit].
 *
 * @param initial The initial [State]
 */
abstract class BlocBase<State>(initial: State) : CoroutineScope {
    protected val job = SupervisorJob()

    protected val scope = CoroutineScope(job)

    protected val mutableChangeFlow = MutableStateFlow<Change<State>?>(null)
        .apply {
            scope.launch {
                collect {
                    it?.let { onChange(it) }
                }
            }
        }
     val stateFlow = mutableChangeFlow.filterNotNull().map { it.nextState }
    var currentState: State = initial
        private set

    @Deprecated("Using the context directly is discouraged.")
    override val coroutineContext: CoroutineContext
        get() = scope.coroutineContext

    fun onCleared() {
        scope.coroutineContext.cancelChildren()
    }


    /**
     * Collect flows in a safe and error-friendly manner.
     */
    protected suspend fun <T> Flow<T>.collectSafely(
        onException: (Throwable) -> Unit = {},
        collector: (T) -> Unit,
    ) {
        withContext(scope.coroutineContext) {
            this@collectSafely.catch { onException(it) }.collect {
                collector(it)
            }
        }
    }

    /**
     * Runs the specified task on IO. It will return a result, as it will catch all errors
     */
    suspend fun <X> execute(
        task: suspend () -> X,
    ): Result<X> {
        return withContext(scope.coroutineContext) {
            runCatching {
                task()
            }
        }
    }


    init {
        Bloc.observer.onCreate(this)
    }


    open fun onChange(change: Change<State>) {
        Bloc.observer.onChange(this, change)
        this.currentState = change.nextState
    }

}
