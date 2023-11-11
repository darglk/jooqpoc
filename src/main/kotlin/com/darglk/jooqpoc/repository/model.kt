package com.darglk.jooqpoc.repository

import com.darglk.jooqpoc.NoArg
import java.time.OffsetDateTime

enum class UserStatus {
    ENABLED, BLOCKED
}

enum class TicketStatus {
    CREATED, IN_PROGRESS, COMPLETED
}

data class UserEntity(
    val id: String,
    val email: String,
    val status: String,
    val password: String,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime
)

data class UserAuthorities(
    val id: String,
    val email: String,
    val authorities: List<AuthorityEntity>
)

data class Authority(
    val authorityId: String,
    val name: String
)

data class TicketEntity(
    val id: String,
    val userId: String,
    val title: String,
    val description: String,
    val status: String,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime
)

data class AttachmentEntity(
    val id: String,
    val ticketId: String,
    val fileKey: String,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime
)

data class TicketAttachments(
    val id: String,
    val userId: String,
    val title: String,
    val description: String,
    val status: String,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime,
    val attachments: List<AttachmentEntity>
)

data class AuthorityEntity(
    val id: String,
    val name: String
)

data class UserAuthorityEntity(
    val userId: String,
    val authorityId: String
)

@NoArg
data class Name(
    val firstName: String,
    val lastName: String
)

@NoArg
data class Book(
    val id: String,
    val title: String
)

@NoArg
data class Author(
    val id: String,
    val firstName: String,
    val lastName: String,
    val books: List<Book>
)