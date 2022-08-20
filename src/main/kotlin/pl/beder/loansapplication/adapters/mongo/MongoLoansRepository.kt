package pl.beder.loansapplication.adapters.mongo

import org.springframework.data.mongodb.core.MongoOperations
import pl.beder.loansapplication.domain.model.Loan
import pl.beder.loansapplication.domain.ports.outgoing.LoansRepository
import java.util.UUID

class MongoLoansRepository(private val mongoOperations: MongoOperations): LoansRepository {
    override fun findByUUID(id: UUID): Loan? {
        TODO("Not yet implemented")
    }

    override fun save(loan: Loan) {
        TODO("Not yet implemented")
    }

}
