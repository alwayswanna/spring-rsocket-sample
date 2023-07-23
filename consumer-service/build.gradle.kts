plugins {
    id("org.springframework.boot")
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.serialization")
}

val kotlinMuLoggingVersion = "3.0.5"

dependencies {
    /* spring */
    implementation("org.springframework.boot:spring-boot-starter-rsocket")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")

    /* db */
    implementation("org.flywaydb:flyway-core")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.postgresql:r2dbc-postgresql")

    /* kotlin */
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    /* other */
    implementation("io.github.microutils:kotlin-logging-jvm:$kotlinMuLoggingVersion")
    implementation(project(mapOf("path" to ":rsocket-common")))
    implementation("org.apache.poi:poi-ooxml:5.2.3")

    /* test */
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}