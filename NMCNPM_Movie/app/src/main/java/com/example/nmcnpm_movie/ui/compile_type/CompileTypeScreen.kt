package com.example.movie.ui.compile_type

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration

import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movie.movie.domain.model.MovieDetail
import com.example.movie.movie.domain.model.MovieSearch
import com.example.movie.ui.detail.UiState
import com.example.movie.ui.movie.CardMovieBasic
import com.example.movie.ui.movie.calculateGridHeight


@Composable
fun CompileTypeScreen(slug: String, navController: NavController,viewmodel: CompileTypeVM= hiltViewModel()) {
    val state = viewmodel.searchMoviePagingFlow.collectAsLazyPagingItems()

    Log.d("ffff", "$slug")
    LaunchedEffect(slug) {
        Log.d("ffff", "$slug")
        viewmodel.setSlug(slug)
    }

              Box(modifier = Modifier.fillMaxSize().background(Color.Black)){
              if(state.itemCount==0){
                  CircularProgressIndicator(
                      modifier = Modifier.size(30.dp),
                      strokeWidth = 3.dp,
                      color = Color.Red
                  )
              }else{
                  LazyVerticalGrid(
                      columns = GridCells.Fixed(3),
                      modifier = Modifier
                          .padding(top = 5.dp)
                          .background(Color.Black)
                          .fillMaxWidth()
                          .height(calculateGridHeight(8)),
                      verticalArrangement = Arrangement.spacedBy(8.dp),
                      horizontalArrangement = Arrangement.spacedBy(8.dp),
                      contentPadding = PaddingValues(8.dp)

                  ) {

                      items(count = state.itemCount, key = { index -> index.hashCode()}) { index ->
                          state[index]?.let { CardMovieBasic(movie = it,navController) }
                      }




                  }
              }
              }

}