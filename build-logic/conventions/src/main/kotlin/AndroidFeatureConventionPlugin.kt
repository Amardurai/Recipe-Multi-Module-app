import com.android.build.gradle.LibraryExtension
import com.anna.greeneats.constants.AndroidConventions
import com.anna.greeneats.extensions.implementation
import com.anna.greeneats.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin: Plugin<Project>{
  override fun apply(target: Project) {
    with(target){
      with(pluginManager){
        apply(AndroidConventions.LIBRARY)
        apply(AndroidConventions.HILT)
        apply(AndroidConventions.Compose.LIBRARY)
      }

      extensions.configure<LibraryExtension> {
        defaultConfig{
          testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
        testOptions.animationsDisabled = true
      }

      dependencies {
        implementation(libs.findLibrary("kotlinx.coroutines").get())
      }
    }
  }
}