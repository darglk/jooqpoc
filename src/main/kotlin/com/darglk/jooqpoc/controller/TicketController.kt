package com.darglk.jooqpoc.controller

import com.darglk.jooqpoc.exception.ValidationException
import com.darglk.jooqpoc.service.TicketService
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/activejdbc/tickets")
class TicketController(private val ticketService: TicketService) {
    
    @PostMapping
    fun createTicket(@RequestBody request: CreateTicketRequest, errors: Errors) {
        if (errors.hasErrors()) {
            throw ValidationException(errors)
        }
        ticketService.createTicket(request)
    }
    
    @GetMapping
    fun getTickets() : List<TicketResponse> {
        return ticketService.getTickets()
    }
    
    @DeleteMapping("/{ticketId}")
    fun deleteTicket(@PathVariable("ticketId") ticketId: String) {
        ticketService.deleteTicket(ticketId)
    }
}