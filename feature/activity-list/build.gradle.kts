plugins {
    id("boredapp.android.feature")
    id("boredapp.android.library.jacoco")
}

android {
    namespace = "com.sonder.boredapp.feature.activity_list"

    viewBinding {
        enable = true
    }
}
