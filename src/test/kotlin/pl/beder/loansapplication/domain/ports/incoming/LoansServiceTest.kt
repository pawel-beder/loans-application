package pl.beder.loansapplication.domain.ports.incoming

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.beder.loansapplication.adapters.InMemoryLoansRepository
import pl.beder.loansapplication.config.LoanProperties
import pl.beder.loansapplication.domain.Money
import pl.beder.loansapplication.domain.model.Loan
import java.math.BigDecimal

internal class LoansServiceTest {

    companion object {
        private const val DEFAULT_TERM = 10L
        private const val DEFAULT_EXTENSION = 7L
        private val DEFAULT_AMOUNT = Money("10.00")
        private val DEFAULT_PROPS = LoanProperties(
            BigDecimal.ONE,
            BigDecimal.TEN,
            1L,
            10L,
            DEFAULT_EXTENSION
        )
    }

    private val repo = InMemoryLoansRepository()
    private val service = LoansService(repo, DEFAULT_PROPS)

    @Test
    fun `should create loan`() {
        //when
        val loan = service.grantLoan(DEFAULT_TERM, DEFAULT_AMOUNT)

        //then
        val persistedLoan = repo.findByUUID(loan.uuid)
        assertThat(persistedLoan?.term).isEqualTo(DEFAULT_TERM)
        assertThat(persistedLoan?.amount).isEqualTo(DEFAULT_AMOUNT)
    }

    @Test
    fun extendLoan() {
        //given
        val originalLoan =  loanExists()

        //when
        service.extendLoan(originalLoan.uuid)

        //then
        val loan = repo.findByUUID(originalLoan.uuid)
        assertThat(loan?.dueDate).isEqualTo(originalLoan.dueDate.plusDays(DEFAULT_EXTENSION))
    }

    @Test
    fun fetchLoan() {
        //given
        val originalLoan =  loanExists()

        //when
        val fetchedLoan = service.fetchLoan(originalLoan.uuid)

        //then
        assertThat(fetchedLoan).isEqualTo(originalLoan)
    }

    private fun loanExists(): Loan {
        return service.grantLoan(DEFAULT_TERM, DEFAULT_AMOUNT)
    }
}
