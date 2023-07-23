package a.gleb.consumer

import mu.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

val logger = KotlinLogging.logger {  }

@SpringBootApplication
class ConsumerApp

fun main(args: Array<String>) {
    runApplication<ConsumerApp>(*args)
}
