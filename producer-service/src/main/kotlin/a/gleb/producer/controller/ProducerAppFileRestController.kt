package a.gleb.producer.controller

import a.gleb.producer.model.MessageResponse
import a.gleb.producer.service.FileUserMessageService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.flow.Flow
import org.springframework.http.MediaType.APPLICATION_NDJSON_VALUE
import org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController

const val PRODUCER_FILE_CONTROLLER_NAME = "producer-file-controller"

@RestController
@RequestMapping("/api/v1")
@Tag(name = PRODUCER_FILE_CONTROLLER_NAME)
class ProducerAppFileRestController(
    private val fileUserMessageService: FileUserMessageService
) {

    @Operation(
        summary = "Upload message from file .xlsx"
    )
    @PostMapping("/file", consumes = [MULTIPART_FORM_DATA_VALUE], produces = [APPLICATION_NDJSON_VALUE])
    fun uploadFile(@RequestPart file: FilePart): Flow<MessageResponse?> {
        return fileUserMessageService.uploadFile(file)
    }
}