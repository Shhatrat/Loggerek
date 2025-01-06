package com.shhatrat.loggerek.base

import androidx.compose.foundation.shape.GenericShape
import androidx.compose.ui.graphics.Shape

object SoftRoundedShape {

    fun get(): Shape {
        return GenericShape { size, _ ->
            moveTo(0f, 0f)
            lineTo(0f, size.height - size.height / 7)
            quadraticBezierTo(size.width / 2, size.height, size.width, size.height - size.height / 7)
            lineTo(size.width, 0f)
            close()
        }
    }
}