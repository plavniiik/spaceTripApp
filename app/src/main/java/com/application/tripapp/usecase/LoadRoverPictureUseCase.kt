package com.application.tripapp.usecase

import com.application.tripapp.model.MarsImage
import com.application.tripapp.repository.MarsRoverRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoadRoverPictureUseCase @Inject constructor(
    private val repository: MarsRoverRepository
) {
    suspend fun getPicture(date:String): Flow<List<MarsImage>> = flow {
        val response = repository.getRoverImages(date)
        if (response.isSuccessful) {
            response.body()?.photos?.map { photo ->
                MarsImage(
                    id=photo.id,
                    img_src = photo.img_src,
                    earth_date = photo.earth_date,
                    full_name = photo.camera.full_name,
                    name_rover = photo.rover.name
                )
            }?.let { emit(it) }
        }
    }
}