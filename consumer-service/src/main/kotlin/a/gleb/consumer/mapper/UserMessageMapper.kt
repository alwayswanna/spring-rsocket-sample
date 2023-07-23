package a.gleb.consumer.mapper

import a.gleb.common.api.PersonMessageRequest
import a.gleb.common.api.PersonMessageResponse
import a.gleb.consumer.db.entity.UserMessageDao
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*

@Component
class UserMessageMapper {

    suspend fun toPersonMessageResponse(userMessageDao: UserMessageDao?): PersonMessageResponse? {
        if (userMessageDao == null) {
            return null
        }

        return PersonMessageResponse(
            id = userMessageDao.id!!,
            firstName = userMessageDao.firstName,
            lastName = userMessageDao.lastName,
            age = userMessageDao.age,
            birthDate = userMessageDao.birthDate
        )
    }

    suspend fun toUserMessageDao(personMessageRequest: PersonMessageRequest): UserMessageDao {
        return UserMessageDao(
            id = null,
            firstName = personMessageRequest.firstName,
            lastName = personMessageRequest.lastName,
            age = personMessageRequest.age,
            birthDate = personMessageRequest.birthDate,
            lastUpdate = LocalDateTime.now()
        )
    }
}