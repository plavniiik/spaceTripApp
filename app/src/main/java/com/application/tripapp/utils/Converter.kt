package com.application.tripapp.utils

import com.application.tripapp.db.PictureEntity
import com.application.tripapp.model.Asteroid
import com.application.tripapp.model.Payload
import com.application.tripapp.model.Picture
import com.application.tripapp.model.PictureOfTheDay
import com.application.tripapp.network.PictureOfTheDayResponse
import com.application.tripapp.network.asteroid.NearEarthObjects
import com.application.tripapp.network.payload.PayloadResponse
import com.application.tripapp.network.picture.PictureResponse
import kotlin.math.round

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

fun convertNearEarthObjectsToAsteroid(nearEarthObjects: NearEarthObjects): Asteroid {

    val estimatedDiameterMax =  round(nearEarthObjects.estimated_diameter.meters.estimated_diameter_max * 100) / 100
    val estimatedDiameterMin =  round(nearEarthObjects.estimated_diameter.meters.estimated_diameter_min * 100) / 100

    return Asteroid(
        id = nearEarthObjects.id.toInt(),
        nasa_jpl_url = nearEarthObjects.nasa_jpl_url,
        name = nearEarthObjects.name,
        absolute_magnitude_h = round(nearEarthObjects.absolute_magnitude_h * 100) / 100,
        is_potentially_hazardous_asteroid = nearEarthObjects.is_potentially_hazardous_asteroid,
        estimated_diameter_max = estimatedDiameterMax,
        estimated_diameter_min = estimatedDiameterMin,
        close_approach_date = nearEarthObjects.close_approach_data
    )
}

fun convertPayloadResponseToPayload(payloadResponse: PayloadResponse): Payload {

    return Payload(
        id = payloadResponse.id,
        description = payloadResponse.description,
        files = payloadResponse.files,
        identifier = payloadResponse.identifier,
        identifierLowercase = payloadResponse.identifierLowercase,
        payloadName = payloadResponse.payloadName,
        people = payloadResponse.people,
        type = payloadResponse.type
    )
}

fun convertPictureResponseToPicture(pictureResponse: PictureResponse?): List<Picture> {
    if (pictureResponse != null) {
        return pictureResponse.collection.items.mapNotNull { item ->
            if (item.data != null && item.links != null) {
                Picture(
                    data = item.data,
                    href = item.href,
                    links = item.links,
                    id = item.data[0].nasa_id,
                    title = item.data[0].title,
                    type = item.data[0].media_type,
                    description = item.data[0].description,
                    link = item.links[0].href
                )
            } else null
        }
    } else return emptyList()
}


