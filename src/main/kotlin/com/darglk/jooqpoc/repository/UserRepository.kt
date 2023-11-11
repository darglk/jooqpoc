package com.darglk.jooqpoc.repository

import com.darglk.jooqpoc.controller.CreateUserRequest

interface UserRepository {
    fun findUser(id: String): UserEntity?
    fun findUserByEmail(email: String): UserEntity?
    fun insertUser(user: CreateUserRequest)
    fun doesEmailExist(email: String): Boolean
    fun updatePassword(userId: String, password: String)
    fun updateEmail(userId: String, email: String)
    fun getUsers(search: String?, page: Int, pageSize: Int): List<UserAuthorities>
}