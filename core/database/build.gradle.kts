@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("boredapp.android.library")
    id("boredapp.android.library.jacoco")
    id("boredapp.android.hilt")
    id("boredapp.android.room")
    id("kotlinx-serialization")
}

android {
    namespace = "com.sonder.boredapp.core.database"
}

dependencies {
    implementation(project(":core:model"))

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
}
