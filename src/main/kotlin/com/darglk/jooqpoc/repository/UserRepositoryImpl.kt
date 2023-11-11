package com.darglk.jooqpoc.repository

import com.darglk.jooqpoc.controller.CreateUserRequest
import nu.studer.sample.jooqpoc.Tables.*
import org.jooq.DSLContext
import org.jooq.impl.DSL.*
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class UserRepositoryImpl(
    val db: DSLContext
) : UserRepository {
    override fun findUser(id: String): UserEntity? {
        return db.select(*USERS.fields())
            .from(USERS)
            .where(USERS.ID.eq(id))
            .fetchOneInto(UserEntity::class.java)
    }
    
    override fun findUserByEmail(email: String): UserEntity? {
        return db.select(asterisk())
            .from(USERS)
            .where(USERS.EMAIL.eq(email))
            .fetchOneInto(UserEntity::class.java)
    }
    
    override fun insertUser(user: CreateUserRequest) {
        val userId = UUID.randomUUID()
        db.insertInto(
            USERS, USERS.ID, USERS.EMAIL, USERS.PASSWORD, USERS.STATUS
        ).values(userId.toString(), user.email, user.password, "CREATED")
            .execute()
        
        db.select(asterisk()).from(AUTHORITIES).fetchInto(AuthorityEntity::class.java)
            .forEach {
                db.insertInto(
                    USERS_AUTHORITIES, USERS_AUTHORITIES.USER_ID, USERS_AUTHORITIES.AUTHORITY_ID
                ).values(userId.toString(), it.id).execute()
            }
    }
    
    override fun doesEmailExist(email: String): Boolean {
        return findUserByEmail(email) != null
    }
    
    override fun updatePassword(userId: String, password: String) {
        db.update(USERS)
            .set(USERS.PASSWORD, password)
            .where(USERS.ID.eq(userId))
            .execute()
    }
    
    override fun updateEmail(userId: String, email: String) {
        db.update(USERS)
            .set(USERS.EMAIL, email)
            .where(USERS.ID.eq(userId))
            .execute()
    }
    
    override fun getUsers(search: String?, page: Int, pageSize: Int): List<UserAuthorities> {
        val query = db.select(
            USERS.ID, USERS.EMAIL, array(
                select(
                    row(AUTHORITIES.ID, AUTHORITIES.NAME).mapping(AuthorityEntity::class.java, ::AuthorityEntity)
                ).distinctOn(AUTHORITIES.ID)
                    .from(AUTHORITIES)
                    .leftOuterJoin(USERS_AUTHORITIES).on(AUTHORITIES.ID.eq(USERS_AUTHORITIES.AUTHORITY_ID))
                    .leftOuterJoin(USERS).on(USERS_AUTHORITIES.USER_ID.eq(USERS.ID))
            ).`as`("authorities").convertFrom(Arrays::asList)
        )
            .from(USERS)
        if (search != null) {
            query.where(USERS.EMAIL.likeIgnoreCase(search))
        }
        query.limit(pageSize * page, pageSize)
        return query.fetchInto(UserAuthorities::class.java)
    }
}