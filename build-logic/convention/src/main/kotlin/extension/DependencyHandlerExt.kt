package extension

import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler

internal fun DependencyHandler.project(path: String): Dependency =
    project(mapOf("path" to path))

internal fun DependencyHandler.implementation(notation: Any): Dependency? =
    add("implementation", notation)
internal fun DependencyHandler.api(notation: Any): Dependency? =
    add("api", notation)
