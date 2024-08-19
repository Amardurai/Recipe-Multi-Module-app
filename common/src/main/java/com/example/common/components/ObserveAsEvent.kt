package com.example.common.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow

@Composable
fun <T> ObserveAsEvent(
    flow: Flow<T>,
    lifeCycle: Lifecycle.State = Lifecycle.State.STARTED,
    onEvent: (T) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(flow) {
        lifecycleOwner.repeatOnLifecycle(lifeCycle) {
            flow.collect(onEvent)
        }
    }
}