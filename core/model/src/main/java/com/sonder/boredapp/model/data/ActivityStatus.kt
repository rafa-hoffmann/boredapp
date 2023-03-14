package com.sonder.boredapp.model.data

import kotlinx.datetime.Instant

sealed interface ActivityStatus {
    object Withdrawal : ActivityStatus

    data class Finished(
        val startTime: Instant,
        val finishTime: Instant
    ) : ActivityStatus

    data class InProgress(
        val startTime: Instant
    ) : ActivityStatus
}