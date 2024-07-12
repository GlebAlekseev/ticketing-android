package plugins

import com.android.build.gradle.LibraryExtension
import extension.requiredVersion
import extension.versionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import setup.configureKotlinAndroid
import setup.configureNamespace

@Suppress("unused")
class AndroidDesignConventionPlugin : Plugin<Project> {

    @Suppress("UnstableApiUsage")
    override fun apply(target: Project) {
        with(target) {
            with(plugins) {
                apply(Plugins.KotlinAndroid)
                apply(Plugins.AndroidLibrary)
            }

            val libs = versionCatalog

            extensions.configure<LibraryExtension> {
                configureNamespace(this)
                configureKotlinAndroid(this)

                compileSdk = libs.requiredVersion("compileSdk").toInt()
                buildToolsVersion = libs.requiredVersion("buildTools")

                defaultConfig {
                    minSdk = libs.requiredVersion("minSdk").toInt()
                }

                buildFeatures {
                    viewBinding = true
                    androidResources = true
                    shaders = false
                    resValues = true
                    buildConfig = false
                }
            }
        }
    }
}
