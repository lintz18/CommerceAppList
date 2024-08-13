package com.jgcoding.kotlin.commercelistapp.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jgcoding.kotlin.commercelistapp.R
import com.jgcoding.kotlin.commercelistapp.core.systemdesign.gray_light

@Composable
fun <T> MyScaffold(
    state: Result<T>,
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    containerColor: Color = gray_light,
    contentColor: Color = contentColorFor(containerColor),
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    content: @Composable (PaddingValues, T) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = topBar,
        bottomBar = bottomBar,
        snackbarHost = snackbarHost,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        containerColor = containerColor,
        contentColor = contentColor,
        contentWindowInsets = contentWindowInsets,
    ) { padding ->
        when (state) {
            is Result.Loading -> {
                LoadingIndicator(modifier = Modifier.padding(padding))
            }

            is Result.Error -> {
                ErrorText(
                    error = state.exception,
                    modifier = Modifier.padding(padding)
                )
            }

            is Result.Success -> {
                content(padding, state.data)
            }
        }
    }
}

@Composable
fun ErrorText(error: Throwable, modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.error) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = stringResource(id = R.string.error_connectivity),
                modifier = Modifier.size(72.dp)
            )
            Text(
                text = error.localizedMessage ?: "An error occurred",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall,
            )
        }
    }
}