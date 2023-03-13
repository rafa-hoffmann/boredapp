package com.sonder.boredapp.network.di.model

import kotlinx.serialization.Serializable

@Serializable
data class ActivityResponse(
    val activity: String,
    val accessibility: Float,
    val type: String,
    val participants: Int,
    val price: Float,
    val key: String
)
