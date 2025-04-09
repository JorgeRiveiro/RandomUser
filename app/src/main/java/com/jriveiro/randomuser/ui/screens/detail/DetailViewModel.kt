package com.jriveiro.randomuser.ui.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jriveiro.randomuser.data.UserDetails
import com.jriveiro.randomuser.data.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: UsersRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val userId: String = savedStateHandle["userId"]
        ?: throw IllegalArgumentException("User ID not found in saved state")

    var state by mutableStateOf(UiState())
        private set

    data class UiState(
        val loading: Boolean = false,
        val userDetails: UserDetails? = null
    )

    init {
        viewModelScope.launch {
            state = UiState(loading = true)
            state = UiState(loading = false, userDetails = repository.findUserById(userId))
        }
    }
}