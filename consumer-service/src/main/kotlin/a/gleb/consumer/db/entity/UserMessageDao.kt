package a.gleb.consumer.db.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Table("user_message")
data class UserMessageDao(
    @field:Id
    var id: UUID?,

    @field:Column(value = "first_name")
    var firstName: String,

    @field:Column(value = "last_name")
    var lastName: String,

    @field:Column(value = "age")
    val age: Int,

    @field:Column(value = "birth_date")
    val birthDate: LocalDate,

    @field:Column(value = "last_update")
    var lastUpdate: LocalDateTime
)