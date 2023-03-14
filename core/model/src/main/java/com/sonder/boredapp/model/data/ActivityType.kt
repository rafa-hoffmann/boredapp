package com.sonder.boredapp.model.data

enum class ActivityType(val apiName: String) {
    EDUCATION("education"),
    RECREATIONAL("recreational"),
    SOCIAL("social"),
    DIY("diy"),
    CHARITY("charity"),
    COOKING("cooking"),
    RELAXATION("relaxation"),
    MUSIC("music"),
    BUSYWORK("busywork")
}
fun String.asActivityResourceType() = ActivityType.values()
    .first { type -> type.apiName == this }
