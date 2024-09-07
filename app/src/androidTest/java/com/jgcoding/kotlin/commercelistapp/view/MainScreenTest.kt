package com.jgcoding.kotlin.commercelistapp.view

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.jgcoding.kotlin.commercelistapp.core.testing.sampleCommerces
import com.jgcoding.kotlin.commercelistapp.ui.common.LOADING_INDICATOR_TAG
import com.jgcoding.kotlin.commercelistapp.ui.common.Result
import com.jgcoding.kotlin.commercelistapp.ui.main.MainScreen
import org.junit.*

class MainScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenLoadingState_showProgress(): Unit = with(composeTestRule) {
        setContent {
            MainScreen(
                state = Result.Loading,
                scrollToTop = true,
                commerces = listOf(),
                categoriesList = listOf(),
                onCategoryClick = {},
                onCommerceClick = {},
                onUpdateScroll = {}
            )
        }

        onNodeWithTag(LOADING_INDICATOR_TAG).assertExists()
    }

    @Test
    fun whenErrorState_showError(): Unit = with(composeTestRule) {
        setContent {
            MainScreen(
                state = Result.Error(RuntimeException("An error occurred")),
                scrollToTop = true,
                commerces = listOf(),
                categoriesList = listOf(),
                onCategoryClick = {},
                onCommerceClick = {},
                onUpdateScroll = {}
            )
        }

        onNodeWithText("An error occurred").assertExists()
    }

    @Test
    fun whenSuccessState_commercesAreShown(): Unit = with(composeTestRule) {
        setContent {
            MainScreen(
                state = Result.Success(sampleCommerces(1,2,3)),
                scrollToTop = true,
                commerces = sampleCommerces(1,2,3),
                categoriesList = listOf(),
                onCategoryClick = {},
                onCommerceClick = {},
                onUpdateScroll = {}
            )
        }

        onNodeWithText("Title 2").assertExists()
    }

    @Test
    fun whenCommerceClicked_listenerIsCalled(): Unit = with(composeTestRule) {
        var clickedCommerceId = -1
        val commerces = sampleCommerces(1,2,3)
        setContent {
            MainScreen(
                state = Result.Success(commerces),
                scrollToTop = true,
                commerces = commerces,
                categoriesList = listOf(),
                onCategoryClick = {},
                onCommerceClick = {
                    clickedCommerceId = it.id
                },
                onUpdateScroll = {}
            )
        }

        onNodeWithText("Title 2").performClick()

        Assert.assertEquals(2, clickedCommerceId)
    }
}