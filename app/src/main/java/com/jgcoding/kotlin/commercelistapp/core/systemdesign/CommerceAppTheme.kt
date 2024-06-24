package com.jgcoding.kotlin.commercelistapp.core.systemdesign

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun CommerceListTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        // Colors
        LocalAppColors provides appColors,

        // Content
        content = content
    )
}

object CommerceListTheme {

    // region Colors

    val textColors: AppColors
        @Composable
        get() = LocalAppColors.current

    // endregion Colors

}