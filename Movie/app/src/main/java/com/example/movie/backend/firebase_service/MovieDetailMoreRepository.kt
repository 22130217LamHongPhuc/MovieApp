package com.example.movie.backend.firebase_service

import android.util.Log
import com.example.movie.movie.domain.model.Comment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class MovieDetailMoreRepository {

    private val db = FirebaseDatabase.getInstance().reference


    fun addComment(cmt:Comment,onResult : (Result<Unit>) -> Unit)
    {
        //     4.1.26 Hệ thống thực hiện callApi của Firebase để thực hiện lưu bình luận lên firebase

        val key = db.child("Comment").child(cmt.idMovie).push().key

        if(key == null){
            onResult(Result.failure(Exception("Không tạo được key")))
            return
        }

        //     4.1.27 MovieDetailMoreRepository nhận kết quả trả về lưu trữ bình luận từ Firebase

        db.child("Comment").child(cmt.idMovie).child(key).setValue(cmt).
        addOnSuccessListener {
            onResult(Result.success(Unit))
        }

    }

     fun getCommentsRealtime(movieId: String, onUpdate: (MutableList<Comment>) -> Unit) {
         //     4.1.15 Hệ thống thực hiện callApi đến firebase để lấy thông tin danh sách bình luận của bộ phim

         db.child("Comment").child(movieId)
             .addListenerForSingleValueEvent(object : ValueEventListener {
                 override fun onDataChange(snapshot: DataSnapshot) {
                     val result = mutableListOf<Comment>()
                     for (child in snapshot.children) {
                         val comment = child.getValue(Comment::class.java)
                         if (comment != null) {
                             result.add(comment)
                         }
                     }
                     //     4.1.16 MovieDetailMoreRepository nhận dữ liệu bình luận về từ firebase

                     onUpdate(result)
                 }

                 override fun onCancelled(error: DatabaseError) {
                     onUpdate(mutableListOf())
                 }
             })
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun isFavoriteMovie(idUser:String, idMovie:String):Boolean{

        return suspendCancellableCoroutine { cont ->
            val ref = FirebaseDatabase.getInstance().getReference("Favorite")
                .child(idUser)
                .child(idMovie)

            ref.get()
                .addOnSuccessListener { snapshot ->
                    Log.d("detail", "Firebase success")
                    cont.resume(snapshot.exists())
                }
                .addOnFailureListener { e ->
                    Log.d("detail", "Firebase failed: ${e.message}")
                    cont.resume(false)
                }
        }

    }

    fun setFavoriteMovie(uid: String?, idMovie: String,result:(Boolean) -> Unit) {
        FirebaseDatabase.getInstance().getReference("Favorite")
            .child(uid ?: "")
            .child(idMovie)
            .setValue(true)
            .addOnSuccessListener {
                result(true)
            }.addOnFailureListener {
                result(false)
            }
    }

}