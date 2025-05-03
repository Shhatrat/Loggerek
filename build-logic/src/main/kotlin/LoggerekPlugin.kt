import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

class LoggerekPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val kotlinMultiplatformExtension = extensions.getByType<KotlinMultiplatformExtension>()
            with(kotlinMultiplatformExtension) {
                @OptIn(ExperimentalWasmDsl::class)
                wasmJs {
                    browser {
                        val rootDirPath = project.rootDir.path
                        val projectDirPath = project.projectDir.path
                        commonWebpackConfig {
                            devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                                static = (static ?: mutableListOf()).apply {
                                    // Serve sources to debug inside browser
                                    add(rootDirPath)
                                    add(projectDirPath)
                                }
                            }
                        }
                    }
                }
                androidTarget {
                    compilerOptions {
                        jvmTarget.set(JvmTarget.JVM_11)
                    }
                }

                listOf(
                    iosX64(),
                    iosArm64(),
                    iosSimulatorArm64()
                )
                jvm()
                with(sourceSets) {
                    setupSourceSets()
                }
            }
        }
    }

    private fun NamedDomainObjectContainer<KotlinSourceSet>.setupSourceSets() {
        val commonMain = getByName("commonMain") {
            dependencies { }
        }
        val commonTest = getByName("commonTest") {
            dependencies { }
        }

        val mobileMain = create("mobileMain") {
            dependsOn(commonMain)
        }

        val mobileTest = create("mobileTest") {
            dependsOn(commonTest)
        }

        val androidMain = getByName("androidMain") {
            dependsOn(mobileMain)
            dependencies {
            }
        }

        val androidUnitTest = getByName("androidUnitTest") {
            dependsOn(mobileTest)
        }

        create("iosMain") {
            dependsOn(mobileMain)
            getByName("iosX64Main").dependsOn(this)
            getByName("iosArm64Main").dependsOn(this)
            getByName("iosSimulatorArm64Main").dependsOn(this)
        }

        create("iosTest") {
            dependsOn(mobileTest)
            getByName("iosX64Test").dependsOn(this)
            getByName("iosArm64Test").dependsOn(this)
            getByName("iosSimulatorArm64Test").dependsOn(this)
        }
    }

}