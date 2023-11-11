package com.darglk.jooqpoc

import org.jooq.SQLDialect
import org.jooq.impl.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy
import javax.sql.DataSource

@Configuration
class JooqConfiguration(
    val dataSource: DataSource
) {
    
    @Bean
    fun db() = DefaultDSLContext(configuration())
    
    @Bean
    fun configuration(): org.jooq.Configuration {
        val jooqConfiguration = DefaultConfiguration()
        jooqConfiguration.set(connectionProvider())
        jooqConfiguration.setSQLDialect(SQLDialect.POSTGRES)
//        jooqConfiguration.set(DefaultExecuteListenerProvider(exceptionTransformer()))
        return jooqConfiguration
    }
    
    
    @Bean
    fun connectionProvider(): DataSourceConnectionProvider {
        return DataSourceConnectionProvider(TransactionAwareDataSourceProxy(dataSource))
    }
}