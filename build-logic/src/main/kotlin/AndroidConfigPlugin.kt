import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.gradle.api.artifacts.VersionCatalogsExtension


class AndroidConfigPlugin : Plugin<Project> {

    fun Project.lib(name: String) = rootProject.extensions
    .getByType<VersionCatalogsExtension>()
    .named("libs")
    .findVersion(name)
    .get()
    .toString()
    .toInt()

    override fun apply(project: Project) {
        println("Hello from simplified build-logic!")

        val minSdk = project.lib("android-minSdk")
        val targetSdk = project.lib("android-targetSdk")
        val compileSdk = project.lib("android-compileSdk")

        project.pluginManager.withPlugin("com.android.application") {
            with(project.extensions.getByType<ApplicationExtension>()) {
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_11
                    targetCompatibility = JavaVersion.VERSION_11
                }
                defaultConfig {
                    this.minSdk = minSdk
                    this.targetSdk = targetSdk
                }
            }
        }

        project.pluginManager.withPlugin("com.android.library") {
            with(project.extensions.getByType<LibraryExtension>()) {
                this.compileSdk = compileSdk
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_11
                    targetCompatibility = JavaVersion.VERSION_11
                }
                defaultConfig{
                    this.minSdk = minSdk
                    this.targetSdk = targetSdk
                }
            }
        }
    }
}