package pl.beder.loansapplication

import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    classes = [LoansApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class BaseIntegrationTest {
}
