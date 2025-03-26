package com.jriveiro.randomuser.data

data class User(
    val id: Int,
    val title: String,
    val profileImage: String
)

val users: List<User>
    get() = (1..100).map {
        User(
            id = it,
            title = "User $it",
            profileImage = "https://picsum.photos/400/400"
        )
    }