package com.example.movie

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movie.ui.category.MovieTypeScreen
import com.example.movie.ui.category.TypeMovieScreen
import com.example.movie.ui.chatbot.ChatbotScreen
import com.example.movie.ui.compile_type.CompileTypeScreen
import com.example.movie.ui.detail.DetailMovieScreen
import com.example.movie.ui.history.MovieHistoryScreen
import com.example.movie.ui.home.MainScreen
import com.example.movie.ui.home.VoiceSearchScreen
import com.example.movie.ui.play_movie.PlayMovieScreen
import com.example.movie.ui.profile.ProfileScreen
import com.example.movie.backend.firebase_service.FacebookAuthHelper
import com.example.movie.ui.search.MovieSearchScreen
import com.example.movie.ui.sigin.SiginViewmodel
import com.example.movie.ui.sigin.SiginScreen

import com.example.movie.ui.theme.MovieTheme
import com.example.movie.ui.theme.Pink40
import com.example.movie.ui.theme.Pink80
import com.example.movie.ui.theme.Purple40
import com.example.movie.ui.theme.Purple80
import com.example.movie.ui.theme.PurpleGrey40
import com.example.movie.ui.theme.PurpleGrey80
import com.example.movie.ui.theme.Typography
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    val activity:ComponentActivity = this@MainActivity
    private lateinit var facebookAuthHelper: FacebookAuthHelper



    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        facebookAuthHelper = FacebookAuthHelper(FirebaseAuth.getInstance())

        Log.d("main","create")
        window.statusBarColor = Color.Black.toArgb()

        setContent {
            MovieThemeCustomer (){

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                    val listImg = listOf<Int>(
                        R.drawable.img_2,
                        R.drawable.img_3,
                        R.drawable.img_4,
                        R.drawable.img_5
                    )
                    val navController = rememberNavController()
                    val viewmodel:SiginViewmodel = hiltViewModel()
                    val navController_child = rememberNavController()

                    MovieNavGraph(navController,viewmodel,activity,facebookAuthHelper,navController_child)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("sss","1")
        facebookAuthHelper.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {


    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun MovieNavGraph(navController: NavHostController,viewmodel: SiginViewmodel,activity: ComponentActivity,facebookAuthHelper: FacebookAuthHelper,navController_child:NavHostController){

// Cho phép nội dung chồng lấn status bar

    NavHost(navController = navController , startDestination = "home",
       ){
        composable("home"){
            MainScreen(navController = navController, navController_child = navController_child)
        }

        composable("chatbot") {
            ChatbotScreen()
        }
        composable("movies") {
            TypeMovieScreen(
                navController,
                moveToTypeMovie = { type, slug ->
                    navController.navigate("movie_type/$type/$slug")
                }
            )
        }
        composable("profile") {
            ProfileScreen(navController)
        }

        composable("detail/{slug}/{id}", arguments = listOf(navArgument("slug"){
            type = NavType.StringType
        },navArgument("id"){
            type = NavType.StringType
        })){
            backStackEntry ->
            val slug = backStackEntry.arguments?.getString("slug") ?: "0"
            val id = backStackEntry.arguments?.getString("id") ?: "0"
            DetailMovieScreen(slug, navController = navController,id = id)
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

        composable(route="compile_type/{slug}", arguments = listOf(navArgument("slug"){
            type = NavType.StringType })
        ){
                backStackEntry ->
            val slug = backStackEntry.arguments?.getString("slug") ?: ""

            Log.d("search",slug)

            CompileTypeScreen(slug = slug, navController = navController )


        }

        composable(route="sign_in"){
                backStackEntry ->

            SiginScreen(viewmodel = viewmodel,activity = activity, navController = navController)
            {
                facebookAuthHelper.loginWithFacebook(activity,onSuccess = {
                    result -> viewmodel.creaditFacebookForFirebase(result,activity)
                },
                onError = {
                    viewmodel.setErrorLogin(it)
                })
            }
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

        composable(route="voice"){
                backStackEntry ->
            VoiceSearchScreen(cancel = {
                navController.popBackStack()
            }){
                search ->
                navController.navigate("search_movie/$search"){
                    popUpTo("voice"){
                        inclusive = true
                    }
                }
            }

        }
        composable(route="movie_history"){
            MovieHistoryScreen(navController= navController)
        }



    }

}
// Extension function để xử lý chuyển tab
fun NavController.navigateTab(index: Int) {
    val route = when(index) {
        0 -> "home"
        1 -> "chatbot"
        2 -> "movies"
        3 -> "profile"
        else -> "home"
    }

    navigate(route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MovieTheme {
        Greeting("Android")
    }

}

@Composable
fun MovieThemeCustomer(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Black.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)


