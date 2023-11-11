package com.darglk.jooqpoc.exception

import com.darglk.jooqpoc.exception.CustomException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(value = [CustomException::class])
    fun handleCustomException(ex: CustomException): ResponseEntity<*> {
        return CustomException.handleCustomException(ex)
    }
}