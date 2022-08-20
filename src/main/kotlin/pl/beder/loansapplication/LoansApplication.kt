package pl.beder.loansapplication

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import pl.beder.loansapplication.config.PropertyBasedLoanProperties

@SpringBootApplication
@EnableConfigurationProperties(PropertyBasedLoanProperties::class)
class LoansApplication

fun main(args: Array<String>) {
    runApplication<LoansApplication>(*args)
}
