package com.jgcoding.kotlin.commercelistapp.ui.compose.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.jgcoding.kotlin.commercelistapp.core.systemdesign.black
import com.jgcoding.kotlin.commercelistapp.core.systemdesign.white


@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(navController: NavHostController, title: String, isBack: Boolean){
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    var isDrawerOpen = remember {
        mutableStateOf(false)
    }
    TopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = white,
            titleContentColor = black,
        ),
        title = {
            Text(modifier = Modifier.fillMaxWidth(),
                text = title,
                color = Color.Black,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        },
        modifier = Modifier.background(Color.White),
        navigationIcon = {
            if (isBack){
                IconButton(onClick = {navController.navigateUp()}) {
                    Icon(Icons.Filled.ArrowBack, "backIcon")
                }
            }
        }
    )
}