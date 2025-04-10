package com.jriveiro.randomuser.data

data class User(
    val id: String,
    val title: String,
    val email: String,
    val profileImage: String,
    val dateOfBirth: String,
    val address: String,
    val numberPhone: String
)

data class UserDetails(
    val id: String,
    val fullName: String,
    val email: String,
    val profileImage: String,
    val dateOfBirth: String,
    val address: String,
    val numberPhone: String
)