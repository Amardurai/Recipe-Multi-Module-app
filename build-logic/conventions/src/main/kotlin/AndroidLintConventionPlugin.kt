import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.dsl.Lint
import com.anna.greeneats.extensions.initConfig
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLintConventionPlugin: Plugin<Project> {
  override fun apply(target: Project) {
    with(target){
      when {
        pluginManager.hasPlugin("com.android.application") ->
          configure<ApplicationExtension> { lint(Lint::initConfig) }

        pluginManager.hasPlugin("com.android.library") ->
          configure<LibraryExtension> { lint(Lint::initConfig) }

        else -> {
          pluginManager.apply("com.android.lint")
          configure<Lint>(Lint::initConfig)
        }
      }
    }
  }
}