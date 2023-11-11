package com.darglk.jooqpoc.service

import com.darglk.jooqpoc.controller.CreateTicketRequest
import com.darglk.jooqpoc.controller.TicketResponse

interface TicketService {
    fun createTicket(request: CreateTicketRequest)
    fun getTickets(): List<TicketResponse>
    fun deleteTicket(ticketId: String)
}