package pl.beder.loansapplication.domain.model

import java.math.BigDecimal

data class LoanProperties(
    val minAmount: BigDecimal,
    val maxAmount: BigDecimal,
    val minTerm: Long,
    val maxTerm: Long,
    val extensionTerm: Long
)
