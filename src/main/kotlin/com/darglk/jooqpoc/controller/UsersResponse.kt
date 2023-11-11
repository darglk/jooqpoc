package com.darglk.jooqpoc.controller

data class UsersResponse(
    val id: String,
    val email: String,
    val authorities: List<AuthorityResponse>
)
