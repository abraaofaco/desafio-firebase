package com.example.desafiofirebase.provider.database

import android.util.Log
import com.example.desafiofirebase.other.Constants.GAME_COLLECTION
import com.example.desafiofirebase.provider.IDatabaseProvider
import com.example.desafiofirebase.provider.database.entities.Game
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.*

class FirestoreRepository : IDatabaseProvider {

    private val mFirestore = FirebaseFirestore.getInstance()
    private var mGameCollection: CollectionReference = mFirestore.collection(GAME_COLLECTION)

    override suspend fun listAll(): List<Game> {
        return try {
            mGameCollection.get().await().toObjects(Game::class.java)
        } catch (e: Exception) {
            Log.i("FirestoreRepo@list", e.message, e)
            emptyList()
        }
    }

    override suspend fun item(uuid: String): Game? {
        return try {
            mGameCollection.document(uuid).get().await().toObject(Game::class.java)
        } catch (e: Exception) {
            Log.i("FirestoreRepo@list", e.message, e)
            null
        }
    }

    override suspend fun create(
        name: String,
        createdYear: Int,
        description: String,
        imageUrl: String
    ) {
        try {
            val game = Game("", name, createdYear, description, imageUrl)

            mGameCollection.add(game).await()
        } catch (e: Exception) {
            Log.i("FirestoreRepo@create", e.message, e)
        }
    }

    override suspend fun update(game: Game) {
        try {
            mGameCollection.document(game.uuid).set(game).await()
        } catch (e: Exception) {
            Log.i("FirestoreRepo@update", e.message, e)
        }
    }
}
