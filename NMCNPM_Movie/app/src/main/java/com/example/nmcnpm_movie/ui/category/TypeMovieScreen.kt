package com.example.movie.ui.category

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.movie.movie.data.remote.models.Category
import com.example.movie.movie.data.remote.models.Country
import com.example.movie.ui.movie.CardMovieBasic
import com.example.movie.util.K
import kotlinx.serialization.json.Json

@Composable
fun TypeMovieScreen(navController: NavController,viewmodel: ViewmodelCategory = hiltViewModel(), moveToTypeMovie:(String,String) -> Unit) {


    val state = viewmodel.stateHome.collectAsState()
    DisposableEffect(key1 = Unit){
        onDispose {
            Log.d("statsss","dis")
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {


        item {
            Text(
                text = "Lịch sử tìm kiếm",
                style = MaterialTheme.typography.titleMedium.copy(color = Color(0xffE4EFE7))
            )
        }

        item {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 100.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier.height(200.dp) // Đặt chiều cao cố định
            ) {
                items(
                    state.value.history.size,
                    key = { index -> state.value.history.get(index).hashCode() }) { index ->
                    CategoryType(state.value.history[index], "", index == 0) { search ->
                    }
                }
            }
        }

        item {
            Text(
                text = "Thể Loại",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        item {
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier.height(200.dp) // Đặt chiều cao cố định
            ) {
                items(
                    state.value.categories.size,
                    key = { index -> state.value.categories.get(index).hashCode() }) { index ->
                    CategoryType(
                        state.value.categories[index]?.name ?: "",
                        state.value.categories[index]?.slug ?: "",
                        index == 0
                    ) { search ->
                        moveToTypeMovie("category", search)
                    }
                }
            }
        }

        item {
            Text(
                text = "Quốc gia",
                style = MaterialTheme.typography.titleMedium.copy(color = Color.White, fontWeight = FontWeight.Bold)
            )
        }

        item {
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier.height(200.dp) // Đặt chiều cao cố định
            ) {
                items(state.value.countries.size, key = { index -> state.value.countries.get(index).hashCode() }) { index ->

                    Log.d("loadd",index.toString())
                    CategoryType(state.value.countries[index]?.name ?: "",state.value.countries[index]?.slug ?: "", index == 0){
                            search -> moveToTypeMovie("country",search)
                    }
                }
            }
        }
    }
}


@Composable
fun CategoryType(category: String,slug:String,isSeleted:Boolean=false,onSearchByType :(String) -> Unit) {
    val color = if(isSeleted) Color(0xFFA1E256) else Color(0xffFDFAF6)

    val bg =  Color(0x99DCDFD8).copy(alpha =0.2f)

    Box(modifier= Modifier
        .fillMaxSize()
        .background(color = bg, shape = RoundedCornerShape(5.dp))
        .padding(5.dp)
        .clickable {
            onSearchByType(slug)
        }){

        Text(text =category, style = MaterialTheme.typography.bodyMedium.copy(color = color))
    }
}

@Preview
@Composable
fun testt(){

}