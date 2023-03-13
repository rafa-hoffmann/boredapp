package com.sonder.boredapp.common.network

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val boredDispatcher: BoredDispatchers)

enum class BoredDispatchers {
    IO
}
