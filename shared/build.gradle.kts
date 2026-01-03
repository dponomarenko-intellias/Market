import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    iosArm64()
    iosSimulatorArm64()
    
    jvm()
    
    js {
        browser()
    }
    
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }
    
    sourceSets {
        androidMain.dependencies {
            // Ktor Client Engine for Android
            implementation(libs.ktor.client.okhttp)
        }

        commonMain.dependencies {
            // Compose Multiplatform
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)

            // Kotlinx Coroutines
            implementation(libs.kotlinx.coroutinesCore)

            // Kotlinx Serialization
            implementation(libs.kotlinx.serialization.json)

            // Ktor Client
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.contentNegotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.logging)

            // Koin
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.composeVM)

            // Voyager Navigation
            implementation(libs.voyager.navigator)
            implementation(libs.voyager.screenModel)
            implementation(libs.voyager.transitions)
            implementation(libs.voyager.koin)

            // Kamel Image Loading
            implementation(libs.kamel.image)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        jvmMain.dependencies {
            // Ktor Client Engine for JVM
            implementation(libs.ktor.client.okhttp)
        }

        val wasmJsMain by getting {
            dependencies {
                // Ktor Client Engine for WasmJS
                implementation(libs.ktor.client.js)
            }
        }

        val jsMain by getting {
            dependencies {
                // Ktor Client Engine for JS
                implementation(libs.ktor.client.js)
            }
        }
    }
}

android {
    namespace = "com.dmytro.ponomarenko.market.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
