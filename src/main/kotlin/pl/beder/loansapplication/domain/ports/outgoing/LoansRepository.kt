package pl.beder.loansapplication.domain.ports.outgoing

import pl.beder.loansapplication.domain.model.Loan
import java.util.UUID

interface LoansRepository {
    fun findByUUID(id: UUID): Loan?
    fun save(loan: Loan)
}
