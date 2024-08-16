package com.jgcoding.kotlin.commercelistapp.ui.compose.components

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jgcoding.kotlin.commercelistapp.R
import com.jgcoding.kotlin.commercelistapp.core.systemdesign.*

@Composable
fun CategoryCard(
    modifier: Modifier = Modifier,
    selected : Boolean = false,
    backgroundColor: Color = white,
    icon: Int,
    iconColor: Color,
    text: String,
    onClick: (String) -> Unit
) {
    val rowBackgroundColor = if (selected) Color.LightGray else backgroundColor

    Surface(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .background(color = transparent),
    ) {
        Row(
            modifier = modifier
                .clip(shape = RoundedCornerShape(16.dp))
                .background(color = rowBackgroundColor)
                .padding(12.dp)
                .clickable {
                    onClick(text)
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,

            ) {
            Image(
                modifier = modifier
                    .height(24.dp)
                    .width(24.dp),
                painter = painterResource(id = icon),
                contentDescription = "Icon",
                colorFilter = ColorFilter.tint(iconColor)
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
        iconColor = black,
        text = "Category Text",
        onClick = {}
    )
}