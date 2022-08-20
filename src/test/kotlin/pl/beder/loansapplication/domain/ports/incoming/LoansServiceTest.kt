package pl.beder.loansapplication.domain.ports.incoming

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import pl.beder.loansapplication.adapters.InMemoryLoansRepository
import pl.beder.loansapplication.domain.Money
import pl.beder.loansapplication.domain.model.Loan
import pl.beder.loansapplication.domain.model.LoanProperties
import java.math.BigDecimal
import java.time.Clock
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneId

internal class LoansServiceTest {

    companion object {
        private const val DEFAULT_TERM = 90L
        private const val DEFAULT_EXTENSION = 7L
        private val DEFAULT_AMOUNT = Money("10.00")
        private val MAXIMUM_AMOUNT = BigDecimal("100.00")
        private val ZONE = ZoneId.of("Europe/Warsaw")

        private val DEFAULT_PROPS = LoanProperties(
            BigDecimal("5.00"),
            MAXIMUM_AMOUNT,
            30L,
            365L,
            DEFAULT_EXTENSION,
            LocalTime.MIDNIGHT,
            LocalTime.of(6, 0)
        )
    }

    private val clock = Clock.fixed(Instant.parse("2022-08-20T02:03:00Z"), ZONE) //04:03 in Warsaw timezone
    private val repo = InMemoryLoansRepository()
    private val service = LoansService(clock, repo, DEFAULT_PROPS)

    @Test
    internal fun `should create loan`() {
        //when
        val loan = service.createLoan(DEFAULT_TERM, DEFAULT_AMOUNT)

        //then
        val persistedLoan = repo.findByUUID(loan.uuid)
        assertThat(persistedLoan?.term).isEqualTo(DEFAULT_TERM)
        assertThat(persistedLoan?.amount).isEqualTo(DEFAULT_AMOUNT)
    }

    @Test
    internal fun `should extend loan`() {
        //given
        val originalLoan = loanExists()

        //when
        service.extendLoan(originalLoan.uuid)

        //then
        val loan = repo.findByUUID(originalLoan.uuid)
        assertThat(loan?.dueDate).isEqualTo(originalLoan.dueDate.plusDays(DEFAULT_EXTENSION))
    }

    @Test
    internal fun `should fetch loan`() {
        //given
        val originalLoan = loanExists()

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
        assertThatThrownBy { service.createLoan(DEFAULT_TERM, Money(amount)) }
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
        assertThatThrownBy { service.createLoan(term, DEFAULT_AMOUNT) }
            .isExactlyInstanceOf(TermAmountOutOfBoundsException::class.java)
    }

    @Test
    internal fun `should fail when requesting maximum amount at night`() {
        //when
        assertThatThrownBy { service.createLoan(DEFAULT_TERM, Money(MAXIMUM_AMOUNT)) }
            .isExactlyInstanceOf(SuspiciousActivityException::class.java)
    }

    private fun loanExists(): Loan {
        return service.createLoan(DEFAULT_TERM, DEFAULT_AMOUNT)
    }
}
