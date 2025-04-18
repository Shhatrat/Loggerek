import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.gradle.api.artifacts.VersionCatalogsExtension


class AndroidConfigPlugin : Plugin<Project> {


    override fun apply(project: Project) {
        println("Hello from simplified build-logic!")

        val minSdk = project.libVersion("android-minSdk")
        val targetSdk = project.libVersion("android-targetSdk")
        val compileSdk = project.libVersion("android-compileSdk")

        project.pluginManager.withPlugin("com.android.application") {
            with(project.extensions.getByType<ApplicationExtension>()) {
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_11
                    targetCompatibility = JavaVersion.VERSION_11
                }
                println(project.name)
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