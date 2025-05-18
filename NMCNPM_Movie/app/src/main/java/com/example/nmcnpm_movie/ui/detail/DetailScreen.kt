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


@Composable
fun bottomViewDetail(modifier: Modifier,clickComment :() -> Unit,clickFavorite: () -> Unit,isFavorite : Boolean) {

    Row(modifier = modifier
        .background(Color.DarkGray)
        .padding(10.dp)
        .fillMaxWidth()
        .wrapContentHeight()) {
        Spacer(modifier = Modifier.width(5.dp))

        // 4.1.25 Người dùng chọn vào icon bình luận ở góc dưới cùng của chi tiết phim
        Icon(imageVector = Icons.Default.Comment, contentDescription = null,tint = Color.White,
            modifier = Modifier
                .size(45.dp)
                .clickable {
                    clickComment()
                })
        Spacer(modifier = Modifier.width(10.dp))



        Icon(imageVector = Icons.Default.Favorite, contentDescription = null,
            modifier = Modifier
                .size(45.dp).
                let {
                    if (!isFavorite) {
                        it.clickable {
                            clickFavorite()
                        }
                    } else {
                        it
                    }
                },
            tint = if (isFavorite) Color.Red else Color.White,
        )
        Spacer(modifier = Modifier.width(10.dp))

        Button(onClick = {}, colors = ButtonDefaults.buttonColors(
            containerColor = Color.Red
        ),
            modifier =  Modifier.fillMaxWidth()
        ) {
            Text(text = "Xem ngay",style = MaterialTheme.typography.bodyLarge.copy(Color.White))
        }

    }
}

@Composable
fun DetailMovieView(movie: MovieDetail,onClickChapterMovie :(String)->Unit) {

    var isLoadInfor by remember {
        mutableStateOf(true)
    }

    ConstraintLayout(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.DarkGray)
        .padding(bottom = 15.dp)) {
        val (bgRef, Fg, titleRef, movieInforRef,tabRef,chapterRef) = createRefs()
        val barrier = createGuidelineFromTop(0.08f)

        BackGroundPoster(
            thumbnail = movie.thumbUrl,
            modifier = Modifier.constrainAs(bgRef) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        )

        ForegroundPoster(
            poster = movie.poster,
            modifier = Modifier.constrainAs(Fg) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(barrier)

            }
        )



        Text(
            text = movie.name,
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .constrainAs(titleRef) {
                    start.linkTo(Fg.start)
                    end.linkTo(Fg.end)
                    bottom.linkTo(Fg.bottom, margin = 10.dp)
                    width = Dimension.fillToConstraints
                },
            fontSize = 20.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif
        )

        val tabs = listOf("Thông tin","Tập phim")


        MovieTabDetail(tabs, modifier = Modifier.constrainAs(tabRef){
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(Fg.bottom)
            width = Dimension.fillToConstraints
        }){
                bool ->
            isLoadInfor = bool
        }

        if(isLoadInfor){
            LoadInforMovieDetail(movie = movie, modifier = Modifier
                .constrainAs(movieInforRef) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(tabRef.bottom, margin = 10.dp)
                    bottom.linkTo(parent.bottom)

                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                })
        }else{
            LoadEposideMovieDetail(movie,modifier = Modifier
                .fillMaxWidth()
                .constrainAs(chapterRef) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(tabRef.bottom, margin = 10.dp)
                    bottom.linkTo(parent.bottom)

                    width = Dimension.fillToConstraints

                    height = Dimension.fillToConstraints
                }){
                    link -> onClickChapterMovie(link)
            }
        }
    }

}



