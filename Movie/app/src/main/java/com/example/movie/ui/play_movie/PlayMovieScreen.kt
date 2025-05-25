package com.example.movie.ui.play_movie

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Build
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.activity.compose.BackHandler
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import com.example.movie.ui.history.MovieHistoryViewmodel
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@SuppressLint("SuspiciousIndentation")
@OptIn(UnstableApi::class)
@Composable
fun PlayMovieScreen(encodedLink: String, navController: NavController) {
    //1.12 khoi tao giao dien PlayMovieScreen()
    val link = remember { URLDecoder.decode(encodedLink, StandardCharsets.UTF_8.toString()) }
    val context = LocalContext.current as Activity
    val window = context.window

    var fullScreen by remember { mutableStateOf(false) }
    var isFirst by remember { mutableStateOf(true) }
    var isPlayerVisible by remember { mutableStateOf(true) }


    Log.d("player", link)
//    window.statusBarColor = Color.Black.toArgb()
//    WindowCompat.setDecorFitsSystemWindows(window, false)
    val trackSelector = remember { DefaultTrackSelector(context) }



    //1.13 khoi tao ExoPlayer
    //1.14 ExoPlayer load du lieu phim tu link
    val exoPlayer = remember {
        ExoPlayer.Builder(context).setTrackSelector(trackSelector).build().apply {
            val dataSourceFactory = DefaultHttpDataSource.Factory()
            val mediaSource: MediaSource = HlsMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(link))
            setMediaSource(mediaSource)
            prepare()
            playWhenReady = true
        }
    }




    LaunchedEffect(fullScreen) {
        Log.d("stass screen :","1")
       if(isFirst){
           isFirst = false
       }else{
           Log.d("stass screen :","2")

           context.requestedOrientation = if (fullScreen)
               ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

           else
               ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
       }
    }
    DisposableEffect(Unit) {
        onDispose {

            exoPlayer.stop()
            exoPlayer.clearMediaItems()
            exoPlayer.release()
            Log.d("ExoPlayer", "Released")
       }
    }



    BackHandler {
        if (fullScreen) {
            fullScreen = false
        } else {
            isPlayerVisible = false // Ẩn UI player
            navController.popBackStack()
        }
    }

    val height = if (fullScreen) LocalConfiguration.current.screenHeightDp.dp
    else (LocalConfiguration.current.screenHeightDp / 3).dp

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .background(Color.Black)
        ) {
            if (isPlayerVisible) {

                //1.14 hien thi phim len giao dien
                AndroidView(
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(if (isPlayerVisible) 1f else 0f),
                    factory = { ctx ->
                        PlayerView(ctx).apply {
                            player = exoPlayer
                            useController = true
                            setControllerShowTimeoutMs(3000) // Hide controls after 3s
                            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
                            setFullscreenButtonClickListener { isFullScreen ->
                                if (fullScreen != isFullScreen) {
                                    fullScreen = isFullScreen
                                }
                            }
                        }
                    }
                )
            }
        }
}

fun hideSystemUI(window: android.view.Window) {
    WindowCompat.setDecorFitsSystemWindows(window, false)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.insetsController?.apply {
            hide(WindowInsets.Type.systemBars())
            systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    } else {
        window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }
}

fun showSystemUI(window: android.view.Window) {
    WindowCompat.setDecorFitsSystemWindows(window, true)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.insetsController?.show(WindowInsets.Type.systemBars())
    } else {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
    }
}
