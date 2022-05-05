apply {
    from("$rootDir/compose-module.gradle")
}

dependencies {
    "implementation"(project(Modules.composeVideoPlayer))

    "implementation"(Coil.coilCompose)
    "implementation"(Coil.coilComposeVideo)
}