package com.jriveiro.randomuser.data

data class User(
    val id: Int,
    val title: String,
    val profileImage: String
)

data class UserDetails(
    val id: Int,
    val fullName: String,
    val email: String,
    val profileImage: String
)