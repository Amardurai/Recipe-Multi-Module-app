package com.example.common.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.common.R

@Composable
fun AnimatedPreloader(modifier: Modifier = Modifier) {
    val lottieComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.not_found_lottie))


    val clipSpecs = LottieClipSpec.Progress(0f, 1f)

    LottieAnimation(
        modifier = modifier,
        composition = lottieComposition,
        iterations = 1,
        clipSpec = clipSpecs,
    )
}