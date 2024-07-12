plugins {
    id("ticketing.android.library")
}

android {
    namespace = "ru.alekseevjk.ticketing.api"
    buildFeatures {
        resValues = true
        androidResources = true
    }
}

dependencies {
    implementation(libs.dagger)
}