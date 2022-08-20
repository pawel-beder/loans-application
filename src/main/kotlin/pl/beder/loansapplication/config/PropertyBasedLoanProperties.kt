package pl.beder.loansapplication.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import pl.beder.loansapplication.domain.model.LoanProperties
import java.math.BigDecimal
import java.time.LocalTime
import kotlin.coroutines.suspendCoroutine

@ConstructorBinding
@ConfigurationProperties(prefix = "loan")
data class PropertyBasedLoanProperties(
    val minAmount: BigDecimal,
    val maxAmount: BigDecimal,
    val minTerm: Long,
    val maxTerm: Long,
    val extensionTerm: Long,
    val suspiciousHourStart: String,
    val suspiciousHourEnd: String
) {
    fun toDomain() = LoanProperties(
        minAmount,
        maxAmount,
        minTerm,
        maxTerm,
        extensionTerm,
        LocalTime.parse(suspiciousHourStart),
        LocalTime.parse(suspiciousHourEnd)
    )


}
