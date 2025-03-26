package com.jriveiro.randomuser.ui.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jriveiro.randomuser.data.UserDetails
import com.jriveiro.randomuser.data.UsersRepository
import kotlinx.coroutines.launch

class DetailViewModel (private val id : Int) : ViewModel(){
    private val repository: UsersRepository = UsersRepository()

    var state by mutableStateOf(UiState())
        private set

    data class UiState(
        val loading: Boolean = false,
        val userDetails: UserDetails? = null
    )

    init {
        viewModelScope.launch {
            state = UiState(loading = true)
            state = UiState(loading = false, userDetails = repository.findUserById(id))
        }
    }
}