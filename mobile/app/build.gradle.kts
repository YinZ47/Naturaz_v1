@file:Suppress("UnstableApiUsage")

import java.util.Properties

plugins {
    id(BuildPlugins.androidApplication)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.hilt)
    id(BuildPlugins.ksp)
    id(BuildPlugins.kotlinParcelize)
    id(BuildPlugins.kotlinSerialization)
}

val hasGoogleServicesConfig = file("google-services.json").exists()

if (hasGoogleServicesConfig) {
    apply(plugin = BuildPlugins.googleServices)
    apply(plugin = BuildPlugins.crashlytics)
} else {
    logger.warn("google-services.json not found. Skipping Google Services and Crashlytics plugins.")
}

val localProperties = Properties().apply {
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        localPropertiesFile.inputStream().use { load(it) }
    }
}

fun getLocalProperty(key: String, default: String = ""): String =
    localProperties.getProperty(key, default)

android {
    namespace = AppConfig.applicationId
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = AppConfig.applicationId
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = "com.naturaz.bd.runner.NaturazTestRunner"
        vectorDrawables.useSupportLibrary = true

        buildConfigField("String", "API_BASE_URL", "\"${getLocalProperty("API_BASE_URL", "https://api.naturaz.com/")}\"")
        buildConfigField("String", "MAPS_API_KEY", "\"${getLocalProperty("MAPS_API_KEY", "") }\"")
        buildConfigField("String", "BKASH_APP_KEY", "\"${getLocalProperty("BKASH_APP_KEY", "") }\"")
        buildConfigField("String", "NAGAD_APP_KEY", "\"${getLocalProperty("NAGAD_APP_KEY", "") }\"")
        buildConfigField("String", "ROCKET_APP_KEY", "\"${getLocalProperty("ROCKET_APP_KEY", "") }\"")
        buildConfigField("String", "FIREBASE_WEB_CLIENT_ID", "\"${getLocalProperty("FIREBASE_WEB_CLIENT_ID", "") }\"")

        manifestPlaceholders["MAPS_API_KEY"] = getLocalProperty("MAPS_API_KEY", "")
    }

    signingConfigs {
        val keystorePath = System.getenv("KEYSTORE_PATH")
        if (!keystorePath.isNullOrBlank()) {
            create("release") {
                storeFile = file(keystorePath)
                storePassword = System.getenv("KEYSTORE_PASSWORD")
                keyAlias = System.getenv("KEY_ALIAS")
                keyPassword = System.getenv("KEY_PASSWORD")
            }
        }
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            isDebuggable = true
            resValue("string", "app_name", "Naturaz Debug")
            buildConfigField("Boolean", "ENABLE_NETWORK_LOGGING", "true")
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.findByName("release") ?: signingConfigs.getByName("debug")
            resValue("string", "app_name", "Naturaz")
            buildConfigField("Boolean", "ENABLE_NETWORK_LOGGING", "false")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs = freeCompilerArgs + listOf("-Xcontext-receivers")
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeCompiler
    }

    packaging {
        resources.excludes += setOf(
            "/META-INF/{AL2.0,LGPL2.1}",
            "META-INF/LICENSE.txt",
            "META-INF/LICENSE.md",
            "META-INF/NOTICE.txt",
            "META-INF/NOTICE.md"
        )
    }

    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
            it.testLogging {
                events("passed", "skipped", "failed")
            }
        }
    }
}

dependencies {
    implementation(platform(Libs.composeBom))
    implementation(Libs.composeUi)
    implementation(Libs.composeUiGraphics)
    implementation(Libs.composeUiToolingPreview)
    implementation(Libs.composeMaterial3)
    implementation(Libs.composeMaterialIcons)
    implementation(Libs.composeConstraintLayout)
    implementation(Libs.composeWindowSize)
    implementation(Libs.activityCompose)
    implementation(Libs.navigationCompose)
    implementation(Libs.navigationUiKtx)
    implementation(Libs.lifecycleRuntime)
    implementation(Libs.lifecycleRuntimeCompose)
    implementation(Libs.lifecycleViewModel)
    implementation(Libs.lifecycleViewModelCompose)

    implementation(Libs.coreKtx)
    implementation(Libs.coroutinesCore)
    implementation(Libs.coroutinesAndroid)
    implementation(Libs.coroutinesPlayServices)

    implementation(Libs.hiltAndroid)
    implementation(Libs.hiltNavigationCompose)
    ksp(Libs.hiltCompiler)

    implementation(Libs.retrofit)
    implementation(Libs.retrofitSerialization)
    implementation(Libs.okHttp)
    implementation(Libs.okHttpLogging)
    implementation(Libs.serializationJson)

    implementation(Libs.roomRuntime)
    implementation(Libs.roomKtx)
    implementation(Libs.roomPaging)
    ksp(Libs.roomCompiler)

    implementation(Libs.dataStorePreferences)
    implementation(Libs.dataStoreProto)
    implementation(Libs.protobufLite)

    implementation(Libs.coilCompose)
    implementation(Libs.coilGif)

    implementation(Libs.accompanistSystemUi)
    implementation(Libs.accompanistPermissions)

    implementation(Libs.maps)
    implementation(Libs.location)

    implementation(Libs.cameraXCore)
    implementation(Libs.cameraXCamera2)
    implementation(Libs.cameraXLifecycle)
    implementation(Libs.cameraXVideo)
    implementation(Libs.cameraXView)
    implementation(Libs.cameraXExtensions)

    implementation(Libs.workRuntime)
    implementation(Libs.pagingRuntime)
    implementation(Libs.pagingCompose)

    implementation(Libs.firebaseBom)
    implementation(Libs.firebaseAnalytics)
    implementation(Libs.firebaseCrashlytics)
    implementation(Libs.firebaseMessaging)
    implementation(Libs.firebasePerformance)
    implementation(Libs.firebaseConfig)
    implementation(Libs.firebaseAuth)
    implementation(Libs.firebaseUiAuth)
    implementation(Libs.playServicesAuth)

    implementation(Libs.biometric)
    implementation(Libs.securityCrypto)
    implementation(Libs.timber)
    implementation(Libs.ossLicenses)

    debugImplementation(Libs.leakCanary)
    debugImplementation(Libs.chucker)
    debugImplementation(DebugLibs.composeUiTooling)
    debugImplementation(DebugLibs.composeUiTestManifest)
    releaseImplementation(Libs.chuckerNoOp)

    testImplementation(TestLibs.junit5Api)
    testImplementation(TestLibs.junit5Params)
    testRuntimeOnly(TestLibs.junit5Engine)
    testImplementation(TestLibs.mockk)
    testImplementation(TestLibs.coroutinesTest)
    testImplementation(TestLibs.turbine)

    androidTestImplementation(TestLibs.androidJUnitExt)
    androidTestImplementation(TestLibs.androidTestRunner)
    androidTestImplementation(TestLibs.espressoCore)
    androidTestImplementation(TestLibs.composeUiTestJunit4)
    androidTestImplementation(TestLibs.hiltTesting)
    androidTestImplementation(TestLibs.junit5AndroidRunner)
    androidTestRuntimeOnly(TestLibs.junit5AndroidCore)
}
