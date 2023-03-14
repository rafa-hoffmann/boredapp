plugins {
    id("boredapp.android.library")
    id("boredapp.android.library.jacoco")
    id("boredapp.android.hilt")
    id("kotlinx-serialization")
}

android {
    namespace = "com.sonder.boredapp.core.data"
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:database"))
    implementation(project(":core:model"))
    implementation(project(":core:network"))

    implementation(libs.androidx.core.ktx)

    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.play.services)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.play.services.location)
}