@Composable
fun ForegroundPoster(poster: String, modifier: Modifier) {


    val width = LocalConfiguration.current.screenWidthDp / 2
    val height = LocalConfiguration.current.screenHeightDp / 4

    val targetWidthPx = with(LocalDensity.current) {
        width.dp.toPx()
    }.toInt()


    val targetHeightPx = (targetWidthPx * 3 / 2).toInt()
    Box(
        modifier = modifier
            .width(width.dp)
            .height(height.dp)
            .clip(RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(poster)
                    .size(targetWidthPx,targetHeightPx)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .crossfade(true)
                    .build()

            ), contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color.Transparent,
                            Color.Transparent,
                            Color(0xB91A1B1B),
                        )
                    ), shape = RoundedCornerShape(16.dp)
                )
        )
    }
}


@Composable
fun BackGroundPoster(thumbnail: String, modifier: Modifier) {
    val height = LocalConfiguration.current.screenHeightDp / 3
    val width = LocalConfiguration.current.screenWidthDp

    val targetWidthPx = with(LocalDensity.current) {
        width.dp.toPx()
    }.toInt()

    val targetHeightPx = (targetWidthPx * 3 / 2).toInt()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height.dp)
            .background(color = Color.Transparent),
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(thumbnail)
                    .size(targetWidthPx,targetHeightPx)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .crossfade(true)
                    .build()
            ),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(height.dp)
                .alpha(0.6f),
            contentScale = ContentScale.Crop
        )

    }

}

@Composable
fun Rating(movie: MovieDetail, modifier: Modifier) {
    Row(
        modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = modifier.width(25.dp))
        Icon(imageVector = Icons.Default.Schedule, contentDescription = "", tint = Color.White)
        Text(
            text = movie.episode_total+" tập",
            modifier.padding(start = 6.dp),
            color = Color.White
        )
        Spacer(modifier = modifier.width(25.dp))
        Icon(
            imageVector = Icons.Default.Timelapse,
            contentDescription = "",
            tint = Color.White
        )
        Text(
            text = movie.timeMovie,
            modifier.padding(start = 6.dp),
            color = Color.White
        )
        Spacer(modifier = modifier.width(25.dp))
        Icon(imageVector = Icons.Filled.DateRange, contentDescription = "", tint = Color.White)
        Text(
            text = movie.yearProduct.toString(),
            modifier.padding(start = 6.dp),
            color = Color.White
        )
    }
}

@Composable
fun TextBuilder(icon: ImageVector, title: String, bodyText: String) {
    Row(modifier = Modifier.padding(vertical = 10.dp, horizontal = 10.dp)) {
        Icon(
            imageVector = icon,
            contentDescription = "Person",
            tint = Color.White
        )
        Text(
            text = title,
            Modifier.padding(start = 10.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
    Text(text = bodyText, color = Color.White, modifier = Modifier.padding(horizontal = 10.dp))
}


@Composable
fun MovieTabDetail(tabs: List<String>, modifier: Modifier = Modifier,isLoadInfor: (Boolean) -> Unit) {
    var selectedTabIndex by remember {
        mutableStateOf(0)
    }

    LaunchedEffect(key1 = selectedTabIndex){
        if(selectedTabIndex == 0){
            isLoadInfor(true)
        }else{
            isLoadInfor(false)
        }
    }

    androidx.compose.material3.TabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier.fillMaxWidth(),
        containerColor  = Color(0xFF302E2E),
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier
                    .tabIndicatorOffset(tabPositions[selectedTabIndex])
                    .height(2.dp),
                color = Color.Red
            )
        }) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { selectedTabIndex = index },
                text = {
                    Text(
                        title,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal
                        ))
                },
                selectedContentColor = Color.White,
                unselectedContentColor = Color(0xFF999292),

                )
        }

    }


}


