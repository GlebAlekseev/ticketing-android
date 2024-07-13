plugins {
    `kotlin-dsl`
}

group = "ru.alekseevjk.ticketing.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.agp)
    implementation(libs.ksp.gradle.plugin)
    implementation(libs.kotlin.plugin)
}

gradlePlugin {
    plugins {
        register("AndroidApplicationConventionPlugin") {
            id = "ticketing.android.application"
            implementationClass = "plugins.AndroidApplicationConventionPlugin"
        }
        register("AndroidLibraryConventionPlugin") {
            id = "ticketing.android.library"
            implementationClass = "plugins.AndroidLibraryConventionPlugin"
        }
        register("FeatureApiConventionPlugin") {
            id = "ticketing.feature.api"
            implementationClass = "plugins.FeatureApiConventionPlugin"
        }
        register("FeatureImplementationConventionPlugin") {
            id = "ticketing.feature.impl"
            implementationClass = "plugins.FeatureImplementationConventionPlugin"
        }
        register("AndroidDesignConventionPlugin") {
            id = "ticketing.android.design"
            implementationClass = "plugins.AndroidDesignConventionPlugin"
        }
        register("KspConventionPlugin") {
            id = "ticketing.ksp"
            implementationClass = "plugins.KspConventionPlugin"
        }
    }
}