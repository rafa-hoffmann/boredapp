package com.sonder.boredapp.model.data

data class ActivityResource(
    val activity: String,
    val accessibility: Float,
    val type: ActivityType,
    val participants: Int,
    val price: Float,
    val key: String
)