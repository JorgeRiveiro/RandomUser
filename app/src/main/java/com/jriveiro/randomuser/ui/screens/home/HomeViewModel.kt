package com.jriveiro.randomuser.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jriveiro.randomuser.data.User
import com.jriveiro.randomuser.data.UsersRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repository : UsersRepository = UsersRepository()

    var state by mutableStateOf(UiState())
        private set

    fun onUiReady() {
        viewModelScope.launch {
            state = UiState(loading = true)
            state = UiState(loading = false, users = repository.fetchAllUsers())
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val users: List<User> = emptyList()
    )
}