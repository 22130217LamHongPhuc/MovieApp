package com.example.movie.ui.history

import com.example.movie.ui.search.MovieSearchViewModel


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ClearAll
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.RemoveDone
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movie.ui.movie.CardMovieBasic
import com.example.movie.ui.movie.CardMovieSearch

@Composable
fun MovieHistoryScreen(viewmodel: MovieHistoryViewmodel = hiltViewModel(),navController:NavController){


    val moviePagingItems = viewmodel.history.collectAsState()




    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Black)){
        LazyColumn (modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)){
            Log.d("search size",moviePagingItems.value.size.toString())
            item{
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp), horizontalArrangement = Arrangement.SpaceBetween){
                    Text(text = "Đã xem",
                        style = MaterialTheme.typography.titleMedium.copy(color = Color.White, fontWeight = FontWeight.Bold))

                    Icon(imageVector = Icons.Default.ClearAll, contentDescription = "Clear",tint = Color.White, modifier =
                    Modifier.clickable {
                        viewmodel.clearAll()
                    })
                }
            }
            items(moviePagingItems.value.size) {
                    index -> moviePagingItems.value[index]?.let {
                Log.d("search","index $index")
                CardMovieSearch(movie = it, navController = navController)
            }
            }

        }
    }


}