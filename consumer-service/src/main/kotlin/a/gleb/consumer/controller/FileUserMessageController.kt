package a.gleb.consumer.controller

import a.gleb.common.api.PersonMessageResponse
import a.gleb.consumer.service.FileUserMessageService
import kotlinx.coroutines.flow.Flow
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller

@Controller
class FileUserMessageController(
    private val fileUserMessageService: FileUserMessageService
) {

    @MessageMapping("file-upload")
    suspend fun uploadFile(dataBufferFlow: Flow<DataBuffer>): Flow<PersonMessageResponse?>{
        return fileUserMessageService.uploadFile(dataBufferFlow)
    }
}