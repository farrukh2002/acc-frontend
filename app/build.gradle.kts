plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "crazyboyfeng.accSettings"
    compileSdk = 34

    defaultConfig {
        applicationId = "crazyboyfeng.accSettings"
        minSdk = 26
        //noinspection OldTargetApi
        targetSdk = 34
        versionCode = 202406020
        versionName = "Dev-0.3.6-Pre"
//        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
        resValue("string", "version_name", versionName!!)
    }
//    buildFeatures { viewBinding = true }
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.preference.ktx)
    implementation(libs.androidx.preference)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.material3)
    implementation(libs.material)
    //noinspection GradleDependency
    implementation(libs.topjohnwusu)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    implementation(libs.edittext)
    implementation(libs.numberpicker)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
//    testImplementation("junit:junit:4.13.2")
//    androidTestImplementation("androidx.test.ext:junit:1.1.3")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}