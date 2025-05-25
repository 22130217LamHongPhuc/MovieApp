package com.example.movie.ui.chatbot

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.movie.R
import com.example.movie.movie.domain.model.Message
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ChatbotScreen(vm: ChatbotViewmodel = hiltViewModel()) {
    //1.3 khoi tao viewmodel

    val messages by vm.chat.collectAsState()
    val isLoading by vm.isLoading.collectAsState()
    val state = rememberLazyListState()

    var input by remember { mutableStateOf("") }
    val keyBoardController = LocalSoftwareKeyboardController.current
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.chatbot))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )

    //1.4 hien thi giao dien chatbox

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff06202B))
    ) {
        LottieAnimation(
            composition = composition,
            progress = progress,
            modifier = Modifier.size(80.dp)
        )
        LaunchedEffect(messages.size) {
            if (messages.isNotEmpty()) {
                state.animateScrollToItem(messages.size - 1)
            }
        }



        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
            state = state
        ) {
            //1.11 load tin nhan moi nhat
            
            items(messages,key = { it.text}) { message ->
                ChatMessageBubble(message)
            }
            if(isLoading){
                //1.9 chuyen sang trang thai  loading

                item{
                    AnimatedDots()
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color.Transparent, shape = RoundedCornerShape(24.dp))
                .border(
                    border = BorderStroke(
                        1.dp, brush = Brush.linearGradient(
                            listOf(Color(0xff94B4C1), Color(0xff547792))
                        )
                    ), shape = RoundedCornerShape(24.dp)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            //1.4 chon vao o nhap tin nhan
            //1.4 nhap thong tin vao o nhap tin nhan
            // 1.5 nhan nut gui

            TextField(
                value = input,
                onValueChange = { input = it },

                placeholder = { Text("Nhập tin nhắn...") },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    disabledTextColor = Color.White

                ),
                maxLines = 4
            )
            IconButton(onClick = {
                if (input.isNotBlank()) {
                    //1.6 gui yeu cau den viewmodel
                    vm.addMessageOfUser(Message("You",input))
                    input = ""
                    keyBoardController?.hide()
                }


            }) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun ChatMessageBubble(message: Message) {
    val isUser = message.sender == "You"
    val alignment = if (isUser) Alignment.End else Alignment.Start
    val bubbleColor = if (isUser) Color(0xFFDCF8C6) else Color.White

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .background(bubbleColor, shape = RoundedCornerShape(16.dp))
                .padding(12.dp)
        ) {
            Text(text = message.text)
        }
    }
}

@Composable
fun AnimatedDots() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .background(Color.Transparent, shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            val dotCount = 3
            val delay = 200
            val state = remember { mutableStateOf(0) }

            LaunchedEffect(Unit) {
                while (true) {
                    delay(delay.toLong())
                    state.value = (state.value + 1) % (dotCount + 1)
                }
            }

            Row {
                repeat(state.value) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        modifier = Modifier
                            .size(13.dp)
                            .padding(horizontal = 2.dp),
                        tint = Color(0xFFDCF8C6)
                    )                }
            }        }
    }
}