package com.darglk.jooqpoc.service

import com.darglk.jooqpoc.controller.*

interface UserService {
    fun getUser(id: String): UserResponse
    fun getUsers(search: String?, page: Int, pageSize: Int): List<UsersResponse>
    fun createUser(createUserRequest: CreateUserRequest)
    fun doesUserExist(email: String): Boolean
    fun updatePassword(request: UpdatePasswordRequest)
    fun updateEmail(request: UpdateEmailRequest)
}