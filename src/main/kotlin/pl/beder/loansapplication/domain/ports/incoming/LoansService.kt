package pl.beder.loansapplication.domain.ports.incoming

import pl.beder.loansapplication.domain.Money
import pl.beder.loansapplication.domain.model.Loan
import pl.beder.loansapplication.domain.model.LoanProperties
import pl.beder.loansapplication.domain.ports.outgoing.LoansRepository
import java.math.BigDecimal
import java.util.UUID

class LoansService(
    private val repo: LoansRepository,
    private val props: LoanProperties
) {

    fun grantLoan(term: Long, amount: Money): Loan {
        validate(term, amount)
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

    private fun validate(term: Long, amount: Money) {
        val requestedAmount = amount.amount
        when {
            requestedAmount < props.minAmount -> amountBelowThreshold(requestedAmount)
            requestedAmount > props.maxAmount -> amountAboveThreshold(requestedAmount)
        }

        when {
            term < props.minTerm -> termBelowThreshold(term)
            term > props.maxTerm -> termAboveThreshold(term)
        }
    }

    private fun amountAboveThreshold(requestedAmount: BigDecimal) {
        throw LoanAmountOutOfBoundsException(
            "Requested loan amount $requestedAmount is more than maximum required amount ${props.minAmount}"
        )
    }

    private fun amountBelowThreshold(requestedAmount: BigDecimal) {
        throw LoanAmountOutOfBoundsException(
            "Requested loan amount $requestedAmount is less than minimum required amount ${props.minAmount}"
        )
    }

    private fun termBelowThreshold(term: Long) {
        throw TermAmountOutOfBoundsException(
            "Requested term of $term days is below minimum term of ${props.minTerm} days"
        )
    }

    private fun termAboveThreshold(term: Long) {
        throw TermAmountOutOfBoundsException(
            "Requested term of $term days is above maximum term of ${props.maxTerm} days"
        )
    }
}

class LoanNotFoundException(id: UUID) : RuntimeException("Loan with id $id was not found")

class LoanAmountOutOfBoundsException(message: String) : RuntimeException(message)

class TermAmountOutOfBoundsException(message: String) : RuntimeException(message)
