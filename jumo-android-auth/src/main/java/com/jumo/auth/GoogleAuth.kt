@file:OptIn(ExperimentalCoroutinesApi::class)

package com.jumo.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

object GoogleAuth {
  private const val provider = "google"

  private val gso
    get() = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
      .requestIdToken(BuildConfig.GOOGLE_SERVER_CLIENT_ID)
      .requestEmail()
      .build()

  fun init(context: Context) {
    GoogleSignIn.getClient(context, gso)
  }

  fun login(fragment: Fragment, launcher: ActivityResultLauncher<Intent>): Flow<AuthState> =
    callbackFlow {
      channel.send(AuthState.Loading)
//      val account = null
      val account = GoogleSignIn.getLastSignedInAccount(fragment.requireContext())
      when {
        false && account != null -> {
          channel.send(
            AuthState.Success(account.id.toString(), account.idToken.toString(), provider)
          )
          channel.close()
        }
        else -> launchGoogle(fragment, launcher)
      }
      awaitClose()
    }

  private fun ProducerScope<AuthState>.launchGoogle(
    fragment: Fragment,
    launcher: ActivityResultLauncher<Intent>,
  ) {
    val client = GoogleSignIn.getClient(fragment.requireContext(), gso)
    client.silentSignIn().addOnSuccessListener { account ->
      trySendBlocking(
        AuthState.Success(account.id.toString(), account.idToken.toString(), provider)
      )
      channel.close()
    }.addOnFailureListener {
//      trySendBlocking(AuthState.Error(it))
      launcher.launch(client.signInIntent)
      close()
    }.addOnCanceledListener {
      trySendBlocking(AuthState.Error(Exception("user canceled")))
      channel.close()
    }
  }

  fun createActivityLauncher(fragment: Fragment, onResult: (AuthState) -> Unit) =
    fragment.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
      when (result.resultCode) {
        Activity.RESULT_OK -> runCatching {
          val account = GoogleSignIn.getSignedInAccountFromIntent(result.data).result
          onResult(
            AuthState.Success(account.id.toString(), account.idToken.toString(), provider)
          )
        }.onFailure {
          onResult(AuthState.Error(it))
        }
        else -> onResult(AuthState.Error(Exception("user canceled")))
      }
    }

}