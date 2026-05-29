import com.google.protobuf.gradle.id

plugins {
    alias(libs.plugins.android.application)
    id("com.google.protobuf")
}

android {
    namespace = "com.djowda.grpc_test"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.djowda.grpc_test"
        minSdk = 27
        targetSdk = 37
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

    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.25.1"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.64.0"
        }
        // Optional: Add grpckt if you want Kotlin-specific coroutine stubs
//        id("grpckt") {
//            artifact = "io.grpc:protoc-gen-grpc-kotlin:1.4.1:jdk8@jar"
//        }
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                id("java") {
                    option("lite") // Generates lightweight Java classes
                }
            }
            task.plugins {
                id("grpc") {
                    option("lite")
                }
//                id("grpckt") // Optional
            }
        }
    }
}

dependencies {

    // Fixes the missing javax.annotation.Generated error
    compileOnly("org.apache.tomcat:annotations-api:6.0.53")

    // Alternative option if you prefer standard Jakarta artifact
    // compileOnly("jakarta.annotation:jakarta.annotation-api:1.3.5")

    implementation(libs.activity.ktx)
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.material)

    // gRPC Core & Transports
    implementation("io.grpc:grpc-okhttp:1.81.0")
    implementation("io.grpc:grpc-protobuf-lite:1.81.0")
    implementation("io.grpc:grpc-stub:1.81.0")

    // Protobuf Runtime
    implementation("com.google.protobuf:protobuf-javalite:4.35.0")


    testImplementation(libs.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.ext.junit)
}