@Composable
fun LoadInforMovieDetail(movie: MovieDetail,modifier: Modifier){

    LazyColumn(
        modifier =modifier,
    ) {
        item {

            Rating(movie = movie, modifier = Modifier)
        }
        item{
            TextBuilder(
                icon = Icons.Filled.Info,
                title = "Mô tả:",
                bodyText = movie.description
            )
        }


        item{
            TextBuilder(
                icon = Icons.Filled.Person,
                title = "Diễn viên:",
                bodyText = movie.actors.joinToString(", ")
            )
        }

        item{
            TextBuilder(
                icon = Icons.Filled.RecentActors,
                title = "Đạo diễn:",
                bodyText = movie.director.joinToString(", ")
            )
        }

        item{
            TextBuilder(
                icon = Icons.Filled.Category,
                title = "Thể loại:",
                bodyText = movie.categorys.map { item -> item.name }.joinToString(", ")
            )
        }

        item{
            TextBuilder(
                icon = Icons.Filled.ViewHeadline,
                title = "Quốc gia:",
                bodyText = movie.country.map { item -> item.name }.joinToString(", ")
            )
        }


    }

}


@Composable
fun LoadEposideMovieDetail(movie: MovieDetail, modifier: Modifier,onClickChapterMovie :(String) -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize() // Giới hạn chiều cao
    ) {
        EpisodeView(episode = movie.episodes[0]){
                link -> onClickChapterMovie(link)
        }
        Spacer(modifier = Modifier.height(10.dp))



        if(movie.episodes.size>1){
            EpisodeView(episode = movie.episodes[1]){
                    link -> onClickChapterMovie(link)
            }
        }
    }
}


@Composable
fun EpisodeView(episode: Episode,onClickChapterMovie :(String) -> Unit) {
    Column() {

        Text(text = episode?.serverName ?: "",
            style = MaterialTheme.typography.bodyMedium.
            copy(color = Color.White, fontFamily = FontFamily.SansSerif))

        Spacer(modifier = Modifier.height(10.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(6),
            modifier = Modifier.wrapContentHeight(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),

            ) {

            episode.serverData?.let {
                items(it){
                        item -> EpisodeViewItem(item){
                        link -> onClickChapterMovie(link)
                }
                }
            }
        }
    }
}

@Composable
fun EpisodeViewItem(item: ServerData?,onClickChapterMovie :(String) -> Unit) {
    // 1.11 goi phuong thuc onClickChapterMovie()

    Box(modifier = Modifier
        .size(30.dp)
        .background(color = Color.Blue, shape = RoundedCornerShape(5.dp))
        .clickable {
            onClickChapterMovie(item?.linkM3u8 ?: "")
        },
        contentAlignment = Alignment.Center
    ){
        var number:String?

        try{
            number  = item?.name?.substring(item?.name?.lastIndexOf(" ") ?: 0)?.trim()

        }catch (e:Exception){
            number = item?.name
        }

        Text(text = number.toString(),style = TextStyle.Default.copy(color = Color.White), textAlign = TextAlign.Center)
    }
}


@Composable
fun CommentItemView(comment: Comment){
    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()) {


        val (imgRef,nameRef,contentRef,dateRef) = createRefs()
        Image(painter = painterResource(id = R.drawable.img_2),
            contentDescription = null, modifier = Modifier
                .size(45.dp)
                .clip(CircleShape)
                .constrainAs(imgRef)
                {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                },
            contentScale = ContentScale.Crop)

        Text(text = comment.userName,style = MaterialTheme.typography.bodyMedium.copy(color = Color(
            0xFFC8CECA
        )
        ),
            modifier = Modifier.constrainAs(nameRef){
                start.linkTo(imgRef.end, margin = 10.dp)
                end.linkTo(parent.end, margin = 10.dp)
                top.linkTo(imgRef.top)
                width = Dimension.fillToConstraints
            })


        Text(text = comment.content,style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xffFDFAF6)),
            modifier = Modifier.constrainAs(contentRef){
                start.linkTo(nameRef.start)
                end.linkTo(nameRef.end)
                top.linkTo(nameRef.bottom,margin = 5.dp)
                width = Dimension.fillToConstraints
            })
        val dateFormat = comment.formatDayCmt()

        Text(text = dateFormat,style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFFC8CECA)),
            modifier = Modifier.constrainAs(dateRef){
                start.linkTo(nameRef.start)
                top.linkTo(contentRef.bottom,margin = 5.dp)
            })
    }
}