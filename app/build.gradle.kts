plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("org.jetbrains.kotlin.plugin.allopen") version "1.3.72"
    id("com.apollographql.apollo") version "2.2.0"
}

android {
    compileSdkVersion(29)
    buildToolsVersion("29.0.3")

    defaultConfig {
        applicationId = "io.aircall.android"
        minSdkVersion(21)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
        manifestPlaceholders["appAuthRedirectScheme"] = "io.aircall.android"
        multiDexEnabled = true
        testInstrumentationRunner = "io.aircall.android.CustomTestRunner"
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.3.72")
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    implementation("androidx.fragment:fragment-ktx:1.2.5")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
    implementation("androidx.multidex:multidex:2.0.1")
    implementation("net.openid:appauth:0.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7")
    implementation("com.apollographql.apollo:apollo-runtime:2.2.0")
    implementation("com.apollographql.apollo:apollo-coroutines-support:2.2.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.0")
    implementation("com.google.android.material:material:1.1.0")
    kapt("com.google.dagger:dagger-android-processor:2.24")
    kapt("com.google.dagger:dagger-compiler:2.24")
    implementation("com.google.dagger:dagger:2.24")
    implementation("com.google.dagger:dagger-android:2.24")
    implementation("com.google.dagger:dagger-android-support:2.24")
    testImplementation("junit:junit:4.13")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.7")
    testImplementation("com.github.ologe:flow-test-observer:1.4.1")
    testImplementation("io.mockk:mockk:1.9.2")
    androidTestImplementation("io.mockk:mockk-android:1.9.2")
    androidTestImplementation("androidx.test:rules:1.2.0")
    androidTestImplementation("androidx.test:runner:1.2.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
}

apollo {
    generateKotlinModels.set(true)
}

allOpen {
    annotation("io.aircall.android.OpenForTesting")
}