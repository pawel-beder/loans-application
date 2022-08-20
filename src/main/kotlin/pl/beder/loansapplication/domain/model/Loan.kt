package pl.beder.loansapplication.domain.model

import pl.beder.loansapplication.domain.Money
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.UUID

data class Loan(val term: Long, var amount: Money) {
    val uuid: UUID = UUID.randomUUID()
    private val creationTime: Instant = Instant.now()
    var dueDate: LocalDate = LocalDate.ofInstant(creationTime, ZoneId.systemDefault()).plusDays(term)

    fun extend(extensionTerm: Long) {
        dueDate = dueDate.plusDays(extensionTerm)
    }
}
