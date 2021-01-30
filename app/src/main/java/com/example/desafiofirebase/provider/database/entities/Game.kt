package com.example.desafiofirebase.provider.database.entities

import com.google.firebase.firestore.DocumentId

data class Game(
    @DocumentId
    val uuid: String = "",
    val name: String = "",
    val createdYear: Int = 0,
    val description: String = "",
    var imageUrl: String = ""
)