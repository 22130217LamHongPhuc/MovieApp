package com.example.movie.util

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


   object Instance{
       val firebaseUser: FirebaseUser? =FirebaseAuth.getInstance().currentUser

        val facebookUid =firebaseUser?.providerData
           ?.firstOrNull { it.providerId == "facebook.com" }
           ?.uid
   }


