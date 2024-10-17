plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "crazyboyfeng.accSettings"
    //noinspection GradleDependency
    compileSdk = 34
    defaultConfig {
        applicationId = "crazyboyfeng.accSettings"
        minSdk = 26
        //noinspection ExpiredTargetSdkVersion
        targetSdk = 34
        versionCode = 202406020
        versionName = "Dev-0.3.3-Pre"
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
    kotlinOptions { jvmTarget = "1.8" }
}

dependencies {
//    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("androidx.preference:preference-ktx:1.2.1")
    implementation("androidx.work:work-runtime:2.9.1")
    //noinspection GradleDependency
    implementation("com.github.topjohnwu.libsu:core:4.0.0")
   val axpeVersion = "0.9.0"
    implementation("com.github.CrazyBoyFeng.AndroidXPreferenceExtensions:edittext:$axpeVersion")
    implementation("com.github.CrazyBoyFeng.AndroidXPreferenceExtensions:numberpicker:$axpeVersion")
//    testImplementation("junit:junit:4.13.2")
//    androidTestImplementation("androidx.test.ext:junit:1.1.3")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}