package com.darglk.jooqpoc.controller

import com.darglk.jooqpoc.controller.AttachmentResponse
import java.time.Instant

data class TicketResponse(
    val id: String,
    val userId: String,
    val description: String,
    val status: String,
    val createdAt: Instant,
    val updatedAt: Instant,
    val attachments: List<AttachmentResponse>
)
