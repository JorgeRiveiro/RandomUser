package com.jriveiro.randomuser.data

import com.jriveiro.randomuser.data.local.UserDao
import com.jriveiro.randomuser.data.local.UserEntity
import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val userDao: UserDao
) {

    suspend fun fetchAllUsers(results: Int = 50): List<User> {
        val users = UsersClient.instance.fetchUsers(results)
            .results
            .map { it.toDomainModel() }
        userDao.clearTable()
        userDao.insertAll(users.map { it.toEntity() })
        return users
    }

    suspend fun findUserById(id: String): UserDetails? {
        return userDao.getUserById(id)?.toUserDetails()
    }
}

private fun RemoteUser.toDomainModel() = User(
    id = login.uuid,
    title = "${name.first} ${name.last}",
    email = email,
    profileImage = picture.medium
)

private fun User.toEntity() = UserEntity(
    id = id,
    firstName = title.split(" ")[0],
    lastName = title.split(" ")[1],
    email = email,
    avatar = profileImage
)

private fun UserEntity.toUserDetails() = UserDetails(
    id = id,
    fullName = "$firstName $lastName",
    email = email,
    profileImage = avatar
)