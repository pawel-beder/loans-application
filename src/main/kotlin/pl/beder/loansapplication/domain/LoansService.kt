package pl.beder.loansapplication.domain

import pl.beder.loansapplication.domain.ports.outgoing.LoansRepository

class LoansService(
    private val repo: LoansRepository
) {

    fun grantLoan(term: Int, amount: Money) {

    }

    fun extendLoan() {

    }

    fun fetchLoan() {

    }
}
