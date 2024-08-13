package com.jgcoding.kotlin.commercelistapp.ui.main

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.jgcoding.kotlin.commercelistapp.R
import com.jgcoding.kotlin.commercelistapp.core.systemdesign.*
import com.jgcoding.kotlin.commercelistapp.domain.model.Commerce
import com.jgcoding.kotlin.commercelistapp.ui.common.*
import com.jgcoding.kotlin.commercelistapp.ui.compose.components.SimpleCard
import com.jgcoding.kotlin.commercelistapp.ui.main.viewmodel.MainViewModel


@Composable
fun MainScreen(
    vm: MainViewModel = hiltViewModel(),
    onCommerceClick: (Commerce) -> Unit
) {
    PermissionRequestEffect(permission = Manifest.permission.ACCESS_COARSE_LOCATION) {
        vm.onUiReady()
    }

    val state by vm.state.collectAsState()
    MainScreen(
        state = state,
        onCommerceClick = onCommerceClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    state: Result<List<Commerce>>,
    onCommerceClick: (Commerce) -> Unit
) {
    val homeState = rememberHomeState()

    Screen {
        MyScaffold(
            state = state,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            text = stringResource(id = R.string.commerces_list)
                        )
                    },
                    scrollBehavior = homeState.scrollBehavior,
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = white,
                        scrolledContainerColor = white
                    )
                )
            },
            modifier = Modifier.nestedScroll(homeState.scrollBehavior.nestedScrollConnection),
            contentWindowInsets = WindowInsets.safeDrawing
        ) { padding, commerces ->

            Column(modifier = Modifier.padding(top = padding.calculateTopPadding())) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .background(color = gray_light),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    SimpleCard(modifier = Modifier.weight(1f), topText = "top", bottomText = "btoom")
                    SimpleCard(modifier = Modifier.weight(1f), topText = "top", bottomText = "btoom")
                }

                LazyVerticalGrid(
                    columns = GridCells.Adaptive(120.dp),
                    contentPadding = padding,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.padding(horizontal = 4.dp)
                ) {
                    items(commerces.sortedBy {
                        it.distance
                    }, key = { it.id }) {
                        CommerceItem(commerce = it) { onCommerceClick(it) }
                    }
                }
            }
        }
    }
}

@Composable
fun CommerceItem(commerce: Commerce, onClick: () -> Unit) {
    Column(
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        CommerceListTheme {
            Box {
                AsyncImage(
                    model = commerce.photo,
                    contentDescription = commerce.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(2 / 3f)
                        .clip(MaterialTheme.shapes.small)
                )
            }
        }
        Text(
            text = commerce.name,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            modifier = Modifier.padding(8.dp)
        )
    }
}