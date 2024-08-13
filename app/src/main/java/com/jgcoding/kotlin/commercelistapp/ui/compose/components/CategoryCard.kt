package com.jgcoding.kotlin.commercelistapp.ui.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jgcoding.kotlin.commercelistapp.R
import com.jgcoding.kotlin.commercelistapp.core.systemdesign.dark_blue
import com.jgcoding.kotlin.commercelistapp.core.systemdesign.white

@Composable
fun CategoryCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = white,
    icon: Int,
    text: String,
) {

    Surface(

    ) {
        Box(
            modifier = modifier
                .background(
                    color = backgroundColor, // Color de fondo de la Box
                    shape = RoundedCornerShape(16.dp) // Borde redondeado
                )
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            Image(painter = painterResource(id = icon), contentDescription = "Icon")
            Text(
                text = text,
                fontSize = 16.sp
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun CategoryCard() {
    CategoryCard(
        icon = R.drawable.cart_colour,
        text = "Category Text"
    )
}