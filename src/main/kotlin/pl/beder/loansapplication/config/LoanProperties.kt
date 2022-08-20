package pl.beder.loansapplication.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.math.BigDecimal

@ConstructorBinding
@ConfigurationProperties(prefix = "loan")
data class LoanProperties(
    val minAmount: BigDecimal,
    val maxAmount: BigDecimal,
    val minTerm: Long,
    val maxTerm: Long,
    val extensionTerm: Long
)
