package com.example.movie.ui.detail

import android.util.Log
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
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.TabRow
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RecentActors
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.SignalWifiStatusbarConnectedNoInternet4
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timelapse
import androidx.compose.material.icons.filled.ViewHeadline
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.movie.R
import com.example.movie.movie.data.remote.models.Episode
import com.example.movie.movie.data.remote.models.ServerData
import com.example.movie.movie.domain.model.Comment
import com.example.movie.movie.domain.model.Movie
import com.example.movie.movie.domain.model.MovieDetail
import com.example.movie.ui.history.MovieHistoryViewmodel
import com.example.movie.ui.home.MovieTab
import com.example.movie.util.K
import com.example.movie.util.K.getRoute
import com.example.movie.util.K.userCurrent
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class,
    ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)

@Composable
fun DetailMovieScreen(slug: String,id:String ,
                      viewModel: MovieDetailViewModel = hiltViewModel(),navController: NavController,viewModelHistory:MovieHistoryViewmodel= hiltViewModel()) {


    // 4.1.11 Hệ thống load giao diện trang chi tiết phim
    val stateMovie by viewModel.stateMovieDetail.collectAsState()
    Log.d("stass","detail")
    var newComment by remember { mutableStateOf("") }

    var isShowDialogLogin by remember { mutableStateOf(false) }


    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()
    val isShow by remember {
        derivedStateOf { scaffoldState.bottomSheetState.isExpanded }
    }

    val sta by remember {
        derivedStateOf { scaffoldState.bottomSheetState.currentValue }
    }
    val stateFavorite by viewModel.favoriteMovieDetail.collectAsState()



    val bg =  Color(0x99DCDFD8).copy(alpha =0.2f)


    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            // 4.1.27 Hệ thống gọi  phương thức getCommentsByMovieId(MovieID) trong MovieDetailViewModel
            LaunchedEffect(Unit)
            {
                viewModel.loadAllComment(id)
            }

            Column(modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp)) {

                val stateCmt by viewModel.cmtList.collectAsState()

                Text("Bình luận về: ", style =
                    MaterialTheme.typography.bodyLarge.copy(color = Color.White, fontWeight = FontWeight.Bold))
                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // 4.1.32 Hiển thị giao diện danh sách các bình luận của bộ phim

                    // 4.1.42 Hiển thị giao diện danh sách các bình luận bao gồm bình luận mơi nhất của bộ phim

                    items(stateCmt.size){
                            index -> CommentItemView(stateCmt[index])
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                val keyboardController = LocalSoftwareKeyboardController.current


                Row(modifier = Modifier.fillMaxWidth()) {
                    // 4.1.33 Người dùng chọn ô nhập nội dung ở dưới cùng của bottomSheet

                    // 4.1.34 Người dùng thực hiện nhập bình luận về bộ phim


                    OutlinedTextField(
                        value = newComment,
                        onValueChange = { newComment = it },
                        placeholder = { Text("Viết bình luận...", color = Color(0xFFCCCFCD)) },
                        modifier = Modifier
                            .weight(1f)
                            .background(color = bg),
                        textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
                    )

                    // 4.1.35 Người dùng nhấn nút “Gửi” kế bên ô nhập nội dung bình luận để thưc hiện bình luận
                    androidx.compose.material.Button(
                        onClick = {

                            // 4.1.36 Hệ thống kiểm tra trạng thái đăng nhập của người dùng qua K.userCurrent


                            if(K.userCurrent == null) {
                                //	4.2.0. Hệ thống phát hiện người dùng chưa đăng nhập
                                isShowDialogLogin = true
                            }else{
                                // 4.1.37  Hệ thống gọi phương thức addComment(id,K.userCurrent.uid,newComment,K.userCurrent?.displayName ?: "Ẩn danh") trong MovieDetailViewModel
                                viewModel.addComment(id,K.userCurrent.uid,newComment,K.userCurrent?.displayName ?: "Ẩn danh")
                                newComment = ""
                                val hide = keyboardController?.hide()
                            }
                        },
                        colors = androidx.compose.material.ButtonDefaults.buttonColors(
                            backgroundColor = Color.Transparent
                        )
                    ) {
                        Icon(imageVector = Icons.Default.Send, contentDescription = null,tint =  Color(0xffFED2E2) )
                    }
                }
            }
        }
    ){

        Box(modifier = Modifier
            .fillMaxSize()
            .background(color = Color.DarkGray)){
            when (stateMovie) {

                is UiState.Default -> {
                    // 4.1.12 Hệ thống gọi phương thức giao getMovieDetailBySlug(slug,idUser) trong MovieDetailViewModel
                    viewModel.getMovieDetailBySlug(slug,id,K.userCurrent?.uid)
                }
                is UiState.Loading -> {
                    // 4.1.14 Hiển thị giao diện trạng thái đang loading

                    Box(modifier = Modifier.fillMaxSize()){
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(30.dp),
                            strokeWidth = 3.dp,
                            color = Color.Red
                        )
                    }
                    Log.d("detail","$slug")
                }


                is UiState.Success<MovieDetail> -> {
                    // 4.1.19 Hệ thống hiển thị giao diện thông tin phim chi tiết

                    val movie = (stateMovie as UiState.Success<MovieDetail>).data
                    DetailMovieView(movie = movie){
                            link ->
                        val encodedLink = URLEncoder.encode(link, StandardCharsets.UTF_8.toString())

                        navController.navigate("play_movie/$encodedLink")
                        val movieHistory = Movie(movie.id,movie.name,movie.slug,movie.thumbUrl,movie.episodeCurrent,movie.poster)
                        viewModelHistory.save(movieHistory)
                    }



                }

                else -> {

                }
            }
            // 4.1.24 Hệ thống hiển thị giao diện thông tin phim yêu thích của bộ phim
            bottomViewDetail(
                Modifier.align(Alignment.BottomCenter), clickComment = {
                    scope.launch {

                        if (isShow)
                        {
                            Log.d("cmt","coll")
                            scaffoldState.bottomSheetState.collapse()
                        } else{
                            Log.d("cmt","Expanded")
                            // 4.1.26 Hiển thị giao diện BottomSheetDialog
                            scaffoldState.bottomSheetState.expand()
                        }
                    }
                }, clickFavorite = {
                    Log.d("favorite","start")
                    viewModel.clickFavorite(K.userCurrent?.uid,id)
                },isFavorite = stateFavorite)



            if(isShowDialogLogin){
                //	4.2.1 Hệ thống hiển thị AlertDialog với nội dung
                //	“Bạn cần đăng nhập để bình luận. Đăng nhập ngay?” với hai nút: “Hủy” và “Đăng nhập”.
                AlertDialog(
                    onDismissRequest = {isShowDialogLogin = false},
                    shape = RoundedCornerShape(20.dp),
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "Warning",
                                tint = Color(0xFFFF9800),
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Bạn chưa đăng nhập!",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    },
                    text = {
                        Text(
                            text = "Bạn cần đăng nhập để thực hiện bình luận. Hãy đăng nhập để tiếp tục.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.DarkGray
                        )
                    },
                    confirmButton = {
                        Button(
                            // 4.2.2. Người dùng nhấn vào nút của alertDialog “Đăng nhập”
                            onClick = {
                                isShowDialogLogin = false
                                // 4.2.3 Hệ thống  chuyển đến trang SiginScreen()
                                navController.navigate("sign_in")
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2))
                        ) {
                            Text("Đăng nhập", color = Color.White)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { isShowDialogLogin = false}) {
                            Text("Hủy", color = Color.Gray)
                        }
                    }
                )
            }

        }


    }
}