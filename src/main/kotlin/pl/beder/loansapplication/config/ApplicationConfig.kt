package pl.beder.loansapplication.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoOperations
import pl.beder.loansapplication.adapters.mongo.MongoLoansRepository
import pl.beder.loansapplication.domain.ports.incoming.LoansService
import pl.beder.loansapplication.domain.ports.outgoing.LoansRepository

@Configuration
class ApplicationConfig {

    @Bean
    fun loansService(
        loansRepository: LoansRepository,
        loansProperties: PropertyBasedLoanProperties
    ) = LoansService(loansRepository, loansProperties.toDomain())

    @Bean
    fun loansRepository(
    mongoOperations: MongoOperations
    ) = MongoLoansRepository(mongoOperations)
}
