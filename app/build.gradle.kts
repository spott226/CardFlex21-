plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android") version "1.9.22"
}


android {
    namespace = "com.example.cardflex"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.cardflex"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.compose.ui:ui:1.5.4")
    implementation("androidx.compose.material3:material3:1.1.2")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.4")
    implementation("androidx.compose.material:material-icons-extended:1.5.4")
    debugImplementation("androidx.compose.ui:ui-tooling:1.5.4")
}