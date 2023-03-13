pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "boredapp"
include(":app")
include(":core")
include(":core:common")
include(":core:network")
include(":core:model")
include(":core:data")
include(":feature")
include(":feature:activity-list")
