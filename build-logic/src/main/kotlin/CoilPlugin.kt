import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class CoilPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.afterEvaluate {
            target.extensions.getByType<KotlinMultiplatformExtension>().apply {
                sourceSets.getByName("commonMain").dependencies {
                    implementation(target.lib("coil.compose"))
                    implementation(target.lib("coil.svg"))
                    implementation(target.lib("coil.network.ktor3"))
                }
            }
        }
    }
}