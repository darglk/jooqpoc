package com.darglk.jooqpoc.repository

import com.darglk.jooqpoc.controller.CreateTicketRequest

interface TicketRepository {
    fun insert(ticket: CreateTicketRequest)
    fun selectTicekts(): List<TicketAttachments>
    fun delete(ticketId: String)
}