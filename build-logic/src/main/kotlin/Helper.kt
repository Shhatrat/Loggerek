import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

fun Project.libVersion(name: String) = catalogVersion()
    .findVersion(name)
    .get()
    .toString()
    .toInt()

fun Project.catalogVersion(): VersionCatalog = rootProject.extensions
    .getByType<VersionCatalogsExtension>()
    .named("libs")

fun Project.lib(name: String): Any{
    return catalogVersion().findLibrary(name).get()
}