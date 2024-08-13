package com.jgcoding.kotlin.commercelistapp.ui.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jgcoding.kotlin.commercelistapp.core.systemdesign.CommerceListTheme

@Composable
fun Screen(content: @Composable () -> Unit) {
    CommerceListTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            content = content
        )
    }
}
