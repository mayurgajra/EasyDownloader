apply {
    from("$rootDir/compose-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.coreUi))
    "implementation"(project(Modules.instaDownloaderDomain))
    "implementation"(project(Modules.fileManager))

    "implementation"(Coil.coilCompose)
}