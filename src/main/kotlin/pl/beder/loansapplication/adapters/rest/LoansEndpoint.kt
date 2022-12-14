package pl.beder.loansapplication.adapters.rest

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pl.beder.loansapplication.domain.model.Money
import pl.beder.loansapplication.domain.model.Loan
import pl.beder.loansapplication.domain.ports.incoming.LoansService
import java.time.LocalDate
import java.util.UUID

@RestController
class LoansEndpoint(private val service: LoansService) {

    @PostMapping("/loans/create")
    fun createLoan(@RequestBody request: LoanRequest): ResponseEntity<LoanResponse> {
        val loan = service.createLoan(request.term, request.amount)
        return ResponseEntity(loan.toResponse(), HttpStatus.CREATED)
    }

    @PostMapping("/loans/{uuid}/extend")
    fun extendLoan(@PathVariable("uuid") uuid: UUID): ResponseEntity<Void> {
        service.extendLoan(uuid)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/loans/{uuid}")
    fun fetchLoan(@PathVariable("uuid") uuid: UUID): ResponseEntity<LoanResponse> {
        val loan = service.fetchLoan(uuid)
        return ResponseEntity(loan.toResponse(), HttpStatus.OK)
    }
}

data class LoanResponse(val uuid: UUID, val amount: Money, val dueDate: LocalDate)

private fun Loan.toResponse(): LoanResponse = LoanResponse(id, amount, dueDate)

data class LoanRequest(val term: Long, val amount: Money)
