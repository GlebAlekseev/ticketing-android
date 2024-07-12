package setup

import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.BaseExtension
import extension.implementation
import extension.requiredVersion
import extension.versionCatalog
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

@Suppress("UnstableApiUsage")
internal fun Project.configureKotlinAndroid(
    extension: CommonExtension<*, *, *, *, *, *>,
) {
    val libs = versionCatalog
    val javaVersion = JavaVersion.VERSION_11

    with(extension) {
        compileSdk = libs.requiredVersion("compileSdk").toInt()
        buildToolsVersion = libs.requiredVersion("buildTools")

        defaultConfig {
            minSdk = libs.requiredVersion("minSdk").toInt()
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        (extension as BaseExtension).defaultConfig.targetSdk =
            libs.requiredVersion("targetSdk").toInt()

        testOptions {
            unitTests {
                isReturnDefaultValues = true
                all { test -> test.useJUnitPlatform() }
                execution = "ANDROIDX_TEST_ORCHESTRATOR"
            }
        }

        compileOptions {
            targetCompatibility = javaVersion
            sourceCompatibility = javaVersion
        }
    }

    tasks.withType<KotlinCompile> {
        with(kotlinOptions) {
            jvmTarget = javaVersion.toString()
            freeCompilerArgs = freeCompilerArgs + listOf(
                "-opt-in=kotlin.RequiresOptIn",
                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-opt-in=kotlinx.coroutines.FlowPreview",
            )
        }
    }

    dependencies {
        implementation(libs.findLibrary("timber").get())
    }
}
