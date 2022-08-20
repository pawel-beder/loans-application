package pl.beder.loansapplication.domain.model

import pl.beder.loansapplication.domain.Money
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.UUID

data class Loan(val term: Long, var amount: Money, val creationTime: Instant) {

    private companion object {
        val zone: ZoneId = ZoneId.of("Europe/Warsaw")
    }

    val uuid: UUID = UUID.randomUUID()
    var dueDate: LocalDate = LocalDate.ofInstant(creationTime,  zone).plusDays(term)

    fun extend(extensionTerm: Long) {
        dueDate = dueDate.plusDays(extensionTerm)
    }
}
