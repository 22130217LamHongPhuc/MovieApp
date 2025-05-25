package com.example.movie.ui.movie

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.movie.movie.domain.model.Movie
import com.example.movie.movie.domain.model.MovieSearch
import com.example.movie.util.K

@Composable
fun CardMovieBasic(movie: Any, navController: NavController) {
    val widthScreen = (LocalConfiguration.current.screenWidthDp / 3 - 4).dp
    val aspectRatio = 2 / 3f

    val targetWidthPx = with(LocalDensity.current) {
        widthScreen.toPx()
    }.toInt()

    val targetHeightPx = (targetWidthPx * 3 / 2).toInt()

    val imageUrl = when (movie) {
        is Movie -> movie.poster
        is MovieSearch -> K.imgUrl + movie.thumbUrl
        else -> ""
    }

    val movieName = (movie as? Movie)?.name ?: (movie as? MovieSearch)?.name.orEmpty()
    val movieSlug = (movie as? Movie)?.slug ?: (movie as? MovieSearch)?.slug.orEmpty()
    val movieId = (movie as? Movie)?.id ?: (movie as? MovieSearch)?.id
    val episode = (movie as? Movie)?.episodeCurrent ?: (movie as? MovieSearch)?.episodeCurrent.orEmpty()

    ConstraintLayout(
        modifier = Modifier
            .width(widthScreen)
            .aspectRatio(aspectRatio)
            .clickable {

                // 4.1.10 Người dùng nhấn vào bộ thực hiện để thực chuyển sang MovieDetailScreen

                navController.navigate("detail/$movieSlug/$movieId")
            }
    ) {
        val (img, epText, nameText) = createRefs()

        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .size(targetWidthPx,targetHeightPx)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .crossfade(true)
                    .build()
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .constrainAs(img) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(epText.top, margin = 6.dp)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                }
        )

        Text(
            text = episode,
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
            modifier = Modifier.constrainAs(epText) {
                bottom.linkTo(nameText.top, margin = 3.dp)
                start.linkTo(parent.start)
            }
        )

        Text(
            text = movieName,
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.constrainAs(nameText) {
                bottom.linkTo(parent.bottom, margin = 3.dp)
                start.linkTo(parent.start)
            }
        )
    }
}


@Composable
fun CardMovieSearch(movie: MovieSearch, navController: NavController) {
    val widthScreen = (LocalConfiguration.current.screenWidthDp / 3 - 4).dp
    val heightScreen = (LocalConfiguration.current.screenHeightDp / 6).dp
    val targetWidthPx = with(LocalDensity.current) {
        widthScreen.toPx()
    }.toInt()

    val targetHeightPx = (targetWidthPx * 3 / 2).toInt()
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .clickable { navController.navigate("detail/${movie.slug}/${movie.id}") }
    ) {
        val (img, name, episode, info) = createRefs()

        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                .data(K.imgUrl + movie.thumbUrl)
                  .size(targetWidthPx,targetHeightPx)
                .memoryCachePolicy(CachePolicy.ENABLED)  // Bật memory cache
                .diskCachePolicy(CachePolicy.ENABLED)   // Bật disk cache
                .crossfade(false)

                .build()),
            contentDescription = "Movie Thumbnail",
            modifier = Modifier
                .width(widthScreen)
                .height(heightScreen)
                .clip(RoundedCornerShape(10.dp))
                .constrainAs(img) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
            contentScale = ContentScale.Crop
        )
        Text(
            text = movie.name,
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.White, fontFamily = FontFamily.SansSerif),
            modifier = Modifier.constrainAs(name) {
                top.linkTo(img.top, margin = 10.dp)
                start.linkTo(img.end, margin = 10.dp)
            },
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Row(modifier = Modifier.constrainAs(info) {
            start.linkTo(name.start)
            top.linkTo(name.bottom, margin = 10.dp)
        }) {
            Text(text = "${movie.year} | ", style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF2DAA9E)))
            Text(text = "${movie.category} | ", style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF2DAA9E)))
            Text(text = movie.country, style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF2DAA9E)))
        }

        Text(
            text = movie.episodeCurrent,
            style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF2DAA9E)),
            modifier = Modifier.constrainAs(episode) {
                top.linkTo(info.bottom, margin = 10.dp)
                start.linkTo(info.start)
            },
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}


@Composable
fun CardMovieSearch(movie: Movie, navController: NavController) {



    val widthScreen = (LocalConfiguration.current.screenWidthDp / 3 - 4).dp
    val heightScreen = (LocalConfiguration.current.screenHeightDp / 6).dp

    val targetWidthPx = with(LocalDensity.current) {
        widthScreen.toPx()
    }.toInt()

    val targetHeightPx = (targetWidthPx * 3 / 2).toInt()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .clickable { navController.navigate("detail/${movie.slug}/${movie.id}") }
    ) {
        val (img, name, episode, info) = createRefs()

        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(movie.thumbUrl)
                    .size(targetWidthPx,targetHeightPx)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .crossfade(false)

                    .build()),
            contentDescription = "Movie Thumbnail",
            modifier = Modifier
                .width(widthScreen)
                .height(heightScreen)
                .clip(RoundedCornerShape(10.dp))
                .constrainAs(img) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
            contentScale = ContentScale.Crop
        )

        Text(
            text = movie.name,
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.White, fontFamily = FontFamily.SansSerif),
            modifier = Modifier.constrainAs(name) {
                top.linkTo(img.top, margin = 10.dp)
                start.linkTo(img.end, margin = 10.dp)
            },
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Row(modifier = Modifier.constrainAs(info) {
            start.linkTo(name.start)
            top.linkTo(name.bottom, margin = 10.dp)
        }) {
            Text(text = "${movie.episodeCurrent} | ", style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF2DAA9E)))

        }

        Text(
            text = movie.episodeCurrent,
            style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF2DAA9E)),
            modifier = Modifier.constrainAs(episode) {
                top.linkTo(info.bottom, margin = 10.dp)
                start.linkTo(info.start)
            },
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun calculateGridHeight(rows: Int): Dp {
    val itemHeight = 200.dp // Chiều cao mỗi item
    val rowSpacing = 8.dp // Khoảng cách giữa các hàng
    val verticalPadding = 16.dp // Padding top + bottom

    return (itemHeight * rows) +
            (rowSpacing * (rows - 1)) +
            verticalPadding
}
