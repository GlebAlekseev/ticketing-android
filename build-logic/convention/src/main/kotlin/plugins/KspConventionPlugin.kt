package plugins

import com.google.devtools.ksp.gradle.KspExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import java.io.File

@Suppress("unused")
class KspConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(plugins) {
                apply("com.google.devtools.ksp")
            }

            extensions.getByType<KspExtension>().apply {
                configureRoom(target.projectDir)
            }
        }
    }

    private fun KspExtension.configureRoom(projectDir: File) {
        arg("room.schemaLocation", "$projectDir/schemas")
        arg("room.incremental", "true")
    }
}
