package com.example.movie.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun indicatorBanner(pagerState: PagerState,modifier: Modifier){
    val count = 4
    val dotWidth =5.dp
    val dotHeight = 5.dp
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            repeat(count) {
                Box(
                    modifier = Modifier
                        .size(width = dotWidth, height = dotHeight)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(5.dp)
                        )
                )
            }
        }

        Box(
            Modifier
                .slidingLineTransition(pagerState, 28f)
                .size(width = dotWidth, height = dotHeight)
                .background(
                    color = Color.Gray,
                    shape = RoundedCornerShape(5.dp),
                )
        )
    }

}


@OptIn(ExperimentalFoundationApi::class)
private fun Modifier.slidingLineTransition(pagerState: PagerState, distance: Float) =
    graphicsLayer {
        val scrollPosition = pagerState.currentPage + pagerState.currentPageOffsetFraction
        translationX = scrollPosition * distance
    }