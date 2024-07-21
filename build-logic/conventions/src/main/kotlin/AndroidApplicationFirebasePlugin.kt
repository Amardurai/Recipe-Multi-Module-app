import com.anna.greeneats.extensions.implementation
import com.anna.greeneats.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationFirebasePlugin: Plugin<Project> {
  override fun apply(target: Project) {
    with(target){
      with(pluginManager) {
        apply("com.google.gms.google-services")
        apply("com.google.firebase.firebase-perf")
        apply("com.google.firebase.crashlytics")
      }

      dependencies {
        val bom = libs.findLibrary("firebase-bom").get()
        implementation(platform(bom))
        implementation(libs.findLibrary("firebase.performance").get())
        implementation(libs.findLibrary("firebase.crashlytics").get())
      }
    }
  }
}