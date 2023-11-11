package com.darglk.jooqpoc.controller

import com.darglk.jooqpoc.controller.AttachmentRequest

data class CreateTicketRequest(
    val title: String,
    val description: String,
    val userId: String,
    val attachments: List<AttachmentRequest>
)
