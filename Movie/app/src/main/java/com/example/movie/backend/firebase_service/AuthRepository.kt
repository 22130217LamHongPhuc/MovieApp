package com.example.movie.backend.firebase_service

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

abstract class AuthRepository(val firebaseAuth: FirebaseAuth) {

    abstract suspend fun signIn( email:String,password:String)
    abstract suspend fun signUp( email:String,password:String):Result<String>
    abstract suspend fun logOut()
    abstract fun updateUI(user: FirebaseUser?)

}