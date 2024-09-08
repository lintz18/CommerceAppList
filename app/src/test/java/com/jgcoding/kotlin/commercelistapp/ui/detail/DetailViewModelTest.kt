package com.jgcoding.kotlin.commercelistapp.ui.detail

import app.cash.turbine.test
import com.jgcoding.kotlin.commercelistapp.core.testing.CoroutinesTestRule
import com.jgcoding.kotlin.commercelistapp.core.testing.sampleCommerce
import com.jgcoding.kotlin.commercelistapp.domain.usecase.GetCommerceIdUseCase
import com.jgcoding.kotlin.commercelistapp.ui.common.Result
import com.jgcoding.kotlin.commercelistapp.ui.detail.viewmodel.DetailViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var getCommerceByIdUseCase: GetCommerceIdUseCase

    private lateinit var vm: DetailViewModel

    private val commerce = sampleCommerce(1)

    @Before
    fun setUp() {
        whenever(getCommerceByIdUseCase(1)).thenReturn(flowOf(commerce))
        vm = DetailViewModel(2, getCommerceByIdUseCase)
    }

    @Test
    fun `UI is updated with the commerce on start`() = runTest {
        vm.state.test {
            assertEquals(Result.Loading, awaitItem())
            assertEquals(Result.Success(commerce), awaitItem())
        }
    }
}