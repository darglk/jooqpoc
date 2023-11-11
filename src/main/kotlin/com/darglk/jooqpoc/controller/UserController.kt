package com.darglk.jooqpoc.controller

import com.darglk.jooqpoc.exception.ValidationException
import com.darglk.jooqpoc.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.*
import kotlin.math.absoluteValue

@RestController
@RequestMapping("/api/activejdbc/users")
class UserController(private val userService: UserService) {
    
    @GetMapping("/{id}")
    fun getUser(@PathVariable("id") id: String): UserResponse {
        return userService.getUser(id)
    }
    
    @PostMapping("/signup")
    fun signup(
        @RequestBody request: CreateUserRequest,
        errors: Errors
    ): ResponseEntity<*> {
        if (errors.hasErrors()) {
            throw ValidationException(errors)
        }
        val response = userService.createUser(request)
        return ResponseEntity.status(HttpStatus.CREATED).body<Any>(response)
    }
    
    @GetMapping("/users")
    fun getUsers(
        @RequestParam("search", required = false) search: String?,
        @RequestParam("page", defaultValue = "0", required = false) page: Int, @RequestParam("pageSize", defaultValue = "1", required = false) pageSize: Int
    ): List<UsersResponse> {
        return userService.getUsers(search, page.absoluteValue, pageSize.absoluteValue)
    }
    
    @GetMapping("/exist")
    fun doesUserExist(@RequestParam("email") email: String): Boolean {
        return userService.doesUserExist(email)
    }
    
    @PutMapping("/password")
    fun updatePassword(@RequestBody request: UpdatePasswordRequest) {
        userService.updatePassword(request)
    }
    
    @PutMapping("/email")
    fun updateEmail(@RequestBody request: UpdateEmailRequest) {
        userService.updateEmail(request)
    }
}