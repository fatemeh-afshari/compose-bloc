package com.compose.bloc

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.compose.bloc.compose.BlocComposer
import com.compose.bloc.compose.BlocSelector
import com.compose.bloc.core.BlocBase
import com.compose.bloc.blocs.CounterBloc
import com.compose.bloc.blocs.CounterEvent
import com.compose.bloc.cubits.CounterCubit
import com.compose.bloc.ui.theme.ComposeBLoCTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeBLoCTheme {
                BlocCounter()
            }
        }
    }
}

/**
 * Creates a Counter based on [CounterBloc]
 */
@Composable
fun BlocCounter(
    bloc: CounterBloc = rememberSaveable { CounterBloc(0) },
) {
    CounterBase(
        bloc,
        onIncrement = {
            bloc.add(CounterEvent.Increment)
        }
    )
}

/**
 * Creates a Counter based on [CounterCubit]
 */
@Composable
fun CubitCounter(
    cubit: CounterCubit = remember { CounterCubit() },
) {
    CounterBase(
        cubit,
        onIncrement = { cubit.increment() }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CounterBase(
    bloc: BlocBase<Int>,
    onIncrement: () -> Unit,
) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onIncrement) {
                Icon(Icons.Default.Add, "Add")
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            BlocComposer(bloc = bloc) {
                Text("$it", style = MaterialTheme.typography.headlineLarge)
            }
        }
    }
}

/**
 * Creates a Counter based on [CounterBloc], using [BlocSelector] to transform each state into a String to display.
 */

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BlocSelectorCounter(
    bloc: CounterBloc = rememberSaveable { CounterBloc(0) },
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    bloc.add(CounterEvent.Increment)
                }
            ) {
                Icon(Icons.Default.Add, "Add")
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            BlocSelector(
                bloc = bloc,
                selector = {
                    "The counter is $it"
                }
            ) {
                Text(it, style = MaterialTheme.typography.headlineLarge)
            }
        }
    }
}

@Preview("BlocCounter")
@Composable

fun BlocCounterPreview() {
    ComposeBLoCTheme {
        BlocCounter()
    }
}

@Preview("CubitCounter")
@Composable

fun CubitCounterPreview() {
    ComposeBLoCTheme {
        CubitCounter()
    }
}

@Preview("BlocSelectorCounter")
@Composable

fun BlocSelectorCounterPreview() {
    ComposeBLoCTheme {
        BlocSelectorCounter()
    }
}
