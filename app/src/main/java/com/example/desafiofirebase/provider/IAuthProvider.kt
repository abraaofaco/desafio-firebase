package com.example.desafiofirebase.provider

import com.google.firebase.auth.FirebaseUser

interface IAuthProvider {

    suspend fun signIn(email: String, password: String)

    suspend fun signUp(email: String, password: String)

    fun signOut()

    fun currentUser(): FirebaseUser?
}