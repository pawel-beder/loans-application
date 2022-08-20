package pl.beder.loansapplication.adapters

import pl.beder.loansapplication.domain.model.Loan
import pl.beder.loansapplication.domain.ports.outgoing.LoansRepository
import java.util.UUID

class InMemoryLoansRepository : LoansRepository {
    private val map = HashMap<UUID, Loan>()

    override fun findByUUID(id: UUID): Loan? {
        return map[id]
    }

    override fun save(loan: Loan) {
        map[loan.id] = loan.copy()
    }
}
