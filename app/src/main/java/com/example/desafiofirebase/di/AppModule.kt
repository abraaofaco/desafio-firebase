package com.example.desafiofirebase.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.desafiofirebase.R
import com.example.desafiofirebase.provider.IAuthProvider
import com.example.desafiofirebase.provider.IDatabaseProvider
import com.example.desafiofirebase.provider.IStorageProvider
import com.example.desafiofirebase.provider.auth.FirebaseAuth
import com.example.desafiofirebase.provider.database.FirestoreRepository
import com.example.desafiofirebase.provider.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .error(R.drawable.not_found)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
    )

    @Singleton
    @Provides
    fun appAuth() = FirebaseAuth() as IAuthProvider

    @Singleton
    @Provides
    fun appDatabase() = FirestoreRepository() as IDatabaseProvider

    @Singleton
    @Provides
    fun appStorage() = FirebaseStorage() as IStorageProvider
}