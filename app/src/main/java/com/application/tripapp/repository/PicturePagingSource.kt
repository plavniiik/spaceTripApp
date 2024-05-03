package com.application.tripapp.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.application.tripapp.model.Picture
import com.application.tripapp.utils.convertPictureResponseToPicture
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import javax.inject.Inject
class PicturePagingSource @AssistedInject constructor(
    private val repository: PictureRepository,
    @Assisted private val keyWord: String
) : PagingSource<Int, Picture>() {
    override fun getRefreshKey(state: PagingState<Int, Picture>): Int? {
        return 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Picture> {
        val nextNumber: Int = params.key ?: 1
        val response = repository.getPictures(keyWord, nextNumber, params.loadSize)
        val pictures = convertPictureResponseToPicture(response.body())
        return LoadResult.Page(
            data = pictures,
            prevKey = null,
            nextKey = nextNumber + 1
        )
    }
}

@AssistedFactory
interface PicturePagingSourceFactory {
    fun create(keyWord: String): PicturePagingSource
}