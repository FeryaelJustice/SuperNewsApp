import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}

val newsApiKey: String = gradleLocalProperties(rootDir, providers).getProperty("api_key", "")
val deeplApiKey: String = gradleLocalProperties(rootDir, providers).getProperty("deepl_api_key", "")

android {
    namespace = "com.feryaeljustice.supernewsapp"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.feryaeljustice.supernewsapp"
        minSdk = 35
        targetSdk = 37
        versionCode = 15
        versionName = "2.1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        resValue("string", "newsApiKey", "\"" + newsApiKey + "\"")
        resValue("string", "deeplApiKey", "\"" + deeplApiKey + "\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            ndk {
                debugSymbolLevel = "FULL"
            }
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
            isDebuggable = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    buildFeatures {
        compose = true
        resValues = true
    }
    packaging {
        resources {
            excludes += "/META-INF/DEPENDENCIES"
        }
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xexplicit-backing-fields")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.com.google.android.material)
    implementation(libs.compose.material.icons)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

//    debugImplementation(libs.com.squareup.leakcanary)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.androidx.core.splashscreen)

    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)

    implementation(libs.com.google.dagger.hilt.android)
    ksp(libs.com.google.dagger.hilt.compiler)

    implementation(libs.com.squareup.retrofit2.retrofit2)
    implementation(libs.com.squareup.retrofit2.converter.kotlinx.serialization)

    implementation(libs.io.coil.compose)

    implementation(libs.androidx.datastore.datastore.preferences)
    implementation(libs.androidx.compose.foundation.foundation)
    implementation(libs.androidx.paging.paging.runtime)
    implementation(libs.androidx.paging.paging.compose)
    implementation(libs.androidx.room.room.runtime)
    ksp(libs.androidx.room.room.compiler)
    implementation(libs.androidx.room.room.ktx)

    implementation(libs.com.deepl.api)

    implementation(libs.integrity)

    implementation(libs.androidx.compose.adaptive)
    implementation(libs.androidx.compose.adaptive.layout)
    implementation(libs.androidx.compose.adaptive.navigation)
}