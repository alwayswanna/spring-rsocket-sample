package a.gleb.producer.service

import a.gleb.common.api.PersonMessageResponse
import a.gleb.producer.logger
import a.gleb.producer.mapper.MessageMapper
import a.gleb.producer.model.MessageResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactive.asFlow
import org.springframework.http.codec.multipart.FilePart
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.messaging.rsocket.retrieveFlux
import org.springframework.stereotype.Service

const val UPLOAD_FILE = "file-upload"

@Service
class FileUserMessageService(
    private val messageMapper: MessageMapper,
    private val rsocketRequester: RSocketRequester
) {

    suspend fun uploadFile(file: FilePart): Flow<MessageResponse?> {
        logger.info { "Start upload file ${file.filename()}" }

        val response = rsocketRequester.route(UPLOAD_FILE)
            .data(file.content().asFlow())
            .retrieveFlux<PersonMessageResponse>()
            .asFlow()
            .map { messageMapper.toMessageResponse(it) }

        logger.info { "End upload file ${file.filename()}" }

        return response
    }
}