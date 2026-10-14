plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "fr.dappli.androidsandbox"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "fr.dappli.androidsandbox"
        minSdk = 29
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    sourceSets {
        getByName("main") {
            // Ship the docs as assets so screens can render them.
            assets.directories.add(rootProject.file("doc").path)
            // Ship the sources as assets so screens can show their own code (see AssetCode).
            assets.directories.add(file("src/main/java").path)
        }
    }
    buildFeatures {
        compose = true
    }
}

composeCompiler {
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
    metricsDestination = layout.buildDirectory.dir("compose_compiler")
}

val strongSkippingPages = tasks.register<StrongSkippingPagesTask>("strongSkippingPages") {
    sourceDir = rootProject.layout.projectDirectory
        .dir("lib/nostrongskipping/src/main/java/fr/dappli/nostrongskipping/page")
}

androidComponents {
    onVariants { variant ->
        variant.sources.kotlin?.addGeneratedSourceDirectory(
            strongSkippingPages,
            StrongSkippingPagesTask::outputDir
        )
    }
}

dependencies {
    implementation(project(":lib:common"))
    implementation(project(":lib:nostrongskipping"))
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.markdown.renderer.m3)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}

/**
 * Copies the :lib:nostrongskipping pages into another package, so :app compiles them
 * a second time, with strong skipping on (the default). Compose decides skipping and
 * memoization at compile time, so the same source behaves differently in each module.
 */
abstract class StrongSkippingPagesTask : DefaultTask() {
    @get:InputDirectory
    abstract val sourceDir: DirectoryProperty

    @get:OutputDirectory
    abstract val outputDir: DirectoryProperty

    @TaskAction
    fun copy() {
        val target = outputDir.get().dir("fr/dappli/androidsandbox/compose/strongskipping/page").asFile
        outputDir.get().asFile.deleteRecursively()
        target.mkdirs()
        sourceDir.get().asFile.listFiles { file -> file.extension == "kt" }!!.forEach { file ->
            val source = file.readText().replace(
                "package fr.dappli.nostrongskipping.page",
                "package fr.dappli.androidsandbox.compose.strongskipping.page"
            )
            target.resolve(file.name).writeText(source)
        }
    }
}
