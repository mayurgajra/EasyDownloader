package com.mayurg.instadownloader_domain.use_case

data class InstaLoaderUseCases(
    val instaLoadFiles: InstaLoadFiles,
    val instaSaveDirectoryUri: InstaSaveDirectoryUri,
    val instaGetDirectoryUri: InstaGetDirectoryUri
)