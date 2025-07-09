package com.squrlabs.sca.domain.model.user

data class UploadResponse(
    val fileName: String = "",
    val fileUrl: String = "",
    val status: String = "",
    val detail: String = ""
)