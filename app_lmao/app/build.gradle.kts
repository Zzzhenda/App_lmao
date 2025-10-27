plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp) // ESTA ES LA FORMA CORRECTA
}

android {
    namespace = "com.example.app_lmao"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.app_lmao"
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
        // AJUSTADO: Room/KSP funciona mejor con 1.8
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        // AJUSTADO:
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // Core (Ya los tenías)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))

    // UI (Ya los tenías)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // --- DEPENDENCIAS FALTANTES AÑADIDAS ---

    // Navigation (Para navegar entre pantallas)
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // ViewModel (Para MVVM)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.3")

    // Room (Base de datos local)
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1") // Procesador de KSP para Room

    // Coil (Para cargar imágenes de galería)
    implementation("io.coil-kt:coil-compose:2.6.0")

    // --- FIN DE DEPENDENCIAS AÑADIDAS ---

    // Test (Ya los tenías)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
