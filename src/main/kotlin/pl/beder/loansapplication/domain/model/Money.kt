package pl.beder.loansapplication.domain.model

import java.math.BigDecimal
import java.util.Currency

data class Money(val amount: BigDecimal, val currency: Currency) {
    constructor(amount: String, currency: String = "PLN") : this(BigDecimal(amount), Currency.getInstance(currency))
    constructor(amount: BigDecimal, currency: String = "PLN") : this(amount, Currency.getInstance(currency))
}
