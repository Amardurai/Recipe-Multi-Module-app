package com.anna.greeneats.extensions

import com.android.build.api.dsl.Lint

fun Lint.initConfig() {
  xmlReport = true
  checkDependencies = true
}