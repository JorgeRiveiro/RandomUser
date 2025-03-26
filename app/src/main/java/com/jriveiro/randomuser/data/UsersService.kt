package com.jriveiro.randomuser.data

import retrofit2.http.GET
import retrofit2.http.Path

interface UsersService {
    @GET("api/users")
    suspend fun fetchUsers(): RemoteUserListResponse

    @GET("api/users/{id}")
    suspend fun fetchUserById(@Path("id") id: Int): RemoteUserResponse
}