package com.jriveiro.randomuser.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteUserListResponse(
    val page: Int,
    @SerialName("per_page") val perPage: Int,
    val total: Int,
    @SerialName("total_pages") val totalPages: Int,
    val data: List<RemoteUser>,
    val support: Support
)

@Serializable
data class RemoteUser(
    val id: Int,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
    val avatar: String,
    val email: String
)

@Serializable
data class Support(
    val url: String,
    val text: String
)

@Serializable
data class RemoteUserResponse(
    val data: RemoteUser
)

