package com.example.desafiofirebase.screen.viewmodel

import android.net.Uri
import androidx.lifecycle.*
import com.example.desafiofirebase.provider.IDatabaseProvider
import com.example.desafiofirebase.provider.IStorageProvider
import com.example.desafiofirebase.provider.database.entities.Game
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val appDatabase: IDatabaseProvider,
    private val appStorage: IStorageProvider
) : ViewModel() {

    private val mListGames = MutableLiveData<List<Game>>()
    val games: LiveData<List<Game>> = mListGames

    private val mSelectedGame = MutableLiveData<Game>()
    val selectedGame: LiveData<Game> = mSelectedGame

    fun loadGames() {
        viewModelScope.launch {
            mListGames.value = appDatabase.listAll()
        }
    }

    fun loadGame(uuid: String) {
        viewModelScope.launch {
            mSelectedGame.value = appDatabase.item(uuid)
        }
    }

    fun createGame(name: String, createdYear: Int, description: String, uri: Uri?) {
        viewModelScope.launch {
            var imageUrl = uri?.toString() ?: ""
            appDatabase.create(name, createdYear, description, imageUrl)
            loadGames()
        }
    }

    fun updateGame(game: Game) {
        viewModelScope.launch {
            appDatabase.update(game)
            loadGames()
        }
    }

    fun clearSelectedGame() {
        viewModelScope.launch {
            mSelectedGame.value = Game()
        }
    }
}