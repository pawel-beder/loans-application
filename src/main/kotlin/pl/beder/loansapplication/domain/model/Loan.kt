package pl.beder.loansapplication.domain.model

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.UUID

data class Loan(
    val id: UUID = UUID.randomUUID(),
    val term: Long,
    val amount: Money,
    val creationTime: Instant,
    var dueDate: LocalDate = LocalDate.ofInstant(creationTime, zone).plusDays(term)
) {

    private companion object {
        val zone: ZoneId = ZoneId.of("Europe/Warsaw")
    }

    fun extend(extensionTerm: Long) {
        dueDate = dueDate.plusDays(extensionTerm)
    }
}
