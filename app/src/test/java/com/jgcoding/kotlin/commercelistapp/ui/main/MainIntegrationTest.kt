package com.jgcoding.kotlin.commercelistapp.ui.main

import app.cash.turbine.test
import com.jgcoding.kotlin.commercelistapp.core.testing.*
import com.jgcoding.kotlin.commercelistapp.domain.model.Commerce
import com.jgcoding.kotlin.commercelistapp.domain.usecase.GetCommercesUseCase
import com.jgcoding.kotlin.commercelistapp.ui.common.Result
import com.jgcoding.kotlin.commercelistapp.ui.main.viewmodel.MainViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class MainIntegrationTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `data is loaded from server when local source is empty`() = runTest {
        val remoteData = sampleCommerces(1, 2)
        val vm = buildViewModelWith(
            localData = emptyList(),
            remoteData = remoteData
        )

        vm.onUiReady()

        vm.state.test {
            assertEquals(Result.Loading, awaitItem())
            assertEquals(Result.Success(emptyList<Commerce>()), awaitItem())
            assertEquals(Result.Success(remoteData), awaitItem())
        }
    }

    @Test
    fun `data is loaded from local source when available`() = runTest {
        val localData = sampleCommerces(1, 2)
        val vm = buildViewModelWith(localData = localData)

        vm.onUiReady()

        vm.state.test {
            assertEquals(Result.Loading, awaitItem())
            assertEquals(Result.Success(localData), awaitItem())
        }

    }

}

private fun buildViewModelWith(
    localData: List<Commerce> = emptyList(),
    remoteData: List<Commerce> = emptyList()
): MainViewModel {
    val fetchCommerceUseCase = GetCommercesUseCase(buildCommerceRepositoryWith(localData, remoteData))
    return MainViewModel(fetchCommerceUseCase)
}