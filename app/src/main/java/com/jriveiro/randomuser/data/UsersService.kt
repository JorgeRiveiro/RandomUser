package com.jriveiro.randomuser.data

import retrofit2.http.GET
import retrofit2.http.Query

interface UsersService {
    @GET("api/")
    suspend fun fetchUsers(@Query("results") results: Int): RemoteUserListResponse
}