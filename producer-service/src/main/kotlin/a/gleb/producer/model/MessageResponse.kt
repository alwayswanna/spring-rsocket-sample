package a.gleb.producer.model

import java.time.LocalDate
import java.util.*

data class MessageResponse(
    val id: UUID?,
    val firstName: String,
    val lastName: String,
    val age: Int,
    val birthDate: LocalDate
)
