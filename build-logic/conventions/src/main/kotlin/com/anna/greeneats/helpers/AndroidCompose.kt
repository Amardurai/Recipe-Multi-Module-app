package com.anna.greeneats.helpers

import com.android.build.api.dsl.CommonExtension
import com.anna.greeneats.extensions.androidTestImplementation
import com.anna.greeneats.extensions.debugImplementation
import com.anna.greeneats.extensions.implementation
import com.anna.greeneats.extensions.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Configure Compose-specific options
 */
internal fun Project.configureAndroidCompose(
  commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
  commonExtension.apply {
    buildFeatures {
      compose = true
    }

    composeOptions {
      kotlinCompilerExtensionVersion = libs.findVersion("androidxComposeCompiler").get().toString()
    }

    dependencies {
      val bom = libs.findLibrary("androidx.compose.bom").get()
      implementation(platform(bom))
      implementation(libs.findLibrary("androidx.activity.compose").get())
      implementation(libs.findLibrary("androidx.ui").get())
      implementation(libs.findLibrary("androidx.ui.graphics").get())
      implementation(libs.findLibrary("androidx.ui.tooling.preview").get())
      implementation(libs.findLibrary("androidx.material3").get())

      // compose navigation
      implementation(libs.findLibrary("androidx.navigation.compose").get())

      androidTestImplementation(platform(bom))
      androidTestImplementation(libs.findLibrary("androidx.ui.test.junit4").get())

      debugImplementation(libs.findLibrary("androidx.ui.tooling").get())
      debugImplementation(libs.findLibrary("androidx.ui.test.manifest").get())

    }

    @Suppress("UnstableApiUsage")
    testOptions {
      unitTests {
        // For Robolectric
        isIncludeAndroidResources = true
      }
    }
  }
}