package com.application.tripapp.utils

import com.application.tripapp.db.PictureEntity
import com.application.tripapp.model.PictureOfTheDay
import com.application.tripapp.network.PictureOfTheDayResponse

fun PictureOfTheDayResponse.toEntity(): PictureEntity {
    return PictureEntity(
        id = "",
        explanation = this.explanation,
        title = this.title,
        url = this.url
    )
}

fun PictureOfTheDayResponse.toPictureOfTheDay(): PictureOfTheDay {
    return PictureOfTheDay(
        explanation = this.explanation,
        title = this.title,
        url = this.url
    )
}

