package com.example.mvipractice.core.data.remote.dto

data class ImageListDTO(
    val hits: List<ImageDTO>?,
    val total: Int?,
    val totalHits: Int?
)
