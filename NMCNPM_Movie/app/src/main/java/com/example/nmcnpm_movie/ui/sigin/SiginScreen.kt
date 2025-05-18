package com.example.movie.ui.sigin

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.movie.R
import com.example.movie.ui.detail.UiState
import kotlinx.coroutines.launch

@Composable
fun SiginScreen(viewmodel: SiginViewmodel = hiltViewModel(), activity: ComponentActivity, navController: NavController, loginFacebook:()-> Unit) {


    val stateSignin = viewmodel.stateSignin.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    // 4.2.4 Hệ thống hiển thị giao diện đăng nhập

    val signInLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                coroutineScope.launch {
                    viewmodel.handleSignInResult(result.data)
                }
            }
        }

    val launcherFb = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
    }


    when(stateSignin.value){
        // 4.2.8 Hiển thị giao diện trạng thái đang loading

        is UiState.Loading -> {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(30.dp),
                    strokeWidth = 3.dp,
                    color = Color.Red
                )
            }
        }
        is UiState.Success -> {
            val state = (stateSignin.value as UiState.Success<String>).data
            Log.d("ppp","success")

            // 4.2.12 Hiển thị thông báo đăng nhập thành công

            Toast.makeText(activity,"Đăng nhập thành công",Toast.LENGTH_SHORT).show()

            // 4.2.13 Hệ thống chuyển về trang chi tiết phim lúc trước để có thể tiếp tục thực hiện chức năng bình luận

            navController.popBackStack()


        }
        else -> {

        }

    }
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (bgRef,logoRef,formRef) = createRefs()
        Background(Modifier.constrainAs(bgRef){
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end) })

        Logo(Modifier.constrainAs(logoRef){
            top.linkTo(parent.top, margin = 50.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })

        FormSignin(Modifier.constrainAs(formRef){
            top.linkTo(logoRef.bottom, margin = 50.dp)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)

            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints

        }, signinWithEmail = {
                email,pass ->
            coroutineScope.launch {
                // 4.2.6 Hệ thống thực hiện gọi phương thức siginWithEmail(email,pass) trong SiginViewmodel

                viewmodel.siginWithEmail(email,pass)
            }

        }, signinWithFacebook = loginFacebook){
            viewmodel.openDialogLogin(activity,signInLauncher)
        }
    }
}