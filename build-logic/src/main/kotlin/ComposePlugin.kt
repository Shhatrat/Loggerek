import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class ComposePlugin: Plugin<Project> {

    override fun apply(target: Project) {
        val libs = target.extensions.getByType<VersionCatalogsExtension>().named("libs")
        target.pluginManager.apply(libs.findPlugin("composeMultiplatform").get().get().pluginId)
        target.pluginManager.apply(libs.findPlugin("composeCompiler").get().get().pluginId)


        target.afterEvaluate {
            target.extensions.getByType<KotlinMultiplatformExtension>().apply {
                sourceSets.getByName("commonMain").dependencies {
                    implementation(target.lib("koin.compose"))
                    implementation(target.lib("compose.runtime"))
                    implementation(target.lib("compose.foundation"))
                    implementation(target.lib("compose.material"))
                    implementation(target.lib("compose.ui"))
                    implementation(target.lib("compose.resources"))
                }
            }
        }
    }
}