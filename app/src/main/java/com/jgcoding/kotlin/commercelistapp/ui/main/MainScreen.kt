package com.jgcoding.kotlin.commercelistapp.ui.main

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.jgcoding.kotlin.commercelistapp.R
import com.jgcoding.kotlin.commercelistapp.core.systemdesign.*
import com.jgcoding.kotlin.commercelistapp.domain.model.Commerce
import com.jgcoding.kotlin.commercelistapp.ui.common.*
import com.jgcoding.kotlin.commercelistapp.ui.compose.components.CategoryCard
import com.jgcoding.kotlin.commercelistapp.ui.compose.components.SimpleCard
import com.jgcoding.kotlin.commercelistapp.ui.main.viewmodel.MainViewModel
import kotlinx.coroutines.launch


@Composable
fun MainScreen(
    vm: MainViewModel = hiltViewModel(),
    onCommerceClick: (Commerce) -> Unit
) {
    PermissionRequestEffect(permission = Manifest.permission.ACCESS_COARSE_LOCATION) {
        vm.onUiReady()
    }

    val state by vm.state.collectAsState()
    val filteredCommerces by vm.filteredCommerces.collectAsState()
    val categoriesList = vm.categoriesList
    MainScreen(
        state = state,
        commerces = filteredCommerces,
        categoriesList = categoriesList,
        onCommerceClick = onCommerceClick,
        onCategoryClick = vm::onCategoryClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    state: Result<List<Commerce>>,
    commerces: List<Commerce>,
    categoriesList: List<String>,
    onCommerceClick: (Commerce) -> Unit,
    onCategoryClick: (String) -> Unit
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
        ) { padding, _ ->
            Column(modifier = Modifier.padding(top = padding.calculateTopPadding())) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp, start = 16.dp, end = 16.dp)
                        .background(color = gray_light),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    SimpleCard(
                        modifier = Modifier.weight(1f),
                        topText = "${commerces.size}",
                        bottomText = stringResource(id = R.string.commerces)
                    )
                    SimpleCard(
                        modifier = Modifier.weight(1f),
                        backgroundColor = white,
                        topText = getNearPlaces(commerces),
                        topTextColor = orange,
                        bottomText = stringResource(id = R.string.near_1km),
                        bottomTextColor = black
                    )
                }

                LazyRow(
                    contentPadding = PaddingValues(top = 24.dp, start = 16.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(categoriesList) {
                        CategoryCard(backgroundColor = white, icon = R.drawable.cart_colour, text = it) { category ->
                            onCategoryClick(category)
                        }
                    }
                }

                LazyVerticalGrid(
                    columns = GridCells.Adaptive(120.dp),
                    contentPadding = PaddingValues(top = 24.dp, start = 16.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.padding(horizontal = 4.dp)
                ) {
                    items(commerces.sortedBy {
                        it.distance
                    }, key = { it.id }) {
                        CommerceItem(commerce = it) {
                            onCommerceClick(it)
                        }
                    }
                }
            }
        }
    }
}

private fun getNearPlaces(commerces: List<Commerce>): String {
    var counter = 0
    for (commerce in commerces) {
        if (commerce.checkDistance()) {
            counter++
        }
    }
    return "$counter"
}

private fun filterByCategory(commerces: Result<List<Commerce>>, category: String) {
    if (commerces is Result.Success) {
        commerces.data.filter {
            it.category.contains(category)
        }
    }
//    return commerces.filter {
//        it.category.contains(category)
//    }
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
                    contentScale = ContentScale.Fit,
                    placeholder = painterResource(id = R.drawable.placeholder),
                    error = painterResource(id = R.drawable.placeholder),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.small)
                )
            }
        }
        Text(
            text = commerce.name,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}