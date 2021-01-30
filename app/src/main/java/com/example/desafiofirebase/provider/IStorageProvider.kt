package com.example.desafiofirebase.provider

import android.net.Uri

interface IStorageProvider {
    suspend fun upload(uri: Uri): Uri?
}