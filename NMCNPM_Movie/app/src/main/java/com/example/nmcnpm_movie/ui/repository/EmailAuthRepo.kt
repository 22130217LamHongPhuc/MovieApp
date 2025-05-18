package com.example.movie.ui.repository


import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest

import com.example.movie.util.K
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.common.api.ApiException


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


@Suppress("DEPRECATION")
class EmailAuthRepo(val auth: FirebaseAuth, val context: Context){



    private val oneTapClient = Identity.getSignInClient(context)
    private val signInRequest = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(K.WebClientID)
                .setFilterByAuthorizedAccounts(false)
                .build()
        )
        .setAutoSelectEnabled(true)
        .build()

    fun signIn(activity: ComponentActivity, launcher: ActivityResultLauncher<IntentSenderRequest>) {
        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener(activity) { result ->
                val intentSenderRequest = IntentSenderRequest.Builder(result.pendingIntent).build()
                launcher.launch(intentSenderRequest) // Mở UI chọn tài khoản Google
            }
            .addOnFailureListener { e ->
                Log.e("SignIn", "One Tap Sign-In failed: ${e.localizedMessage}")
            }
    }

    suspend fun handleSignInResult(intent: Intent?):Result<String> {
        return try {
            val credential = oneTapClient.getSignInCredentialFromIntent(intent)
            val idToken = credential.googleIdToken
            Log.d("infrrr",credential.displayName+" "+credential.password+" "+credential.profilePictureUri)
            if (idToken != null) {
                val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                return suspendCoroutine {
                        continuation ->
                    auth.signInWithCredential(firebaseCredential)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d("SignIn", "Firebase sign-in successful")
                                continuation.resume(Result.success("Successful"))
                            } else {
                                Log.e("SignIn", "Firebase sign-in failed", task.exception)
                                continuation.resume(Result.failure(task.exception ?: Exception("Unknown error")))
                            }
                        }
                }
            } else {
                Log.e("SignIn", "No ID Token!")
                Result.failure(Exception("No ID Token"))
            }
        } catch (e: ApiException) {
            Result.failure(e)
        }
    }

    suspend fun signInWithEmail(email: String, pass: String) :Result<String> {
        //4.2.10 Hệ thống thực hiện callApi đến firebase để xác thực người dùng qua email
        return suspendCoroutine {
                continuation ->  auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener {
                task ->
            if (task.isSuccessful) {
                Log.d("SignIn", "Firebase sign-in successful")
                continuation.resume(Result.success("Successful"))
            } else {
                Log.e("SignIn", "Firebase sign-in failed", task.exception)
                continuation.resume(Result.failure(task.exception ?: Exception("Unknown error")))
            }
        }
        }
    }


}