plugins {
    id("ticketing.android.library")
}

android {
    namespace = "ru.alekseevjk.ticketing.core.network"
}

dependencies {
    implementation(libs.dagger)
    implementation(libs.okhttp)
    implementation(libs.retrofit)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}