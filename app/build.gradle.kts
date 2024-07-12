plugins {
    id("ticketing.android.application")
    id("ticketing.ksp")
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.android)
    id("ticketing.kotlin.parcelize")
    id("kotlin-parcelize")
}

android {
    namespace = "ru.alekseevjk.ticketing"
    defaultConfig {
        applicationId = "ru.alekseevjk.ticketing"
    }
}

dependencies {
    implementation(project(":feature:airline:impl"))
    implementation(project(":core:di"))
    implementation(project(":core:network"))
    implementation(project(":app:api"))
    implementation(project(":design"))
    implementation(libs.threetenabp)
    implementation(libs.kotlinx.datetime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson.converter)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.dagger)
    ksp(libs.dagger.compiler)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}