package io.github.soleworks.validity.samples

import java.util.concurrent.Executors
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher

object Threads {
    val caller: CoroutineDispatcher = dispatcher()

    val other: CoroutineDispatcher = dispatcher()

    private fun dispatcher(): CoroutineDispatcher = Executors
        .newSingleThreadExecutor { task -> Thread(task).apply { isDaemon = true } }
        .asCoroutineDispatcher()
}
