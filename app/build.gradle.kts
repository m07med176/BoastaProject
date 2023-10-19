
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    kotlin("kapt")
    alias(libs.plugins.dagger.hilt.android)
}

android {
    namespace = "tech.bosta.bostatask"
    compileSdk = 34

    defaultConfig {
        applicationId = "tech.bosta.bostatask"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.ui)
    implementation(libs.core.ktx)
    implementation(libs.material3)
    implementation(libs.ui.graphics)
    debugImplementation(libs.ui.tooling)
    implementation(libs.activity.compose)
    implementation(libs.ui.lottie.compose)
    implementation(libs.ui.tooling.preview)
    implementation(libs.navigation.compose)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(platform(libs.compose.bom))
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))

    // Test
    testImplementation(libs.junit)
    testImplementation(libs.test.mockito)
    testImplementation(libs.test.hamcrest)
    testImplementation(libs.test.googleTruth)
    testImplementation(libs.test.robolectric)
    debugImplementation(libs.ui.test.manifest)
    testImplementation(libs.test.core.testing)
    testImplementation(libs.test.coroutineTest)
    testImplementation(libs.test.mockitoInline)
    testImplementation(libs.test.mockwebserver)
    testImplementation(libs.test.mockitoKotlin)
    testImplementation(libs.test.core.ktx.test)
    testImplementation(libs.test.junit.ktx.test)
    testImplementation(libs.test.mockitoAndroid)
    testImplementation(libs.test.hamcrest.library)
    implementation("androidx.work:work-runtime-ktx:2.7.1")

    // Android Test
    androidTestImplementation(libs.ui.test.junit4)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.test.navigation.compose)

    // Dagger Hilt
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.android)
    implementation("androidx.hilt:hilt-work:1.0.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    // Retrofit
    implementation (libs.retrofit2)
    implementation (libs.retrofit2.gson)
    implementation (libs.retrofit2.logging)

    // Coil
    implementation (libs.coil)
    implementation (libs.coil.compose)

    // Accompanist
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.insets)
}