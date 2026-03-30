plugins {
    id("com.android.application")
    id ("com.google.gms.google-services")
}

android {
    namespace = "com.example.uaagi_app"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.uaagi_app"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        isCoreLibraryDesugaringEnabled  = true
    }

    applicationVariants.all {
        outputs.forEach {
            val fileName = "UAAGI-OneHire-${buildType.name}-v${versionName}.apk"
            (it as com.android.build.gradle.internal.api.BaseVariantOutputImpl)
                .outputFileName = fileName
        }
    }
}

dependencies {
    implementation(libs.androidx.camera.camera2.pipe)
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    implementation(libs.volley)
    implementation(libs.lottie)
    implementation(libs.cardview)
    implementation(libs.recyclerview)
    implementation(libs.gson)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.glide)
    implementation(libs.pusher.java.client)
    implementation(libs.firebase.messaging)
    implementation(platform(libs.firebase.bom))
    implementation(libs.play.services.auth)
    implementation (libs.lifecycle.livedata.ktx)
    implementation(libs.androidx.biometric)

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
