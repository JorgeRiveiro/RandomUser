package com.jriveiro.randomuser.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jriveiro.randomuser.data.User
import com.jriveiro.randomuser.data.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: UsersRepository
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state get() = _state.asStateFlow()

    fun onUiReady() {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = UiState(loading = false, users = repository.fetchAllUsers())
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val users: List<User> = emptyList()
    )
}