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

    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.3.0")

    //room
    implementation ("androidx.room:room-ktx:2.4.0-alpha01")
    implementation ("androidx.room:room-runtime:2.4.0-alpha01")
    implementation ("androidx.room:room-rxjava2:2.4.0-alpha01")
    kapt ("androidx.room:room-compiler:2.4.0-alpha01")

    implementation("com.google.code.gson:gson:2.8.6")

    //Dependency injection
    implementation ("io.insert-koin:koin-android:3.0.1")
    implementation ("io.insert-koin:koin-android-ext:3.0.1")
    implementation ("io.insert-koin:koin-androidx-workmanager:3.0.1")
    implementation ("io.insert-koin:koin-androidx-compose:3.0.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}