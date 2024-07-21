pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "Recipe-Multi-Module-app"
include(":app")
include(":common")
include(":feature")
include(":feature:search:ui")
include(":feature:search:domain")
include(":feature:search:data")
include(":media_player")
include(":build-logic")
include(":build-logic:convention")
include(":build-logic")
