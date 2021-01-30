package com.example.desafiofirebase.provider.storage

import android.net.Uri
import android.util.Log
import com.example.desafiofirebase.provider.IStorageProvider
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.*

class FirebaseStorage : IStorageProvider {

    private val mStorage = FirebaseStorage.getInstance()

    override suspend fun upload(uri: Uri): Uri? {
        val filename = UUID.randomUUID().toString()

        return try {
            mStorage.getReference("/images/${filename}")
                .putFile(uri)
                .await()
                .storage
                .downloadUrl
                .await()
        } catch (e: Exception) {
            Log.i("FirebaseStorage@upload", e.message, e)
            null
        }
    }
}