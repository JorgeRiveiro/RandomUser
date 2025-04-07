package com.jriveiro.randomuser.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val avatar: String
)