package com.darglk.jooqpoc.repository

import com.darglk.jooqpoc.controller.CreateTicketRequest
import nu.studer.sample.jooqpoc.Tables.*
import org.jooq.impl.DSL.*
import org.jooq.*

import org.springframework.stereotype.Repository
import java.util.*

@Repository
class TicketRepositoryImpl(
    private val db: DSLContext
) : TicketRepository {
    
    override fun insert(request: CreateTicketRequest) {
        val ticketId = UUID.randomUUID().toString()
        db.insertInto(
            TICKETS, TICKETS.ID, TICKETS.TITLE, TICKETS.DESCRIPTION, TICKETS.USER_ID, TICKETS.STATUS
        ).values(ticketId, request.title, request.description, request.userId, "CREATED").execute()
        
        request.attachments.forEach {
            val q = db.insertInto(
                ATTACHMENTS, ATTACHMENTS.ID, ATTACHMENTS.TICKET_ID, ATTACHMENTS.FILE_KEY
            ).values(UUID.randomUUID().toString(), ticketId, it.fileKey).execute()
        }
    }
    
    override fun selectTicekts(): List<TicketAttachments> {
        val cte = name("ticket_attachments_cte").fields(
            TICKETS.ID.name, TICKETS.TITLE.name
        ).`as`(
            select(TICKETS.ID, TICKETS.TITLE)
                .from(TICKETS)
        )
        val result = db.with(cte)
            .select(
                cte.field(TICKETS.ID.name), cte.field(TICKETS.TITLE.name)
            ).from(cte)
            .fetch()
        
        return db.select(
            TICKETS.ID, TICKETS.TITLE, TICKETS.STATUS, TICKETS.DESCRIPTION, TICKETS.CREATED_AT, TICKETS.UPDATED_AT,
            TICKETS.USER_ID, array(
                select(
                    row(
                        ATTACHMENTS.ID,
                        ATTACHMENTS.TICKET_ID,
                        ATTACHMENTS.FILE_KEY,
                        ATTACHMENTS.CREATED_AT,
                        ATTACHMENTS.UPDATED_AT
                    ).mapping(
                        AttachmentEntity::class.java, ::AttachmentEntity
                    )
                ).from(ATTACHMENTS).where(TICKETS.ID.eq(ATTACHMENTS.TICKET_ID))
            ).`as`("attachments").convertFrom(Arrays::asList)
        ).from(TICKETS).fetch().into(TicketAttachments::class.java)
    }
    
    override fun delete(ticketId: String) {
        db.deleteFrom(TICKETS).where(TICKETS.ID.eq(ticketId)).execute()
    }
}