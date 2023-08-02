package a.gleb.consumer.service

import a.gleb.common.api.PersonMessageRequest
import a.gleb.common.api.PersonMessageResponse
import a.gleb.consumer.db.repository.UserMessageDaoRepository
import a.gleb.consumer.exception.BadRequestException
import a.gleb.consumer.logger
import a.gleb.consumer.mapper.UserMessageMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.*

@Service
class UserMessageService(
    private val userMessageDaoRepository: UserMessageDaoRepository,
    private val messageMapper: UserMessageMapper
) {

    suspend fun save(personMessageRequest: PersonMessageRequest): PersonMessageResponse? {
        logger.info { "Start save user message" }

        val userMessageDao = messageMapper.toUserMessageDao(personMessageRequest)
        val savedMessage = userMessageDaoRepository.save(userMessageDao)
        val personMessageResponse = messageMapper.toPersonMessageResponse(savedMessage)

        logger.info { "Successfully save user message" }
        return personMessageResponse
    }

    fun saveAll(personMessageRequestList: Flow<PersonMessageRequest>): Flow<PersonMessageResponse?> {
        logger.info { "Start save user messages" }

        val userMessageDaoList = personMessageRequestList.map { messageMapper.toUserMessageDao(it) }
        val savedUserMessagesDaoList = userMessageDaoRepository.saveAll(userMessageDaoList)
        return savedUserMessagesDaoList
            .map { messageMapper.toPersonMessageResponse(it) }
            .onCompletion { logger.info { "Successfully save user messages" } }
    }

    suspend fun getMessage(id: UUID): PersonMessageResponse? {
        logger.info { "Find message by id: $id" }

        val savedMessage = userMessageDaoRepository.findById(id)
        val userMessageResponse = messageMapper.toPersonMessageResponse(savedMessage)

        logger.info { "Successfully find message by id: $id" }
        return userMessageResponse
    }

    suspend fun deleteMessage(id: UUID): Mono<Unit> {
        logger.info { "Delete message with id: $id" }
        return Mono.just(userMessageDaoRepository.deleteById(id))
            .doOnSuccess { logger.info { "Successfully delete message by id: $id" } }
    }

    suspend fun updateUserMessage(personMessageRequest: PersonMessageRequest): PersonMessageResponse? {
        logger.info { "Start update user message, id=${personMessageRequest.id}" }

        if (personMessageRequest.id == null) throw BadRequestException("Request does not contain id")

        val messageToSave = messageMapper.toUserMessageDao(personMessageRequest)
        messageToSave.id = personMessageRequest.id
        val updateUserMessage = userMessageDaoRepository.save(messageToSave)
        val userMessageResponse = messageMapper.toPersonMessageResponse(updateUserMessage)

        logger.info { "Successfully update user message, id=${userMessageResponse?.id}" }
        return userMessageResponse
    }

    fun findLastMessageByLastUpdateAndLimit(limiting: Int): Flow<PersonMessageResponse?> {
        logger.info { "Start find last $limiting messages by last_update" }

        return userMessageDaoRepository.findLastUserMessageByLastUpdateAndLimit(limiting)
            .map { messageMapper.toPersonMessageResponse(it) }
            .onCompletion { logger.info { "Successfully find messages" } }
    }
}