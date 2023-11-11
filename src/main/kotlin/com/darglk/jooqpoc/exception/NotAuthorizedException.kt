package com.darglk.jooqpoc.exception

import com.darglk.jooqpoc.exception.CustomException
import com.darglk.jooqpoc.exception.ErrorResponse
import org.springframework.http.HttpStatus

class NotAuthorizedException : CustomException("Unauthorized", HttpStatus.UNAUTHORIZED.value()) {
    override fun serializeErrors(): List<ErrorResponse> {
        return listOf(ErrorResponse("Not authorized", null))
    }
}