package com.jgcoding.kotlin.commercelistapp.ui.main

import app.cash.turbine.test
import com.jgcoding.kotlin.commercelistapp.core.testing.CoroutinesTestRule
import com.jgcoding.kotlin.commercelistapp.core.testing.sampleCommerces
import com.jgcoding.kotlin.commercelistapp.domain.usecase.GetCommercesUseCase
import com.jgcoding.kotlin.commercelistapp.ui.common.Result
import com.jgcoding.kotlin.commercelistapp.ui.main.viewmodel.MainViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var fetchCommercesUseCase: GetCommercesUseCase

    private lateinit var vm: MainViewModel

    @Before
    fun setUp() {
        vm = MainViewModel(fetchCommercesUseCase)
    }

    @Test
    fun `Commerces are not requested if UI is not ready`() = runTest {
        vm.state.first()
        runCurrent()

        verify(fetchCommercesUseCase, times(0)).invoke()
    }

    @Test
    fun `Commerces are requested if UI is ready`() = runTest {
        val commerces = sampleCommerces(1, 2, 3)
        whenever(fetchCommercesUseCase()).thenReturn(flowOf(commerces))

        vm.onUiReady()

        vm.state.test {
            assertEquals(Result.Loading, awaitItem())
            assertEquals(Result.Success(commerces), awaitItem())
        }
    }

    @Test
    fun `Error is propagated when request fails`() = runTest {
        val error = RuntimeException("Exception")
        whenever(fetchCommercesUseCase()).thenThrow(error)

        vm.onUiReady()

        vm.state.test {
            assertEquals(Result.Loading, awaitItem())
            val exceptionMessage = (awaitItem() as Result.Error).exception.message
            assertEquals("Exception", exceptionMessage)
        }
    }
}