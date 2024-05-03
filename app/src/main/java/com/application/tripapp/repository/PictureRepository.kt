package com.application.tripapp.repository

import com.application.tripapp.network.PictureApi
import javax.inject.Inject

class PictureRepository  @Inject constructor(
    private val api: PictureApi
){
    suspend fun getPictures(keyWord:String, page:Int, pageSize:Int) = api.getImages(keyWord,page,pageSize)
    suspend fun getPicture(keyWord:String) = api.getImage(keyWord)
}