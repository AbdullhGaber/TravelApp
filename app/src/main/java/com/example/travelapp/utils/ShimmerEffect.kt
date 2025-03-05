package com.example.travelapp.utils

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import com.example.travelapp.ui.theme.ShimmerEffectColor


fun Modifier.shimmerEffect() = composed{
    val transition = rememberInfiniteTransition("Shimmer Effect Inf Transition")

    val alphaState = transition.animateFloat(
        label = "Animated shimmer effect color alpha",
        initialValue = .2f,
        targetValue = .4f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
    )

    background(color = ShimmerEffectColor.copy(alpha = alphaState.value))
}