import com.android.build.gradle.LibraryExtension
import com.anna.greeneats.constants.AndroidConventions
import com.anna.greeneats.constants.AndroidSdk
import com.anna.greeneats.extensions.androidTestImplementation
import com.anna.greeneats.extensions.implementation
import com.anna.greeneats.extensions.libs
import com.anna.greeneats.extensions.testImplementation
import com.anna.greeneats.helpers.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidDataConventionPlugin: Plugin<Project> {
  override fun apply(target: Project) {
    with(target){
      with(pluginManager) {
        apply(AndroidConventions.LIBRARY)
        apply(AndroidConventions.HILT)
        apply("org.jetbrains.kotlin.plugin.serialization")
      }

      extensions.configure<LibraryExtension> {
        configureKotlinAndroid(this)
        defaultConfig.targetSdk = AndroidSdk.targetSdk
      }

      dependencies {
        // Androidx
        implementation(libs.findLibrary("androidx.core.ktx").get())
        implementation(libs.findLibrary("androidx.appcompat").get())

        // Kotlinx coroutines
        implementation(libs.findLibrary("kotlinx.coroutines").get())

        // Kotlinx serialization
        implementation(libs.findLibrary("kotlinx.serialization.json").get())
      }
    }
  }
}