package pl.beder.loansapplication.adapters.rest

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpMethod
import org.springframework.http.RequestEntity
import pl.beder.loansapplication.BaseIntegrationTest
import pl.beder.loansapplication.domain.Money
import java.net.URI

class LoansEndpointTest : BaseIntegrationTest() {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Test
    fun `should create loan`() {
        val requestEntity = RequestEntity<LoanRequest>(
            LoanRequest(12, Money("112.00")),
            HttpMethod.POST,
            URI("/loans/create")
        )
        val exchange = restTemplate.exchange(requestEntity, LoanResponse::class.java)
    }

}
