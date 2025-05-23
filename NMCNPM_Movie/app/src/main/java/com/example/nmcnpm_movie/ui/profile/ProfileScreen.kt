package com.example.movie.ui.profile

import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.OfflinePin
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.SignalCellularOff
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.movie.R
import com.example.movie.util.Instance
import com.example.movie.util.K
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text


@Composable
fun ProfileScreen(navController:NavController){
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally) {
        if(K.userCurrent==null){
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center){
                Button(onClick = {
                    navController.navigate("sign_in")
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                ),
                    modifier =  Modifier.fillMaxWidth(0.7f)
                ) {
                    Text(text = "Đăng nhập",style = MaterialTheme.typography.bodyLarge.copy(Color.White))
                }
            }
        }else{

            itemProfile(icon = Icons.Default.PersonOutline, title = K.userCurrent?.displayName ?: ""){

            }
            Text(text = "", modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(1f)
                .size(1.dp)
                .background(Color.Red))
            Spacer(modifier = Modifier.height(10.dp))

            itemProfile(icon = Icons.Default.History, title = "Lịch sử xem", onclick = {
                navController.navigate("movie_history")
            })
            Text(text = "", modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(1f)
                .size(1.dp)
                .background(Color.Red))
            Spacer(modifier = Modifier.height(10.dp))


            itemProfile(icon = Icons.Default.Password, title = "Đổi mật khẩu"){

            }
            Text(text = "", modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(1f)
                .size(1.dp)
                .background(Color.Red))
            Spacer(modifier = Modifier.height(10.dp))
            itemProfile(icon = Icons.Default.Close, title = "Đăng xuất"){
                FirebaseAuth.getInstance().signOut()
                navController.navigate("sign_in")
            }

            val avatarUrl = "https://graph.facebook.com/${Instance.facebookUid}/picture?height=100"


            Log.d("image",K.userCurrent?.photoUrl.toString() ?: "")

        }
    }
}

@Composable
fun itemProfile(icon: ImageVector,title: String,onclick:()->Unit){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(24.dp)
        .clickable {
            onclick()
        }) {
        Icon(imageVector =icon , contentDescription =null,tint= Color.White )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = title,style = MaterialTheme.typography.bodyLarge.copy(color = Color.White))
        Spacer(modifier = Modifier.height(10.dp))


    }
}