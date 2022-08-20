package pl.beder.loansapplication

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container

@SpringBootTest(
    classes = [LoansApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class BaseIntegrationTest {
    companion object {
        @Container
        val mongoDbContainer = MongoDBContainer()

        init {
            mongoDbContainer.start()
        }

        @JvmStatic
        @DynamicPropertySource
        fun setProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.data.mongodb.uri", mongoDbContainer::getReplicaSetUrl)
        }
    }
}
