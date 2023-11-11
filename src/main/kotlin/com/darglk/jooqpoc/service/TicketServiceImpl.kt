package com.darglk.jooqpoc.service

import com.darglk.jooqpoc.controller.AttachmentResponse
import com.darglk.jooqpoc.controller.CreateTicketRequest
import com.darglk.jooqpoc.controller.TicketResponse
import com.darglk.jooqpoc.repository.TicketRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TicketServiceImpl(private val ticketRepository: TicketRepository) : TicketService {
    @Transactional
    override fun createTicket(request: CreateTicketRequest) {
        ticketRepository.insert(request)
    }
    @Transactional
    override fun getTickets(): List<TicketResponse> {
        return ticketRepository.selectTicekts().map {
            TicketResponse(
                it.id, it.userId, it.description, it.status, it.createdAt.toInstant(), it.updatedAt.toInstant(),
                it.attachments.map {
                    AttachmentResponse(
                        it.id, it.fileKey
                    )
                }
            )
        }
    }
    @Transactional
    override fun deleteTicket(ticketId: String) {
        ticketRepository.delete(ticketId)
    }
    
}