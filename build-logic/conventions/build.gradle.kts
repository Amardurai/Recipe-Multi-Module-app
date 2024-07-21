import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  `kotlin-dsl`
}

group = "com.anna.greeneats.build_logic.conventions"

java{
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
  kotlinOptions {
    jvmTarget = JavaVersion.VERSION_17.toString()
  }
}

sourceSets{
  main{
    java.srcDirs("src/main/java", "src/main/kotlin")
  }
}

dependencies {
  compileOnly(libs.android.gradle.plugin)
  compileOnly(libs.android.tools.common)
  compileOnly(libs.kotlin.gradle.plugin)
  compileOnly(libs.ksp.gradle.plugin)
  compileOnly(libs.firebase.crashlytics.gradle.plugin)
  compileOnly(libs.firebase.performance.gradle.plugin)
}

gradlePlugin{
  plugins{
    register("androidApplicationConvention") {
      id = "greeneats.convention.application"
      implementationClass = "AndroidApplicationConventionPlugin"
    }

    register("androidApplicationCompose") {
      id = "greeneats.convention.compose.application"
      implementationClass = "AndroidApplicationComposePlugin"
    }

    register("androidLibraryConvention") {
      id = "greeneats.convention.library"
      implementationClass = "AndroidLibraryConventionPlugin"
    }

    register("androidLibraryCompose") {
      id = "greeneats.convention.compose.library"
      implementationClass = "AndroidLibraryComposePlugin"
    }

    register("androidFeatureConvention") {
      id = "greeneats.convention.feature"
      implementationClass = "AndroidFeatureConventionPlugin"
    }

    register("androidHiltConvention") {
      id = "greeneats.convention.hilt"
      implementationClass = "AndroidHiltConventionPlugin"
    }

    register("androidDataConvention") {
      id = "greeneats.convention.data"
      implementationClass = "AndroidDataConventionPlugin"
    }

    register("androidApplicationFirebase") {
      id = "greeneats.convention.firebase"
      implementationClass = "AndroidApplicationFirebasePlugin"
    }

    register("androidLintConvention") {
      id = "greeneats.convention.lint"
      implementationClass = "AndroidLintConventionPlugin"
    }

    register("androidTestConvention") {
      id = "greeneats.convention.test"
      implementationClass = "AndroidTestConventionPlugin"
    }
  }
}

