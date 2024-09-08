package com.jgcoding.kotlin.commercelistapp.ui.detail

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.jgcoding.kotlin.commercelistapp.domain.model.Commerce
import com.jgcoding.kotlin.commercelistapp.ui.common.Result

@OptIn(ExperimentalMaterial3Api::class)
class DetailState(
    private val state: Result<Commerce>,
    val scrollBehavior: TopAppBarScrollBehavior
) {
    val commerce: Commerce?
        get() = (state as? Result.Success)?.data

    val topBarTitle: String
        get() = commerce?.name ?: ""
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberDetailState(
    state: Result<Commerce>,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
) = remember(state) { DetailState(state, scrollBehavior) }