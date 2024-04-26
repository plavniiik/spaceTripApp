package com.application.tripapp.usecase

import com.application.tripapp.db.PictureEntity
import com.application.tripapp.model.PictureOfTheDay
import com.application.tripapp.repository.FireBaseRepository
import com.application.tripapp.repository.PictureOfTheDayRepository
import com.application.tripapp.utils.toEntity
import com.application.tripapp.utils.toPictureOfTheDay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import javax.inject.Inject

class LoadPictureUseCase @Inject constructor(
    private val repository: PictureOfTheDayRepository,
    private val fireRepository: FireBaseRepository
) {
    suspend fun getPictureOfTheDayMain(): Flow<PictureOfTheDay> = flow {
        val response = repository.getPicture()
        if (response.isSuccessful) {
            response.body()?.toPictureOfTheDay()?.run {
                emit(this)
            }
        }
    }

    suspend fun getPicture(): Flow<PictureEntity> = flow {
        val response = repository.getPicture()
        if (response.isSuccessful) {
            response.body()?.let {
                val picture = PictureEntity(
                    id = fireRepository.getPictureId(response.body()!!),
                    explanation = it.explanation,
                    title = it.title,
                    url = it.url
                )
                emit(picture)
            }
        }
    }.take(10)

}
