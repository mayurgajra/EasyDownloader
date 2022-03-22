package com.mayurg.instadownloader_domain.use_case

import com.mayurg.instadownloader_domain.repository.InstaDownloaderRepository
import javax.inject.Inject

class InstaLoadFiles @Inject constructor(
    instaDownloaderRepository: InstaDownloaderRepository
) {

    operator fun invoke() {

    }
}