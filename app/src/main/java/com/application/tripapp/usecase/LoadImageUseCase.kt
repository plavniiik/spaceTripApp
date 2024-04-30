package com.application.tripapp.usecase

import com.application.tripapp.model.Payload
import com.application.tripapp.model.Picture
import com.application.tripapp.repository.PayloadExperimentRepository
import com.application.tripapp.repository.PictureRepository
import com.application.tripapp.utils.convertPayloadResponseToPayload
import com.application.tripapp.utils.convertPictureResponseToPicture
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoadImageUseCase @Inject constructor(
    private val repository: PictureRepository
) {


    suspend fun getPayloadById(id: String): Flow<Picture?> = flow {
        val response = repository.getPicture(id)
        if (response.isSuccessful) {
            val pictureResponse = response.body()
            if (pictureResponse != null) {
                val picture = convertPictureResponseToPicture(pictureResponse)
                emit(picture.first())
            } else {
                emit(null)
            }
        } else {
            emit(null)
        }
    }


}
