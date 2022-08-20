package pl.beder.loansapplication

import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class LoansApplication

fun main(args: Array<String>) {
	runApplication<LoansApplication>(*args)
}
