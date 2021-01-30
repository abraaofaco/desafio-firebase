package com.example.desafiofirebase.provider.auth

import com.example.desafiofirebase.provider.IAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class FirebaseAuth : IAuthProvider {

    private val mAuth = FirebaseAuth.getInstance()

    override suspend fun signIn(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun signUp(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password).await()
    }

    override fun signOut() {
        mAuth.signOut()
    }

    override fun currentUser(): FirebaseUser? {
        return mAuth.currentUser
    }
}