package a.gleb.producer.mapper

import a.gleb.common.api.PersonMessageRequest
import a.gleb.common.api.PersonMessageResponse
import a.gleb.producer.model.MessageRequest
import a.gleb.producer.model.MessageResponse
import org.springframework.stereotype.Component

@Component
class MessageMapper {

    suspend fun toMessageResponse(personMessageResponse: PersonMessageResponse): MessageResponse {
        return MessageResponse(
            id = personMessageResponse.id,
            firstName = personMessageResponse.firstName,
            lastName = personMessageResponse.lastName,
            age = personMessageResponse.age,
            birthDate = personMessageResponse.birthDate
        )
    }

    suspend fun toPersonMessageRequest(messageRequest: MessageRequest): PersonMessageRequest {
        return PersonMessageRequest(
            id = messageRequest.id,
            firstName = messageRequest.firstName,
            lastName = messageRequest.lastName,
            age = messageRequest.age,
            birthDate = messageRequest.birthDate
        )
    }
}