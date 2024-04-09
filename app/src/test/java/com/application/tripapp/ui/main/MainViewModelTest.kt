package com.application.tripapp.ui.main

import com.application.tripapp.model.PictureOfTheDay
import com.application.tripapp.usecase.LoadPictureUseCase
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
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel

    private val useCase: LoadPictureUseCase = mockk(relaxed = true)

    @Before
    fun init() {
        viewModel = MainViewModel(useCase)
    }

    @Test
    fun loadPictureTest() = runBlocking {
        val picture = mockk<PictureOfTheDay>(relaxed = true)
        coEvery { useCase.getPictureOfTheDayMain() } returns flowOf(picture)

        viewModel.processAction(MainAction.LoadPicture)

        delay(1000)

        Assert.assertEquals(MainState.PictureLoaded(picture), viewModel.state.value)
    }
}
