package com.jgcoding.kotlin.commercelistapp.core.systemdesign

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import javax.annotation.concurrent.Immutable

val black: Color
    get() = Color(0xFF000000)
val white: Color
    get() = Color(0xFFFFFFFF)
val gray_light: Color
    get() = Color(0xFFf8f8f8)
val dark_blue: Color
    get() = Color(0xFF041e42)
val orange: Color
    get() = Color(0xFFff6100)
val orange_light: Color
    get() = Color(0xFFff8200)
val yellow: Color
    get() = Color(0xFFffc800)
val blue: Color
    get() = Color(0XFF004a8c)
val gray_text_color: Color
    get() = Color(0xFF464646)
val purple: Color
    get() = Color(0xFFFF9438)

@Immutable
data class AppColors(
    val normalText: Color,
    val normalTextWhite: Color,
    val normalTextDark: Color,
    val normalTextBlue: Color,
    val normalTextOrange: Color,
    val normalTextPurple: Color,
    val normalTextYellow: Color,
    val titleTextDark: Color,
    val background: Color,
    val backgroundDark: Color
)

val LocalAppColors = staticCompositionLocalOf {
    AppColors(
        normalText = Color.Unspecified,
        normalTextWhite = Color.Unspecified,
        normalTextDark = Color.Unspecified,
        normalTextBlue = Color.Unspecified,
        normalTextOrange = Color.Unspecified,
        normalTextPurple = Color.Unspecified,
        normalTextYellow = Color.Unspecified,
        titleTextDark = Color.Unspecified,
        background = Color.Unspecified,
        backgroundDark = Color.Unspecified
    )
}

val appColors = AppColors(
    normalText = black,
    normalTextWhite = white,
    normalTextDark = gray_text_color,
    normalTextBlue = blue,
    normalTextOrange = orange,
    normalTextPurple = purple,
    normalTextYellow = yellow,
    titleTextDark = gray_text_color,
    background = white,
    backgroundDark = dark_blue
)