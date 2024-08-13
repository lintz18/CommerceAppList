package com.jgcoding.kotlin.commercelistapp.ui.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jgcoding.kotlin.commercelistapp.core.systemdesign.*

@Composable
fun SimpleCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = dark_blue,
    topTextColor: Color = white,
    bottomTextColor: Color = white,
    topText: String,
    bottomText: String
    ) {
    Box(
        modifier = modifier
            .background(
                color = backgroundColor, // Color de fondo de la Box
                shape = RoundedCornerShape(16.dp) // Borde redondeado
            )
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.Start // Alinear horizontalmente los textos
        ) {
            Text(
                text = topText,
                fontSize = 20.sp,
                color = topTextColor
            )
            Text(
                text = bottomText,
                fontSize = 16.sp,
                color = bottomTextColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RoundedBoxWithTextsPreview() {
    SimpleCard(
        backgroundColor = dark_blue,
        topTextColor = white,
        bottomTextColor = white,
        topText = "Top Text",
        bottomText = "Bottom Text"
    )
}