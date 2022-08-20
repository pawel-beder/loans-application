package pl.beder.loansapplication.domain.model

import pl.beder.loansapplication.domain.Money
import java.time.Instant
import java.util.UUID

class Loan(val term: Instant, var ammount: Money) {
    val uuid = UUID.randomUUID()
}
