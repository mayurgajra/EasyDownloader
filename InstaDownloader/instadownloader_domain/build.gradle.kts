apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(Coroutines.coroutines)
    "implementation"("androidx.documentfile:documentfile:1.0.1")
}