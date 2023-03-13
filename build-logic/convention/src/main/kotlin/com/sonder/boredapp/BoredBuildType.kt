package com.sonder.boredapp

@Suppress("unused")
enum class BoredBuildType(val applicationIdSuffix: String? = null) {
    DEBUG(".debug"),
    RELEASE
}
