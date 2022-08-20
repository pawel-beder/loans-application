package pl.beder.loansapplication.domain.ports.incoming

import pl.beder.loansapplication.config.LoanProperties
import pl.beder.loansapplication.domain.Money
import pl.beder.loansapplication.domain.model.Loan
import pl.beder.loansapplication.domain.ports.outgoing.LoansRepository
import java.util.UUID

class LoansService(
    private val repo: LoansRepository,
    private val props: LoanProperties
) {

    fun grantLoan(term: Long, amount: Money): Loan {
        val loan = Loan(term, amount)
        repo.save(loan)
        return loan
    }

    fun extendLoan(id: UUID) {
        val loan = repo.findByUUID(id) ?: throw LoanNotFoundException(id)
        loan.extend(props.extensionTerm)
    }

    fun fetchLoan(id: UUID): Loan {
        return repo.findByUUID(id) ?: throw LoanNotFoundException(id)
    }
}

class LoanNotFoundException(id: UUID) : RuntimeException("Loan with id $id was not found")
