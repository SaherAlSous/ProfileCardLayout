package com.saher.profilecardlayout.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

//My Colors
val veryLightGray = Color(0x60DCDCDC)
val lightGreen = Color(0x9932CD32)

//We can get the color using a custom getter that is Composable.
val Colors.lightGreen: Color
    @Composable
    get() = lightGreen