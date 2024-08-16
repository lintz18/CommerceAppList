package com.jgcoding.kotlin.commercelistapp.ui.main

import android.Manifest
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
import org.checkerframework.checker.units.qual.s


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
    val scrollToTop by vm.scrollToTop.collectAsState()
    MainScreen(
        state = state,
        scrollToTop = scrollToTop,
        commerces = filteredCommerces,
        categoriesList = categoriesList,
        onCommerceClick = onCommerceClick,
        onCategoryClick = vm::onCategoryClick,
        onUpdateScroll = vm::updateScrollToTop
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    state: Result<List<Commerce>>,
    scrollToTop: Boolean,
    commerces: List<Commerce>,
    categoriesList: List<String>,
    onCommerceClick: (Commerce) -> Unit,
    onCategoryClick: (String) -> Unit,
    onUpdateScroll: (Boolean) -> Unit
) {
    val homeState = rememberHomeState()
    val listState = rememberLazyGridState()
    val coroutine = rememberCoroutineScope()

    Screen {
        MyScaffold(
            state = state, topBar = {
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
                        containerColor = white, scrolledContainerColor = white
                    )
                )
            }, modifier = Modifier.nestedScroll(homeState.scrollBehavior.nestedScrollConnection), contentWindowInsets = WindowInsets.safeDrawing
        ) { padding, _ ->

            LaunchedEffect(key1 = scrollToTop) {
                coroutine.launch {
                    if (scrollToTop == true) {
                        listState.scrollToItem(0)
                        onUpdateScroll(false)
                    }
                }
            }

            var selectedIndex by remember { mutableStateOf(-1) }

            Column(modifier = Modifier.padding(top = padding.calculateTopPadding())) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp, start = 16.dp, end = 16.dp)
                        .background(color = gray_light),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    SimpleCard(
                        modifier = Modifier.weight(1f), topText = "${commerces.size}", bottomText = stringResource(id = R.string.commerces)
                    )
                    SimpleCard(
                        modifier = Modifier.weight(1f), backgroundColor = white, topText = getNearPlaces(commerces), topTextColor = orange, bottomText = stringResource(id = R.string.near_1km), bottomTextColor = black
                    )
                }

                LazyRow(
                    contentPadding = PaddingValues(top = 24.dp, start = 16.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    itemsIndexed(categoriesList) { index, it ->
                        CategoryCard(
                            modifier = Modifier.animateItemPlacement(),
                            selected = selectedIndex == index,
                            backgroundColor = white,
                            icon = getCategoryIcon(it),
                            iconColor = getCategoryColor(it),
                            text = it
                        ) { category ->
                            selectedIndex = index
                            onCategoryClick(category)
                            onUpdateScroll(true)
                        }
                    }
                }

                Spacer(modifier = Modifier.padding(top = 24.dp))
                LazyVerticalGrid(
                    state = listState,
                    columns = GridCells.Adaptive(120.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
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

@Composable
fun CommerceItem(commerce: Commerce, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .clip(shape = RoundedCornerShape(16.dp))
            .background(color = white)
    ) {
        CommerceListTheme {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                AsyncImage(
                    model = commerce.photo,
                    contentDescription = commerce.name,
                    contentScale = ContentScale.Fit,
                    placeholder = painterResource(id = R.drawable.placeholder),
                    error = painterResource(id = R.drawable.placeholder),
                    modifier = Modifier
                        .width(width = 80.dp)
                        .height(height = 80.dp)
                        .clip(MaterialTheme.shapes.small)
                )
            }
        }
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                modifier = Modifier.height(20.dp).width(20.dp),
                painter = painterResource(id = commerce.getIconCategory()),
                contentDescription = "CategoryImage",
                colorFilter = ColorFilter.tint(commerce.getCategoryColor())
            )
            Text(
                text = commerce.name,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
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

private fun getCategoryIcon(category: String): Int {
    return when (category.lowercase()) {
        "all" -> R.drawable.placeholder
        "beauty" -> R.drawable.cart_colour
        "direct_sales" -> R.drawable.placeholder
        "electric_station" -> R.drawable.electric_scootter_colour
        "food" -> R.drawable.catering_colour
        "gas_station" -> R.drawable.ees_colour
        "leisure" -> R.drawable.car_wash_colour
        "shopping" -> R.drawable.cart_colour
        else -> R.drawable.placeholder
    }
}

fun getCategoryColor(category: String): Color {
    return when (category.lowercase()) {
        "all" -> dark_blue
        "beauty" -> purple
        "direct_sales" -> orange_light
        "electric_station" -> yellow
        "food" -> gray_text_color
        "gas_station" -> blue
        "leisure" -> orange
        "shopping" -> gray_text_color
        else -> black
    }
}