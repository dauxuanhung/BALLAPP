package com.example.ballapp.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val mainActivityRepository: MainActivityRepository) :
    ViewModel() {
    val updateUsers = MutableLiveData<UpdateUsers>()
    val updateTeams = MutableLiveData<UpdateTeams>()

    sealed class UpdateUsers {
        object ResultOk : UpdateUsers()
        object ResultError : UpdateUsers()
    }

    sealed class UpdateTeams {
        object ResultOk : UpdateTeams()
        object ResultError : UpdateTeams()
    }

    fun updateUser(
        userUID: String,
        avatarUrl: String,
    ) {
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
        }) {
            mainActivityRepository.updateUser(userUID, avatarUrl, {
                updateUsers.value = UpdateUsers.ResultOk
            }, {
                updateUsers.value = UpdateUsers.ResultError
            })
        }
    }

    fun updateTeams(
        userUID: String,
        teamImageUrl: String,
    ) {
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
        }) {
            mainActivityRepository.updateTeam(userUID, teamImageUrl, {
                updateTeams.value = UpdateTeams.ResultOk
            }, {
                updateTeams.value = UpdateTeams.ResultError
            })

        }

    }
}