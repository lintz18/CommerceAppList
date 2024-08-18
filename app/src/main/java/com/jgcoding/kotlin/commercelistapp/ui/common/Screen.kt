package com.jgcoding.kotlin.commercelistapp.ui.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jgcoding.kotlin.commercelistapp.core.systemdesign.CommerceListTheme
import com.jgcoding.kotlin.commercelistapp.core.systemdesign.orange

@Composable
fun Screen(content: @Composable () -> Unit) {
    CommerceListTheme {

        val systemUiController = rememberSystemUiController()
        val useDarkIcons = false

        systemUiController.setStatusBarColor(
            color = orange,
            darkIcons = useDarkIcons
        )

        Surface(
            modifier = Modifier.fillMaxSize(),
            content = content
        )
    }
}
