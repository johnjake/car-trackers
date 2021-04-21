plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdk = 30

    defaultConfig {
        minSdk = 26
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    val archCompVersion = "2.3.0-alpha03"
    val koinVersion = "2.0.1"

    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.3.0")

    //room
    implementation ("androidx.room:room-ktx:$archCompVersion")
    implementation ("androidx.room:room-runtime:$archCompVersion")
    implementation ("androidx.room:room-rxjava2:$archCompVersion")
    kapt ("androidx.room:room-compiler:$archCompVersion")

    implementation("com.google.code.gson:gson:2.8.6")

    //Dependency injection
    implementation("org.koin:koin-android:$koinVersion")
    implementation ("org.koin:koin-androidx-viewmodel:$koinVersion")
    implementation ("org.koin:koin-androidx-scope:$koinVersion")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}