import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    application
    kotlin("jvm") version "1.5.21"
    kotlin("plugin.spring") version "1.5.21"
    id("org.springframework.boot") version "2.1.6.RELEASE"
}

group = "com.htmake"
version = "3.2.14"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
}

// The reference artifact predates every BOM that would otherwise decide these
// versions. Pin them to what BOOT-INF/lib actually shipped instead of letting
// a transitive POM silently move the runtime.
configurations.all {
    resolutionStrategy {
        eachDependency {
            when {
                requested.group == "io.netty" -> {
                    useVersion("4.1.42.Final")
                    because("reader-pro-3.2.14.jar ships the Vert.x 3.8.5 Netty line")
                }
                requested.group == "com.fasterxml.jackson.core" -> {
                    useVersion(if (requested.name == "jackson-annotations") "2.9.0" else "2.9.9")
                    because("reader-pro-3.2.14.jar pairs jackson-databind 2.9.9 with jackson-module-kotlin 2.13.5")
                }
            }
        }
        force(
            // Every one of these resolves higher from a transitive POM otherwise.
            "org.jetbrains.kotlin:kotlin-stdlib:1.5.21",
            "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.5.21",
            "org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.5.21",
            "org.jetbrains.kotlin:kotlin-reflect:1.5.21",
            "org.jetbrains.kotlin:kotlin-stdlib-common:1.5.30",
            "org.slf4j:slf4j-api:1.7.26",
            "org.apache.commons:commons-lang3:3.8.1"
        )
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter:2.1.6.RELEASE")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.21")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:1.5.2")

    // Vert.x 3.8.5 HTTP/WebSocket service
    implementation("io.vertx:vertx-core:3.8.5")
    implementation("io.vertx:vertx-web:3.8.5")
    implementation("io.vertx:vertx-web-client:3.8.5")
    implementation("io.vertx:vertx-auth-common:3.8.5")
    implementation("io.vertx:vertx-lang-kotlin:3.8.5")
    implementation("io.vertx:vertx-lang-kotlin-coroutines:3.8.5")

    // serialization
    implementation("com.google.code.gson:gson:2.8.5")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.5")

    // logging
    implementation("io.github.microutils:kotlin-logging:1.6.24")
    implementation("uk.org.lidalia:sysout-over-slf4j:1.0.2")

    implementation("com.google.guava:guava:28.0-jre")

    // HTTP client stack
    implementation("com.squareup.okhttp3:okhttp:4.9.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.1.0")
    implementation("com.squareup.retrofit2:retrofit:2.6.1")
    implementation("com.julienviet:retrofit-vertx:1.1.3")

    // Rule-engine script host. Neither artifact is published for this coordinate
    // in a usable form, so the exact jars from the reference artifact are vendored.
    implementation(fileTree("src/lib") { include("rhino-*.jar") })
    implementation(fileTree("src/lib") { include("xmlpull-*.jar") })

    // source-rule parsing
    implementation("org.jsoup:jsoup:1.14.1")
    implementation("cn.wanghaomiao:JsoupXpath:2.5.0")
    implementation("com.jayway.jsonpath:json-path:2.6.0")
    implementation("org.antlr:antlr4-runtime:4.7.2")

    // crypto helpers
    implementation("cn.hutool:hutool-core:5.8.0.M1")
    implementation("cn.hutool:hutool-crypto:5.8.0.M1")

    implementation("org.mongodb:mongodb-driver-sync:3.8.2")
    implementation("org.apache.pdfbox:pdfbox:2.0.27")

    testImplementation("org.springframework.boot:spring-boot-starter-test:2.1.6.RELEASE")
}

// 恢复期编译引导。传 -PrestoreBootstrap=<解包后的 BOOT-INF/classes 目录> 时，把参考制品的
// 应用类与内嵌依赖挂到 compileOnly 上：尚未迁入的类从制品解析，已迁入的类由本仓库源码提供。
// 这样每一小批源码都能独立编译、独立核验。不传该参数时制品完全不参与构建。
val restoreBootstrap: File? =
    (project.findProperty("restoreBootstrap") as String?)?.let { file(it) }
