package com.example.mvipractice.core.data.mapper

import com.example.mvipractice.core.data.remote.dto.ImageListDTO
import com.example.mvipractice.core.domain.model.Images

fun ImageListDTO.toImages(): Images {
    return Images(
        images = hits?.map { it.previewURL ?: "" } ?: emptyList()
    )
}