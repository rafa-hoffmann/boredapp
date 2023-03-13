import com.sonder.boredapp.BoredBuildType

plugins {
    id("boredapp.android.application")
    id("boredapp.android.application.jacoco")
    id("boredapp.android.hilt")
    id("jacoco")
}

android {
    defaultConfig {
        applicationId = "com.sonder.boredapp"
        versionCode = 1
        versionName = "0.0.1"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        val debug by getting {
            applicationIdSuffix = BoredBuildType.DEBUG.applicationIdSuffix
        }
        val release by getting {
            isMinifyEnabled = true
            applicationIdSuffix = BoredBuildType.RELEASE.applicationIdSuffix
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    viewBinding {
        enable = true
    }

    namespace = "com.sonder.boredapp"
}

dependencies {
    implementation(project(":feature:activity-list"))

    implementation(project(":core:common"))
    implementation(project(":core:data"))
    implementation(project(":core:model"))

    androidTestImplementation(project(":core:network"))
    androidTestImplementation(kotlin("test"))

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.material)
}
