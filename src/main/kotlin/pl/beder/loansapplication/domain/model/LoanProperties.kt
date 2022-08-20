package pl.beder.loansapplication.domain.model

import java.math.BigDecimal
import java.time.LocalTime

data class LoanProperties(
    val minAmount: BigDecimal,
    val maxAmount: BigDecimal,
    val minTerm: Long,
    val maxTerm: Long,
    val extensionTerm: Long,
    val suspiciousHourStart: LocalTime,
    val suspiciousHourEnd: LocalTime
)
