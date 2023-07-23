package a.gleb.common.api

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate
import java.util.*

data class PersonMessageRequest(
    @JsonProperty("id")
    val id: UUID?,
    @JsonProperty("firstName")
    val firstName: String,
    @JsonProperty("lastName")
    val lastName: String,
    @JsonProperty("age")
    val age: Int,
    @JsonProperty("birthDate")
    val birthDate: LocalDate
)
