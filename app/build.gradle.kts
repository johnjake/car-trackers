import com.cartrackers.app.Config.androidAppId
import com.cartrackers.app.Config.androidMinSdk
import com.cartrackers.app.Config.androidTargetSdk
import com.cartrackers.app.Config.androidVerCode
import com.cartrackers.app.Config.androidVerName
import java.util.Properties

plugins {
    id("com.android.application")
    id("kotlin-android")
    id ("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-kapt")
}

android {
    compileSdk = androidTargetSdk

    defaultConfig {
        applicationId = androidAppId
        minSdk = androidMinSdk
        targetSdk = androidTargetSdk
        versionCode = androidVerCode
        versionName = androidVerName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    buildTypes {

        debug {
            isDebuggable = true
            applicationIdSuffix = ".dev"
            getProps(rootProject.file("debug.properties")).forEach { prop ->
                buildConfigField("String", prop.key.toString(), prop.value.toString())
            }
        }

        release {
            isMinifyEnabled = false
            //isShrinkResources = false
            isDebuggable = false
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

    kotlinOptions.apply {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }

    /** testOptions {
    unitTests.isReturnDefaultValues = true
    } **/
}

dependencies {

    implementation(project(mapOf("path" to ":baseplate-persistence")))

    //recycleView
    implementation("androidx.recyclerview:recyclerview:1.2.0")

    //annotation
    implementation("com.android.support:support-annotations:28.0.0")
    implementation("androidx.annotation:annotation:1.2.0")

    //stdlib
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.4.32")

    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.3.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation ("com.android.support:design:30.0.0")

    //httpOK
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")

    //CircleIndicator
    // implementation ("me.relex:circleindicator:2.1.4")

    //sharedPreferrences
    implementation ("androidx.preference:preference-ktx:1.1.1")

    //javax annotation
    implementation("javax.inject:javax.inject:1")

    // implementation ("androidx.room:room-coroutines:2.1.0-alpha04")
    implementation ("androidx.room:room-runtime:2.4.0-alpha01")
    implementation ("androidx.room:room-ktx:2.4.0-alpha01")
    kapt ("androidx.room:room-compiler:2.4.0-alpha01")

    //Dependency injection
    // Koin main features for Android (Scope,ViewModel ...)
    implementation ("io.insert-koin:koin-android:3.0.1")
    // Koin Android - experimental builder extensions
    implementation ("io.insert-koin:koin-android-ext:3.0.1")
    // Koin for Jetpack WorkManager
    implementation ("io.insert-koin:koin-androidx-workmanager:3.0.1")
    // Koin for Jetpack Compose (unstable version)
    implementation ("io.insert-koin:koin-androidx-compose:3.0.1")


    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")

    //timber
    implementation("com.jakewharton.timber:timber:4.7.1")

    //cardView
    implementation("androidx.cardview:cardview:1.0.0")

    //circleView
    implementation("de.hdodenhof:circleimageview:3.0.1")

    //images
    implementation ("com.makeramen:roundedimageview:2.3.0")

    //airbnb
    implementation ("com.airbnb.android:lottie:3.4.4")

    //navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")
    implementation ("androidx.navigation:navigation-compose:1.0.0-alpha10")

    //google maps
    implementation ("com.google.android.gms:play-services-maps:17.0.0")

    //LifeCycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.3.1")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.3.1")
    implementation("androidx.lifecycle:lifecycle-service:2.3.1")
    implementation("androidx.lifecycle:lifecycle-process:2.3.1")
    implementation("androidx.lifecycle:lifecycle-reactivestreams-ktx:2.3.1")

    //rxBinding
    implementation("com.jakewharton.rxbinding3:rxbinding:3.1.0")
    implementation("com.jakewharton.rxbinding3:rxbinding-core:3.1.0")
    implementation("com.jakewharton.rxbinding3:rxbinding-appcompat:3.1.0")

    //kapt("androidx.lifecycle:lifecycle-compiler:2.3.1")
    testImplementation("androidx.arch.core:core-testing:2.1.0")

    //coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.3")

    //image viewer
    implementation("io.coil-kt:coil:1.0.0")

    //paging
    implementation ("androidx.paging:paging-runtime-ktx:3.0.0-rc01")

    //testImplementation("junit:junit:4.13.2")
    testImplementation("junit:junit:4.13.2")
    testImplementation("io.mockk:mockk:1.10.0")
    testImplementation("io.kotlintest:kotlintest-assertions:3.4.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.2")
    testImplementation("org.testcontainers:testcontainers:1.15.1")
    testImplementation("org.testcontainers:junit-jupiter:1.15.1")
    testImplementation ("androidx.test.ext:junit:1.1.3-alpha05")
    testImplementation ("org.mockito:mockito-core:3.0.0")
    testImplementation ("com.nhaarman.mockitokotlin2:mockito-kotlin:2.1.0")
    testImplementation ("org.mockito:mockito-inline:3.0.0")

    testImplementation ("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testRuntimeOnly ("org.junit.jupiter:junit-jupiter-engine:5.7.0")

    /** debugImplementation if unit test, androidTestImplementation for androidTest **/
    testImplementation ("io.insert-koin:koin-test:3.0.1")

    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
    implementation(kotlin("reflect"))
}

repositories {
    google()
}


fun Project.android(configure: com.android.build.gradle.internal.dsl.BaseAppModuleExtension.() -> Unit) {
    return (this as ExtensionAware).extensions.configure(
        "android",
        configure
    )
}

fun getProps(file: File): Properties {
    val props = Properties()
    props.load(file.inputStream())
    return props
}

apply (plugin = "androidx.navigation.safeargs.kotlin")
apply (plugin = "kotlin-kapt")


