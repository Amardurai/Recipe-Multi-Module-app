import com.android.build.gradle.TestExtension
import com.anna.greeneats.constants.AndroidSdk
import com.anna.greeneats.extensions.androidTestImplementation
import com.anna.greeneats.extensions.implementation
import com.anna.greeneats.extensions.ksp
import com.anna.greeneats.extensions.kspAndroidTest
import com.anna.greeneats.extensions.libs
import com.anna.greeneats.extensions.testImplementation
import com.anna.greeneats.helpers.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidTestConventionPlugin: Plugin<Project> {
  override fun apply(target: Project) {
    with(target){
      with(pluginManager) {
        apply("com.android.test")
        apply("org.jetbrains.kotlin.android")
        apply("com.google.devtools.ksp")
      }

      extensions.configure<TestExtension> {
        configureKotlinAndroid(this)
        defaultConfig.targetSdk = AndroidSdk.targetSdk
      }

      dependencies {
        implementation(libs.findLibrary("hilt.android").get())
        ksp(libs.findLibrary("hilt.android.compiler").get())

        testImplementation(kotlin("test"))
        testImplementation(libs.findLibrary("kotlinx.coroutines.test").get())
        //Robolectric tests
        testImplementation(libs.findLibrary("hilt.android.testing").get())
        kspAndroidTest(libs.findLibrary("hilt.android.compiler").get())

        androidTestImplementation(kotlin("test"))
        androidTestImplementation(libs.findLibrary("androidx.junit").get())
        androidTestImplementation(libs.findLibrary("androidx.espresso.core").get())

        // Instrumented tests
        androidTestImplementation(libs.findLibrary("hilt.android.testing").get())
        kspAndroidTest(libs.findLibrary("hilt.android.compiler").get())
      }
    }
  }
}