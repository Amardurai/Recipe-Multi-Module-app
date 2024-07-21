import com.android.build.gradle.LibraryExtension
import com.anna.greeneats.constants.AndroidConventions
import com.anna.greeneats.constants.AndroidSdk
import com.anna.greeneats.extensions.implementation
import com.anna.greeneats.extensions.libs
import com.anna.greeneats.helpers.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryConventionPlugin: Plugin<Project> {
  override fun apply(target: Project) {
    with(target){
      with(pluginManager){
        apply("com.android.library")
        apply("org.jetbrains.kotlin.android")
        apply(AndroidConventions.HILT)
        apply(AndroidConventions.LINT)
      }

      extensions.configure<LibraryExtension> {
        configureKotlinAndroid(this)
        defaultConfig.targetSdk = AndroidSdk.targetSdk

        testOptions{
          unitTests {
            isIncludeAndroidResources = true
          }

          animationsDisabled = true
        }
      }

      dependencies {
        implementation(libs.findLibrary("androidx.core.ktx").get())
        implementation(libs.findLibrary("androidx.appcompat").get())

      }
    }
  }

}