package a.gleb.producer

import mu.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

val logger = KotlinLogging.logger {  }

@SpringBootApplication
class ProducerApp

fun main(args: Array<String>) {
    runApplication<ProducerApp>(*args)
}
