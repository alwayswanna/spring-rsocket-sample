package a.gleb.producer.service

import a.gleb.common.api.PersonMessageResponse
import a.gleb.producer.logger
import a.gleb.producer.mapper.MessageMapper
import a.gleb.producer.model.MessageRequest
import a.gleb.producer.model.MessageResponse
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.messaging.rsocket.retrieveFlux
import org.springframework.messaging.rsocket.retrieveMono
import org.springframework.stereotype.Service
import java.util.*

const val GET_MESSAGE = "get-message"
const val DELETE_MESSAGE = "delete-message"
const val CREATE_MESSAGE = "save"
const val UPDATE_MESSAGE = "update-message"
const val CREATE_MESSAGES = "save-all"
const val GET_MESSAGES_WITH_LIMIT = "find-last-messages"

@Service
class UserMessageService(
    private val messageMapper: MessageMapper,
    private val rsocketRequester: RSocketRequester
) {

    suspend fun getMessage(messageId: UUID): MessageResponse {
        logger.info { "Find message by with id=$messageId" }

        val response = rsocketRequester.route(GET_MESSAGE)
            .data(messageId)
            .retrieveMono<PersonMessageResponse>()
            .asFlow()
            .map { messageMapper.toMessageResponse(it) }
            .first()

        logger.info { "End find message with id=$messageId" }

        return response
    }

    suspend fun deleteMessage(messageId: UUID) {
        logger.info { "Start delete message by with id=$messageId" }

        rsocketRequester.route(DELETE_MESSAGE).data(messageId)

        logger.info { "End delete message with id=$messageId" }
    }

    suspend fun createMessage(messageRequest: MessageRequest): MessageResponse {
        logger.info { "Start create new message" }

        val response = rsocketRequester.route(CREATE_MESSAGE)
            .data(messageMapper.toPersonMessageRequest(messageRequest))
            .retrieveMono<PersonMessageResponse>()
            .asFlow()
            .map { messageMapper.toMessageResponse(it) }
            .first()

        logger.info { "End create new message, message have id=${response.id}" }

        return response
    }

    suspend fun updateMessage(messageRequest: MessageRequest): MessageResponse {
        logger.info { "Start update message" }

        val response = rsocketRequester.route(UPDATE_MESSAGE)
            .data(messageMapper.toPersonMessageRequest(messageRequest))
            .retrieveMono<PersonMessageResponse>()
            .asFlow()
            .map { messageMapper.toMessageResponse(it) }
            .first()

        logger.info { "End update message, message with id=${response.id}" }

        return response
    }

    suspend fun createMessages(messageRequestList: List<MessageRequest>): List<MessageResponse> {
        logger.info { "Start create new messages" }

        val response = messageRequestList.asFlow()
            .map { messageMapper.toPersonMessageRequest(it) }
            .map { rsocketRequester.route(CREATE_MESSAGES).data(it).retrieveFlux<PersonMessageResponse>().asFlow() }
            .map { messageMapper.toMessageResponse(it.first()) }
            .toList()
        logger.info { "End create new messages" }

        return response
    }

    suspend fun getMessagesWithLimits(limit: Int): List<MessageResponse> {
        logger.info { "Start get messages with limit $limit" }

        val response = rsocketRequester.route(GET_MESSAGES_WITH_LIMIT)
            .data(limit)
            .retrieveFlux<PersonMessageResponse>()
            .asFlow()
            .map { messageMapper.toMessageResponse(it) }
            .toList()

        logger.info { "End get messages with limit ${response.size}" }

        return response
    }
}