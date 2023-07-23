package a.gleb.producer.controller

import a.gleb.producer.model.MessageRequest
import a.gleb.producer.model.MessageResponse
import a.gleb.producer.service.UserMessageService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.util.*

const val PRODUCER_CONTROLLER_NAME = "producer-message-controller"

@RestController
@RequestMapping("/api/v1/message")
@Tag(name = PRODUCER_CONTROLLER_NAME)
class ProducerAppRestController(
    private val userMessageService: UserMessageService
) {

    @Operation(
        summary = "Get user message by ID"
    )
    @GetMapping("/get")
    suspend fun getMessage(@RequestParam messageId: UUID): MessageResponse {
        return userMessageService.getMessage(messageId)
    }

    @Operation(
        summary = "Delete message by ID"
    )
    @DeleteMapping("/delete")
    suspend fun deleteMessage(@RequestParam messageId: UUID) {
        return userMessageService.deleteMessage(messageId)
    }

    @Operation(
        summary = "Create new message"
    )
    @PostMapping("/create")
    suspend fun createMessage(@RequestBody messageRequest: MessageRequest): MessageResponse {
        return userMessageService.createMessage(messageRequest)
    }

    @Operation(
        summary = "Create new messages"
    )
    @PostMapping("/create-messages")
    suspend fun createMessages(@RequestBody messageRequestList: List<MessageRequest>): List<MessageResponse> {
        return userMessageService.createMessages(messageRequestList)
    }

    @Operation(
        summary = "Update existing message"
    )
    @PutMapping("/update")
    suspend fun updateMessage(@RequestBody messageRequest: MessageRequest): MessageResponse {
        return userMessageService.updateMessage(messageRequest)
    }

    @Operation(
        summary = "Find last messages with limits"
    )
    @GetMapping("/get-messages")
    suspend fun getMessages(@RequestParam limit: Int): List<MessageResponse> {
        return userMessageService.getMessagesWithLimits(limit)
    }
}