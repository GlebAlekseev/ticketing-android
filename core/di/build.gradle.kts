plugins {
    id("ticketing.android.library")
}

android {
    namespace = "ru.alekseevjk.ticketing.core.di"
}

dependencies {
    implementation(libs.dagger)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}