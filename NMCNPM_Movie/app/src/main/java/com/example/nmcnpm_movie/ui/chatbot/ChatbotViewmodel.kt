package com.example.movie.ui.chatbot
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie.movie.domain.model.Message
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

    private var _chat = MutableStateFlow<List<Message>>(emptyList())
    val chat: StateFlow<List<Message>> get() = _chat
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val systemPrompt = """
    |Bạn là MovieBot - trợ lý điện ảnh. Chỉ trả lời về:
    |- Thông tin phim, diễn viên, đạo diễn
    |- Đề xuất phim theo thể loại
    |- Trailer và nơi xem hợp pháp
    |
    |Câu trả lời mẫu khi hỏi ngoài chủ đề:
    |"Tôi chỉ hỗ trợ câu hỏi về phim ảnh. Bạn quan tâm phim nào gần đây?"
    """.trimMargin()

    private var currentChatContext: String? = systemPrompt

    fun chatBox(userPrompt: String) {
        _isLoading.value = true

        viewModelScope.launch {
            val apiKey = "AIzaSyAysjC2YrWywFVuvczvQFyYT4ISpaxtvNA"

            val generativeModel = GenerativeModel(
                modelName = "gemini-1.5-flash",
                apiKey = apiKey,
            )

            val chatHistory = _chat.value.joinToString("\n") {
                "${it.sender}: ${it.text}"
            }

            val fullPrompt = """
                $systemPrompt
                
                Lịch sử chat hiện tại:
                $chatHistory
                
                Câu hỏi mới của người dùng: $userPrompt
            """.trimIndent()

            try {
                val response = generativeModel.generateContent(fullPrompt)

                _chat.update { list ->
                    list + Message("Chatbot", response.text ?: "Không thể tạo phản hồi")
                }

                Log.d("chatbot", response.text ?: "Không thể tạo phản hồi")
            } catch (e: Exception) {
                _chat.update { list ->
                    list + Message("User", userPrompt) +
                            Message("Chatbot", "Đã xảy ra lỗi: ${e.localizedMessage}")
                }
                Log.e("chatbot", "Error: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addMessageOfUser(message: Message) {
        _chat.update { list ->
            list + message
        }
        Log.d("chatbot", _chat.value.toString())
        chatBox(message.text)
    }

    fun clearCurrentChat() {
        _chat.value = emptyList()
    }
}