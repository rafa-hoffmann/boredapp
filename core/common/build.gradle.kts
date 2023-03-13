plugins {
    id("boredapp.android.library")
    id("boredapp.android.library.jacoco")
    id("boredapp.android.hilt")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    buildFeatures {
        buildConfig = true
    }
    namespace = "com.sonder.boredapp.core.common"
}

secrets {
    defaultPropertiesFileName = "secrets.defaults.properties"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.glide)
    annotationProcessor(libs.glide.compiler)
}
