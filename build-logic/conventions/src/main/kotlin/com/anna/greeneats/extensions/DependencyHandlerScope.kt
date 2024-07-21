package com.anna.greeneats.extensions

import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.ksp(library: Provider<MinimalExternalModuleDependency>) {
  add("ksp", library)
}

fun DependencyHandlerScope.kspAndroidTest(library: Provider<MinimalExternalModuleDependency>) {
  add("kspAndroidTest", library)
}

fun DependencyHandlerScope.implementation(library: Provider<MinimalExternalModuleDependency>) {
  add("implementation", library)
}

fun DependencyHandlerScope.androidTestImplementation(library: Any) {
  add("androidTestImplementation", library)
}

fun DependencyHandlerScope.testImplementation(library: Any) {
  add("testImplementation", library)
}

fun DependencyHandlerScope.debugImplementation(library: Provider<MinimalExternalModuleDependency>) {
  add("debugImplementation", library)
}