package pl.beder.loansapplication.domain.ports.incoming

import pl.beder.loansapplication.domain.model.Money
import pl.beder.loansapplication.domain.model.Loan
import pl.beder.loansapplication.domain.model.LoanProperties
import pl.beder.loansapplication.domain.ports.outgoing.LoansRepository
import java.math.BigDecimal
import java.time.Clock
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneId
import java.util.UUID

class LoansService(
    private val clock: Clock,
    private val repo: LoansRepository,
    private val props: LoanProperties
) {

    companion object {
        val zone: ZoneId = ZoneId.of("Europe/Warsaw")
    }

    fun createLoan(term: Long, amount: Money): Loan {
        val creationTime = Instant.now(clock)
        validate(term, amount, creationTime)
        val loan = Loan(
            term = term,
            amount = amount,
            creationTime = creationTime
        )
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

    private fun validate(term: Long, amount: Money, creationTime: Instant) {
        val localTime = LocalTime.ofInstant(creationTime, zone)
        if(localTime >= props.suspiciousHourStart && localTime <= (props.suspiciousHourEnd)
            && amount.amount.compareTo(props.maxAmount) == 0){
            throw SuspiciousActivityException("Requesting ${props.maxAmount} at $localTime is considered suspicious, loan withhold")
        }

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

class SuspiciousActivityException(message: String) : RuntimeException(message)
