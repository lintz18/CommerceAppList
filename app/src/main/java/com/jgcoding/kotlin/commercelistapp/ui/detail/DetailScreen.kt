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
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
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
            CommerceDetail(
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
private fun CommerceDetail(
    commerce: Commerce,
    modifier: Modifier = Modifier
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(commerce.location.second, commerce.location.first), 15f) // Inverted Lat Long in API service
    }

    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        AsyncImage(
            model = commerce.photo,
            contentDescription = commerce.name,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.placeholder),
            error = painterResource(id = R.drawable.placeholder),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 10f)
        )
        Text(
            text = buildAnnotatedString {
                Property("Servicio", commerce.name)
                Property("Dirección", commerce.address)
                Property("Categoría", commerce.category)
                Property("Horario", commerce.openingHours)
                Property("Localización", commerce.location.toString())
                Property("Cashback", "${commerce.cashback}%", true)
            },
            modifier = Modifier.padding(16.dp)
        )
        GoogleMap(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            cameraPositionState = cameraPositionState,
        ) {
            Marker(
                state = rememberMarkerState(position = LatLng(commerce.location.second, commerce.location.first)),
                title = commerce.name
            )
        }
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