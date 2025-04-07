package com.jriveiro.randomuser.data

import javax.inject.Inject

class UsersRepository @Inject constructor(){
    suspend fun fetchAllUsers(): List<User> = UsersClient.instance.fetchUsers()
        .data
        .map { it.toDomainModel() }

    suspend fun findUserById(id: Int): UserDetails {
        return UsersClient.instance.fetchUserById(id)
            .data
            .toUserDetails()
    }

}

private fun RemoteUser.toDomainModel() = User(
    id = id,
    title = "$firstName $lastName",
    profileImage = avatar
)

private fun RemoteUser.toUserDetails(): UserDetails = UserDetails(
    id = id,
    fullName = "$firstName $lastName",
    email = email,
    profileImage = avatar
)