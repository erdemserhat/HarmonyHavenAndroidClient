pluginManagement {
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

rootProject.name = "Harmony Haven"
include(":app")
include(":core")
include(":feature")
include(":core:model")
include(":feature:auth")
include(":feature:onboarding")
include(":feature:home")
include(":feature:messages")
include(":feature:profile")
include(":core:network")
include(":core:designsystem")
include(":core:domain")
include(":core:ui")
include(":core:data")
include(":core:notifications")
