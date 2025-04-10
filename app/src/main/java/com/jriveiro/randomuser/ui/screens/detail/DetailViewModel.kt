package com.jriveiro.randomuser.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jriveiro.randomuser.data.UserDetails
import com.jriveiro.randomuser.data.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: UsersRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val userId: String = savedStateHandle["userId"]
        ?: throw IllegalArgumentException("User ID not found in saved state")

    private val _state = MutableStateFlow(UiState())
    val state get() = _state.asStateFlow()

    data class UiState(
        val loading: Boolean = false,
        val userDetails: UserDetails? = null,
        val message: String? = null
    )

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = UiState(loading = false, userDetails = repository.findUserById(userId))
        }
    }

    fun onFavoriteClick() {
        _state.update { it.copy(message = "Favorite Clicked") }
    }

    fun onMessageShown() {
        _state.update { it.copy(message = null) }
    }
}