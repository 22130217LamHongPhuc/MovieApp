package com.example.movie.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.speech.RecognizerIntent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.sharp.PlayCircle
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.movie.R
import com.example.movie.movie.domain.model.CompileTypeItem
import com.example.movie.movie.domain.model.Movie
import com.example.movie.movie.domain.model.MovieSearch
import com.example.movie.ui.category.TypeMovieScreen
import com.example.movie.ui.chatbot.ChatbotScreen
import com.example.movie.ui.indicatorBanner
import com.example.movie.ui.movie.CardMovieBasic
import com.example.movie.ui.movie.calculateGridHeight
import com.example.movie.ui.profile.ProfileScreen
import com.example.movie.util.K
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.ExperimentalComposeUiApi
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

@RequiresApi(Build.VERSION_CODES.R)
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable


fun MainScreen(viewModel: HomeViewModel = hiltViewModel(), navController: NavController, navController_child: NavHostController) {

    // 4.1.0 Người dùng thực hiện truy cập vào trang MainScreen
    // 4.1.1 Khởi tạo giao diện trang MainScreen
    LaunchedEffect(Unit) {
        // 4.1.2 Hệ thống thực hiện gọi phương thức getDataHomeMovie() trong HomeViewModel
        viewModel.getDataHomeMovie()
    }


    
    val statePager = rememberPagerState(0) {
        4
    }


    val selectedTab = rememberSaveable { mutableIntStateOf(0) }

    val backStackEntry by navController_child.currentBackStackEntryAsState()
    val scope = rememberCoroutineScope()

    val currentRoute by remember(backStackEntry) {
        mutableStateOf(backStackEntry?.destination?.route)
    }

    val context = LocalContext.current as Activity
    val window = context.window



    val saveableStateHolder = rememberSaveableStateHolder()


    Log.d("NavController", "Current route: ${navController.currentBackStackEntry?.destination?.route}")
    LaunchedEffect(currentRoute) {
        selectedTab.intValue = when (currentRoute) {
            "home_child" -> 0
            "type_movie" -> 1
            "chatbot" -> 2
            "profile" -> 3
            else -> 0
        }
    }
    Scaffold(
        modifier = Modifier.background(Color.Black),
        bottomBar = {
            BottomAppbar(selectedTab.intValue){
                    selectedIndex ->
                scope.launch {
                    statePager.animateScrollToPage(selectedIndex)
                }

            }
        }, containerColor = Color.Black
    ) {
            paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(paddingValues)){
            HorizontalPager(state = statePager, modifier = Modifier.fillMaxSize(),
                key = {
                        index ->  index.hashCode()
                }
                ,
                beyondBoundsPageCount = 1) {
                    page ->
                when(page){
                    0 -> saveableStateHolder.SaveableStateProvider("home") {


                        Home(navController = navController) { slugType ->
                            navController.navigate("compile_type/$slugType")
                        }
                    }
                    1 ->  TypeMovieScreen(navController) { type, slug ->
                        navController.navigate("movie_type/$type/$slug")
                    }
                    2 -> saveableStateHolder.SaveableStateProvider("profile") {


                        saveableStateHolder.SaveableStateProvider("chatbot") {
                            ChatbotScreen()
                        }
                    }
                    3 ->saveableStateHolder.SaveableStateProvider(key = "type") {
                        ProfileScreen(navController)
                    }
                }
            }

        }
    }
}
