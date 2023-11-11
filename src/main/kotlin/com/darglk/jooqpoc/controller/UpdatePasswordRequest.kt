package com.darglk.jooqpoc.controller

data class UpdatePasswordRequest(
    val userId: String,
    val password: String
)
