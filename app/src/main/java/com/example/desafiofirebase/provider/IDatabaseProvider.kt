package com.example.desafiofirebase.provider

import android.net.Uri
import com.example.desafiofirebase.provider.database.entities.Game

interface IDatabaseProvider {
    suspend fun listAll(): List<Game>
    suspend fun item(uuid: String): Game?
    suspend fun create(name: String, createdYear: Int, description: String, imageUrl: String)
    suspend fun update(game: Game)
}
