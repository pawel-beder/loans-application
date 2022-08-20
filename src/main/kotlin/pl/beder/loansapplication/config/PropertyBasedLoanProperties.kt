package pl.beder.loansapplication.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import pl.beder.loansapplication.domain.model.LoanProperties
import java.math.BigDecimal

@ConstructorBinding
@ConfigurationProperties(prefix = "loan")
data class PropertyBasedLoanProperties(
    val minAmount: BigDecimal,
    val maxAmount: BigDecimal,
    val minTerm: Long,
    val maxTerm: Long,
    val extensionTerm: Long
) {
    fun toDomain() = LoanProperties(minAmount, maxAmount, minTerm, maxTerm, extensionTerm)
}
