package pl.beder.loansapplication.domain.ports.incoming

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource
import pl.beder.loansapplication.adapters.InMemoryLoansRepository
import pl.beder.loansapplication.domain.Money
import pl.beder.loansapplication.domain.model.Loan
import pl.beder.loansapplication.domain.model.LoanProperties
import java.math.BigDecimal

internal class LoansServiceTest {

    companion object {
        private const val DEFAULT_TERM = 90L
        private const val BELOW_THRESHOLD_TERM = 29L
        private const val ABOVE_THRESHOLD_TERM = 402L

        private const val DEFAULT_EXTENSION = 7L

        private val DEFAULT_AMOUNT = Money("10.00")
        private val BELOW_THRESHOLD_AMOUNT = Money("2.37")
        private val ABOVE_THRESHOLD_AMOUNT = Money("100.1")

        private val DEFAULT_PROPS = LoanProperties(
            BigDecimal("5.00"),
            BigDecimal("100.00"),
            30L,
            365L,
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
    fun `should extend loan`() {
        //given
        val originalLoan =  loanExists()

        //when
        service.extendLoan(originalLoan.uuid)

        //then
        val loan = repo.findByUUID(originalLoan.uuid)
        assertThat(loan?.dueDate).isEqualTo(originalLoan.dueDate.plusDays(DEFAULT_EXTENSION))
    }

    @Test
    fun `should fetch loan`() {
        //given
        val originalLoan =  loanExists()

        //when
        val fetchedLoan = service.fetchLoan(originalLoan.uuid)

        //then
        assertThat(fetchedLoan).isEqualTo(originalLoan)
    }

    @ParameterizedTest(name = "{index} => amount= {0}")
    @CsvSource(
        "2.37",
        "4.99",
        "100.1",
        "354.45"
    )
    internal fun `should fail when loan amount is not within boundaries`(amount: String) {
        //when
        assertThatThrownBy { service.grantLoan(DEFAULT_TERM, Money(amount)) }
            .isExactlyInstanceOf(LoanAmountOutOfBoundsException::class.java)
    }

    @ParameterizedTest(name = "{index} => term= {0} days")
    @CsvSource(
        "2",
        "29",
        "366",
        "563"
    )
    internal fun `should fail when loan term is not within boundaries`(term: Long) {
        //when
        assertThatThrownBy { service.grantLoan(term, DEFAULT_AMOUNT) }
            .isExactlyInstanceOf(TermAmountOutOfBoundsException::class.java)
    }

    private fun loanExists(): Loan {
        return service.grantLoan(DEFAULT_TERM, DEFAULT_AMOUNT)
    }
}
