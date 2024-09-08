package com.jgcoding.kotlin.commercelistapp.ui.detail

import app.cash.turbine.test
import com.jgcoding.kotlin.commercelistapp.core.testing.*
import com.jgcoding.kotlin.commercelistapp.domain.usecase.GetCommerceIdUseCase
import com.jgcoding.kotlin.commercelistapp.ui.common.Result.Loading
import com.jgcoding.kotlin.commercelistapp.ui.common.Result.Success
import com.jgcoding.kotlin.commercelistapp.ui.detail.viewmodel.DetailViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.*


class DetailIntegrationTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var vm: DetailViewModel

    @Before
    fun setUp() {
        val repository = buildCommerceRepositoryWith(localData = sampleCommerces(1, 2))
        vm = DetailViewModel(
            2,
            GetCommerceIdUseCase(repository)
        )
    }

    @Test
    fun `UI is updated with the commerce on start`() = runTest {
        vm.state.test {
            assertEquals(Loading, awaitItem())
            assertEquals(Success(sampleCommerce(2)), awaitItem())
        }
    }

}