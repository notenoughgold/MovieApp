plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    id("kotlin-kapt")
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.altayiskender.movieapp"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "0.1"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
        debug {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }

    kotlin {
        jvmToolchain(17)
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }

    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }

    namespace = "com.altayiskender.movieapp"
}

dependencies {
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.3")

    //support
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")

    //compose
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui.ui)
    implementation("androidx.compose.material3:material3")
    implementation(libs.compose.ui.tooling.preview)
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation("androidx.navigation:navigation-compose:2.6.0")
    implementation(libs.compose.runtime.livedata)

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.6.1")

    implementation(libs.room)
    implementation(libs.room.runtime)
    kapt(libs.room.compiler)

    //coil
    implementation("io.coil-kt:coil-compose:2.1.0")

    //Dagger Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    // retrofit
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    //paging
    implementation("androidx.paging:paging-runtime-ktx:3.2.0")
    implementation("androidx.paging:paging-compose:3.2.0")

    //testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.7.10")
    androidTestImplementation(libs.compose.ui.test)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)
    androidTestImplementation(libs.hilt.android.testing)
    kaptAndroidTest(libs.hilt.compiler)

    //log
    implementation("com.jakewharton.timber:timber:5.0.1")
}
