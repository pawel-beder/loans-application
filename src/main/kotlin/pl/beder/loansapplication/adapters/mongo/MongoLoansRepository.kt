package pl.beder.loansapplication.adapters.mongo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.mapping.Document
import pl.beder.loansapplication.domain.model.Money
import pl.beder.loansapplication.domain.model.Loan
import pl.beder.loansapplication.domain.ports.outgoing.LoansRepository
import java.time.Instant
import java.time.LocalDate
import java.util.UUID

class MongoLoansRepository(private val mongoOperations: MongoOperations) : LoansRepository {
    override fun findByUUID(id: UUID): Loan? {
        return mongoOperations.findById(id, LoanDocument::class.java)?.toDomain()
    }

    override fun save(loan: Loan) {
        mongoOperations.save(loan.toDocument())
    }
}

private fun LoanDocument.toDomain() = Loan(id, term, amount, creationTime, dueDate)

private fun Loan.toDocument() = LoanDocument(id, term, amount, creationTime, dueDate)

@Document(collection = "loans")
data class LoanDocument(
    @field:Id
    val id: UUID,
    val term: Long,
    val amount: Money,
    val creationTime: Instant,
    val dueDate: LocalDate
)
