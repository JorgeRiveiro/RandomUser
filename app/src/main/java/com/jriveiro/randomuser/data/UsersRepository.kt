package com.jriveiro.randomuser.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class UsersRepository{
    suspend fun fetchAllUsers(): List<User> = withContext(Dispatchers.IO) {
        delay(1000)
        users
    }
}