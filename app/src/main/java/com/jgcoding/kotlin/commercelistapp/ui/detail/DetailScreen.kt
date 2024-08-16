package com.jgcoding.kotlin.commercelistapp.ui.detail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.jgcoding.kotlin.commercelistapp.R
import com.jgcoding.kotlin.commercelistapp.core.systemdesign.white
import com.jgcoding.kotlin.commercelistapp.domain.model.Commerce
import com.jgcoding.kotlin.commercelistapp.ui.common.*
import com.jgcoding.kotlin.commercelistapp.ui.detail.viewmodel.DetailViewModel

@Composable
fun DetailScreen(vm: DetailViewModel = hiltViewModel(), onBack: () -> Unit) {
    val state by vm.state.collectAsState()

    DetailScreen(
        state = state,
        onBack = onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    state: Result<Commerce>,
    onBack: () -> Unit,
) {
    val detailState = rememberDetailState(state)

    Screen {
        MyScaffold(
            state = state,
            topBar = {
                DetailTopBar(
                    title = detailState.topBarTitle,
                    scrollBehavior = detailState.scrollBehavior,
                    onBack = onBack
                )
            },
            modifier = Modifier.nestedScroll(detailState.scrollBehavior.nestedScrollConnection)
        ) { padding, commerce ->
            MovieDetail(
                commerce = commerce,
                modifier = Modifier.padding(padding)
            )
        }
    }
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun DetailTopBar(
    title: String,
    onBack: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    TopAppBar(
        title = {
            Text(
                text = title
            )
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = white, scrolledContainerColor = white
        )
    )
}

@Composable
private fun MovieDetail(
    commerce: Commerce,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = commerce.photo,
            contentDescription = commerce.name,
            contentScale = ContentScale.Fit,
            placeholder = painterResource(id = R.drawable.placeholder),
            error = painterResource(id = R.drawable.placeholder),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 9f)
        )
        Text(text = commerce.address, modifier = Modifier.padding(16.dp))
        Text(
            text = buildAnnotatedString {
//                Property("Original language", movie.originalLanguage)
//                Property("Original title", movie.originalTitle)
//                Property("Release date", movie.releaseDate)
//                Property("Popularity", movie.popularity.toString())
//                Property("Vote average", movie.voteAverage.toString(), true)
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.secondaryContainer)
                .padding(16.dp)
        )
    }
}

@Composable
private fun AnnotatedString.Builder.Property(name: String, value: String, end: Boolean = false) {
    withStyle(ParagraphStyle(lineHeight = 18.sp)) {
        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
            append("$name: ")
        }
        append(value)
        if (!end) {
            append("\n")
        }
    }
}