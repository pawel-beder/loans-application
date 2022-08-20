package pl.beder.loansapplication.adapters.rest

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import pl.beder.loansapplication.BaseIntegrationTest
import pl.beder.loansapplication.domain.Money
import java.net.URI
import java.time.LocalDate

class LoansEndpointTest : BaseIntegrationTest() {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Test
    fun `should create loan`() {
        val amount = Money("112.00")
        val exchange = restTemplate.exchange(requestEntity(amount), LoanResponse::class.java)

        assertThat(exchange.body?.amount).isEqualTo(amount)
        assertThat(exchange.body?.dueDate).isEqualTo(LocalDate.now().plusDays(12))
        assertThat(exchange.statusCode).isEqualTo(HttpStatus.CREATED)
    }

    private fun requestEntity(amount: Money): RequestEntity<LoanRequest> {
        return RequestEntity(
            LoanRequest(12, amount),
            HttpMethod.POST,
            URI("/loans/create")
        )
    }
}
