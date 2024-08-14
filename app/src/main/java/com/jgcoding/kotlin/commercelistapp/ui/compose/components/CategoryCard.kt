package com.jgcoding.kotlin.commercelistapp.ui.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jgcoding.kotlin.commercelistapp.R
import com.jgcoding.kotlin.commercelistapp.core.systemdesign.*

@Composable
fun CategoryCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = white,
    icon: Int,
    text: String,
) {

    Surface(

    ) {
        Row(
            modifier = modifier
                .clip(shape = RoundedCornerShape(16.dp))
                .background(color = backgroundColor)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically, // Centra verticalmente el contenido
            horizontalArrangement = Arrangement.Start // Alinea el contenido al inicio
        ) {
            Image(
                modifier = modifier.height(24.dp).width(24.dp),
                painter = painterResource(id = icon),
                contentDescription = "Icon"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                fontSize = 12.sp
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