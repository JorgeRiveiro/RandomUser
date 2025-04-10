package com.jriveiro.randomuser.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val avatar: String,
    val dateOfBirth: String,
    val address: String,
    val numberPhone: String
)