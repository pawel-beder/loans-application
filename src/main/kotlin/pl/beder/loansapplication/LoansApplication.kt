package pl.beder.loansapplication

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LoansApplication

fun main(args: Array<String>) {
	runApplication<LoansApplication>(*args)
}
