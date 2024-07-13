plugins {
    id("ticketing.android.library")
}

android {
    namespace = "ru.alekseevjk.ticketing.core.common"
    buildFeatures {
        viewBinding = true
        resValues = true
        androidResources = true
    }
}

dependencies {

    implementation(project(":app:api"))
    implementation(libs.dagger)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}