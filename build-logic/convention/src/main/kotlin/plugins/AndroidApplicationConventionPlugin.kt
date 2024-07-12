package plugins

import com.android.build.api.dsl.ApplicationExtension
import extension.requiredVersion
import extension.versionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import setup.configureKotlinAndroid

@Suppress("unused")
class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(plugins) {
                apply(Plugins.AndroidApplication)
                apply(Plugins.KotlinAndroid)
                apply(Plugins.TicketingKsp)
            }

            val libs = versionCatalog

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)

                defaultConfig {
                    versionName = libs.requiredVersion("appVersionName")
                    versionCode = libs.requiredVersion("appVersionCode").toInt()
                }

                packaging {
                    resources {
                        excludes += setOf(
                            "**/META-INF/LICENSE*",
                            "**/META-INF/*.kotlin_module",
                        )
                    }
                }

                buildFeatures {
                    shaders = false
                    resValues = true
                    buildConfig = true
                    viewBinding = true
                }
            }
        }
    }
}
