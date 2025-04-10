package com.jriveiro.randomuser.data

import com.jriveiro.randomuser.data.local.UserDao
import com.jriveiro.randomuser.data.local.UserEntity
import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val userDao: UserDao
) {

    private var cachedUsers: List<User>? = null

    suspend fun fetchAllUsers(results: Int = 50): List<User> {
        if (cachedUsers != null) {
            return cachedUsers!!
        }
        val users = UsersClient.instance.fetchUsers(results)
            .results
            .map { it.toDomainModel() }
        userDao.clearTable()
        userDao.insertAll(users.map { it.toEntity() })
        cachedUsers = users
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
    profileImage = picture.large,
    dateOfBirth = dob.date,
    address = "${location.street.name}, ${location.city}, ${location.country}",
    numberPhone = phone
)

private fun User.toEntity() = UserEntity(
    id = id,
    firstName = title.split(" ")[0],
    lastName = title.split(" ")[1],
    email = email,
    avatar = profileImage,
    dateOfBirth = dateOfBirth,
    address = address,
    numberPhone = numberPhone
)

private fun UserEntity.toUserDetails() = UserDetails(
    id = id,
    fullName = "$firstName $lastName",
    email = email,
    profileImage = avatar,
    dateOfBirth = dateOfBirth,
    address =address,
    numberPhone = numberPhone
)