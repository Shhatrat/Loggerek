import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KtorPlugin : Plugin<Project> {

    override fun apply(target: Project) {

        target.plugins.withId("org.jetbrains.kotlin.multiplatform") {
            val kotlinExtension = target.extensions.getByType<KotlinMultiplatformExtension>()

            kotlinExtension.sourceSets.apply {
                getByName("commonMain").dependencies {
                    implementation(target.lib("okio"))
                    implementation(target.lib("ktor.client.core"))
                    implementation(target.lib("ktor.client.auth"))
                    implementation(target.lib("ktor.client.logging"))
                    implementation(target.lib("kotlinx.datetime"))
                    implementation(target.lib("ktor.client.content.negotiation"))
                    implementation(target.lib("ktor.serialization.kotlinx.json"))
                }
                getByName("androidMain").dependencies {
                    implementation(target.lib("ktor.client.cio"))
                }
                getByName("iosMain").dependencies {
                    implementation(target.lib("ktor.client.darwin"))
                }
                getByName("jvmMain").dependencies {
                    implementation(target.lib("ktor.client.cio"))
                }
            }
        }
    }
}

