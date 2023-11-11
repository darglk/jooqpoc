package com.darglk.jooqpoc.service
import com.darglk.jooqpoc.controller.*
import com.darglk.jooqpoc.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
) : UserService {
    @Transactional
    override fun getUser(id: String): UserResponse {
        return userRepository.findUser(id)?.let {
            UserResponse(it.id, it.email)
        } ?: throw RuntimeException()
    }
    
    @Transactional
    override fun getUsers(search: String?, page: Int, pageSize: Int): List<UsersResponse> {
        return userRepository.getUsers(search, page, pageSize).map {
            UsersResponse(
                it.id, it.email,
                it.authorities.map {
                    AuthorityResponse(it.id, it.name)
                }
            )
        }
    }
    
    @Transactional
    override fun createUser(createUserRequest: CreateUserRequest) {
        val email = createUserRequest.email
        
        if (userRepository.doesEmailExist(email)) {
            throw java.lang.RuntimeException("Email in use")
        }
        userRepository.insertUser(createUserRequest)
    }
    
    @Transactional
    override fun doesUserExist(email: String): Boolean {
        return userRepository.findUserByEmail(email) != null
    }
    
    @Transactional
    override fun updatePassword(request: UpdatePasswordRequest) {
        userRepository.updatePassword(request.userId, request.password)
    }
    
    @Transactional
    override fun updateEmail(request: UpdateEmailRequest) {
        userRepository.updateEmail(request.userId, request.email)
    }
}