package com.jgcoding.kotlin.commercelistapp.ui.detail

import androidx.annotation.StringRes
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import com.jgcoding.kotlin.commercelistapp.R
import com.jgcoding.kotlin.commercelistapp.core.testing.sampleCommerce
import com.jgcoding.kotlin.commercelistapp.ui.common.LOADING_INDICATOR_TAG
import com.jgcoding.kotlin.commercelistapp.ui.common.Result.*
import org.junit.*

class DetailScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenLoadingState_showProgress(): Unit = with(composeTestRule) {
        setContent {
            DetailScreen(
                state = Loading,
                onBack = {}
            )
        }

        onNodeWithTag(LOADING_INDICATOR_TAG).assertExists()
    }

    @Test
    fun whenErrorState_showError(): Unit = with(composeTestRule) {
        setContent {
            DetailScreen(
                state = Error(RuntimeException("An error occurred")),
                onBack = {}
            )
        }

        onNodeWithText("An error occurred").assertExists()
    }

    @Test
    fun whenSuccessState_commerceIsShown(): Unit = with(composeTestRule) {
        val id = 1
        setContent {
            DetailScreen(
                state = Success(sampleCommerce(id)),
                onBack = {}
            )
        }

        onNodeWithText("Title $id").assertExists()
    }

    @Test
    fun whenBackClicked_listenerIsCalled(): Unit = with(composeTestRule) {
        var clicked = false
        setContent {
            DetailScreen(
                state = Success(sampleCommerce(2)),
                onBack = { clicked = true }
            )
        }

        onNodeWithContentDescription(getStringResource(R.string.back)).performClick()
        Assert.assertTrue(clicked)
    }

}

private fun getStringResource(@StringRes id: Int): String {
    val ctx = InstrumentationRegistry.getInstrumentation().targetContext
    return ctx.getString(id)
}