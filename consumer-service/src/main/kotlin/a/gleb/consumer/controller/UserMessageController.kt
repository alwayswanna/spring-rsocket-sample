package a.gleb.consumer.controller

import a.gleb.common.api.PersonMessageRequest
import a.gleb.common.api.PersonMessageResponse
import a.gleb.consumer.service.UserMessageService
import kotlinx.coroutines.flow.Flow
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Mono
import java.util.*

@Controller
class UserMessageController(
    private val userMessageService: UserMessageService
) {

    @MessageMapping("save")
    suspend fun save(personMessageRequest: PersonMessageRequest): PersonMessageResponse? {
        return userMessageService.save(personMessageRequest)
    }

    @MessageMapping("save-all")
    fun saveAll(personMessageRequestList: Flow<PersonMessageRequest>): Flow<PersonMessageResponse?> {
        return userMessageService.saveAll(personMessageRequestList)
    }

    @MessageMapping("get-message")
    suspend fun getMessage(messageId: UUID): PersonMessageResponse? {
        return userMessageService.getMessage(messageId)
    }

    @MessageMapping("update-message")
    suspend fun updateUserMessage(personMessageRequest: PersonMessageRequest): PersonMessageResponse? {
        return userMessageService.updateUserMessage(personMessageRequest)
    }

    @MessageMapping("delete-message")
    suspend fun deleteMessage(messageId: UUID): Mono<Unit> {
        return userMessageService.deleteMessage(messageId)
    }

    @MessageMapping("find-last-messages")
    fun findLastMessages(limiting: Int): Flow<PersonMessageResponse?> {
        return userMessageService.findLastMessageByLastUpdateAndLimit(limiting)
    }

}