if (restoreBootstrap != null) {
    require(restoreBootstrap.isDirectory) {
        "-PrestoreBootstrap 必须指向解包后的 BOOT-INF/classes 目录"
    }
    val bootstrapLib = restoreBootstrap.parentFile.resolve("lib")
    dependencies {
        compileOnly(files(restoreBootstrap))
        if (bootstrapLib.isDirectory) {
            compileOnly(fileTree(mapOf("dir" to bootstrapLib, "include" to "*.jar")))
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xjsr305=strict", "-Xsam-conversions=indy")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    // Restoration surfaces many errors at once; javac's default cap of 100 hides the rest.
    options.compilerArgs.addAll(listOf("-Xmaxerrs", "2000"))
}

application {
    mainClassName = "com.htmake.reader.ReaderApplicationKt"
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    mainClassName = "com.htmake.reader.ReaderApplicationKt"
    isPreserveFileTimestamps = false
    isReproducibleFileOrder = true
    manifest.attributes(
        mapOf("Implementation-Title" to "Reader Pro", "Implementation-Version" to project.version.toString())
    )
}

// The reference artifact's BOOT-INF/lib is the contract for the dependency set.
// This task fails the build when the resolved runtime classpath drifts from it;
// -PallowParityDrift downgrades a mismatch to a warning while migrating.
tasks.register("verifyDependencyParity") {
    group = "verification"
    description = "Verify the runtime classpath matches the reference artifact's BOOT-INF/lib."

    val recordedList = layout.projectDirectory.file("docs/dependencies.txt")
    val allowDrift = project.hasProperty("allowParityDrift")

    // Gradle's okio module metadata declares the JVM artifact as
    // okio-jvm-2.8.0.jar but downloads it as okio-2.8.0.jar. Same bytes; the
    // artifact name is what the reference JAR recorded.
    val declaredNames = mapOf("okio-2.8.0.jar" to "okio-jvm-2.8.0.jar")

    doLast {
        val expected = recordedList.asFile.readLines().map(String::trim).filter(String::isNotEmpty).toSortedSet()

        val resolved = configurations.getByName("runtimeClasspath").resolvedConfiguration
        val resolvedNames = resolved.resolvedArtifacts
            .map { "${it.name}-${it.moduleVersion.id.version}.jar" }
            .map { declaredNames[it] ?: it }
            .toSortedSet()
        val localNames = resolved.files
            .filter { it.extension == "jar" }
            .map { it.name }
            .filter { it.startsWith("rhino-") || it.startsWith("xmlpull-") }
            .toSortedSet()

        val actual = (resolvedNames + localNames).toSortedSet()
        val missing = expected - actual
        val extra = actual - expected

        logger.lifecycle("recorded artifacts : {}", expected.size)
        logger.lifecycle("resolved artifacts : {}", actual.size)
        if (missing.isNotEmpty()) {
            logger.lifecycle("missing ({}):", missing.size)
            missing.forEach { logger.lifecycle("  - {}", it) }
        }
        if (extra.isNotEmpty()) {
            logger.lifecycle("extra ({}):", extra.size)
            extra.forEach { logger.lifecycle("  + {}", it) }
        }
        if (missing.isEmpty() && extra.isEmpty()) {
            logger.lifecycle("runtime classpath matches the reference artifact exactly")
        } else if (allowDrift) {
            logger.warn("runtime classpath differs from the reference artifact (allowed by -PallowParityDrift)")
        } else {
            throw GradleException(
                "runtime classpath differs from the reference artifact; " +
                    "update docs/dependencies.txt only with an evidence-backed reason"
            )
        }
    }
}

tasks.named("check") {
    dependsOn("verifyDependencyParity")
}
