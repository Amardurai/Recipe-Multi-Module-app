package com.anna.greeneats.helpers

import com.android.build.api.dsl.CommonExtension
import com.anna.greeneats.constants.AndroidSdk
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Configure base Kotlin with Android options
 */
internal fun Project.configureKotlinAndroid(
  commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
  commonExtension.apply {
    compileSdk = AndroidSdk.compileSdk

    defaultConfig {
      minSdk = AndroidSdk.minimumSdk
    }

    compileOptions {
      sourceCompatibility = JavaVersion.VERSION_17
      targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
      buildConfig = true
    }

    configureKotlin()
  }
}

/**
 * Configure base Kotlin options
 */
private fun Project.configureKotlin() {
  tasks.withType<KotlinCompile>().configureEach{
    kotlinOptions{
      val warningsAsErrors: String? by project
      allWarningsAsErrors = warningsAsErrors.toBoolean()
      freeCompilerArgs = freeCompilerArgs + listOf(
        "-opt-in=kotlin.RequiresOptIn",
        "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
        "-opt-in=kotlinx.coroutines.FlowPreview",
        "-opt-in=kotlin.Experimental",
      )
      jvmTarget = JavaVersion.VERSION_17.toString()
    }
  }
}