plugins {
    id("java")
}

group = "ru.otus.hw"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.springframework:spring-context:6.1.14")
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}


tasks.test {
    useJUnitPlatform()
}