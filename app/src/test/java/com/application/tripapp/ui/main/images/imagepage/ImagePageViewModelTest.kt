package com.application.tripapp.ui.main.images.imagepage

import com.application.tripapp.model.Picture
import com.application.tripapp.usecase.LoadImageUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ImagePageViewModelTest {
    private lateinit var viewModel: ImagePageViewModel

    private val useCase: LoadImageUseCase = mockk(relaxed = true)

    @Before
    fun init() {
        viewModel = ImagePageViewModel(useCase)
    }

    @Test
    fun loadPictureTest() = runBlocking {
        val pictureId = "PIA22197"
        val picture = mockk<Picture>(relaxed = true)
        coEvery { useCase.getPictureById(pictureId) } returns flowOf(picture)

        viewModel.processAction(ImagePageAction.LoadPictures(pictureId))

        delay(1000)

        Assert.assertEquals(ImagePageState.PictureLoaded(picture), viewModel.state.value)
    }
}