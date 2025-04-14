package com.example.nmcnpm_movie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import com.example.nmcnpm_movie.ui.theme.NMCNPM_MovieTheme
import org.w3c.dom.Text

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    val activity:ComponentActivity = this@MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MovieTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val listImg = listOf<Int>(
                        R.drawable.img_2,
                        R.drawable.img_3,
                        R.drawable.img_4,
                        R.drawable.img_5
                    )
                    val navController = rememberNavController()
                    val viewmodel:SiginViewmodel = hiltViewModel()
                    MovieNavGraph(navController,viewmodel,activity)
                }
            }
        }
    }

    override fun onStop() {
        Log.d("stateMain","stop")
        super.onStop()
    }

    override fun onResume() {
        Log.d("stateMain","resume")

        super.onResume()
    }

    override fun onRestart() {
        Log.d("stateMain","restart")

        super.onRestart()
    }

    override fun onDestroy() {
        Log.d("stateMain","destroy")

        super.onDestroy()
    }

    override fun onPause() {
        Log.d("stateMain","pause")

        super.onPause()
    }
}



@Composable
fun MovieNavGraph(navController: NavHostController,viewmodel: SiginViewmodel,activity: ComponentActivity){

    NavHost(navController = navController , startDestination = "home" ){
        composable("home"){
            HomeScreen(navController = navController)
        }

        composable("detail/{slug}", arguments = listOf(navArgument("slug"){
            type = NavType.StringType
        })){
                backStackEntry ->
            val slug = backStackEntry.arguments?.getString("slug") ?: "0"
            DetailMovieScreen(slug, navController = navController)
        }


        composable("play_movie/{link}", arguments = listOf(navArgument("link"){
            type = NavType.StringType })
        ){
                backStackEntry ->
            val link = backStackEntry.arguments?.getString("link") ?: ""

            PlayMovieScreen(link,navController)
        }


        composable(route="search_movie/{search}", arguments = listOf(navArgument("search"){
            type = NavType.StringType })
        ){
                backStackEntry ->
            val query = backStackEntry.arguments?.getString("search") ?: ""

            Log.d("search",query)

            MovieSearchScreen(query = query, navController = navController )


        }

        composable(route="sign_in"){

            signIn(activity = activity, navController = navController)
        }

        composable(route="movie_type/{type}/{slug}",
            arguments = listOf(navArgument("type"){
                type = NavType.StringType }, navArgument("slug"){
                type = NavType.StringType
            })){
                backStackEntry ->
            val type = backStackEntry.arguments?.getString("type") ?: ""
            val slug = backStackEntry.arguments?.getString("slug") ?: ""

            MovieTypeScreen(slug = slug, type = type, navController = navController )

        }

    }

}



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

