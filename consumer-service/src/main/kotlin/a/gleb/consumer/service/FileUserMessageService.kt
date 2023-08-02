package a.gleb.consumer.service

import a.gleb.common.api.PersonMessageResponse
import a.gleb.consumer.db.entity.UserMessageDao
import a.gleb.consumer.db.repository.UserMessageDaoRepository
import a.gleb.consumer.logger
import a.gleb.consumer.mapper.UserMessageMapper
import kotlinx.coroutines.flow.*
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.stereotype.Service
import java.io.SequenceInputStream
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

const val SHEET_INDEX = 0

const val FIRST_NAME_CELL = 0
const val LAST_NAME_CELL = 1
const val AGE_CELL = 2
const val BIRTH_DATE_CELL = 3

@Service
class FileUserMessageService(
    private val messageMapper: UserMessageMapper,
    private val userMessageDaoRepository: UserMessageDaoRepository
) {

    suspend fun uploadFile(dataBufferFlow: Flow<DataBuffer>): Flow<PersonMessageResponse?> {
        logger.info { "Start upload file" }

        val inputStream = dataBufferFlow.map { it.asInputStream(true) }
            .reduce{acc, i -> SequenceInputStream(acc, i)}

        return XSSFWorkbook(inputStream).getSheetAt(SHEET_INDEX)
            .asFlow()
            .filter { it.rowNum > 0 }
            .map { mapToUserMessageDao(it) }
            .map { userMessageDaoRepository.save(it) }
            .map { messageMapper.toPersonMessageResponse(it) }
            .onCompletion { logger.info { "Successfully upload file" } }
    }

    private suspend fun mapToUserMessageDao(row: Row): UserMessageDao {
        return UserMessageDao(
            id = null,
            firstName = row.getCell(FIRST_NAME_CELL).stringCellValue,
            lastName = row.getCell(LAST_NAME_CELL).stringCellValue,
            age = row.getCell(AGE_CELL).numericCellValue.toInt(),
            birthDate = LocalDate.ofInstant(
                row.getCell(BIRTH_DATE_CELL).dateCellValue.toInstant(),
                ZoneId.systemDefault()
            ),
            lastUpdate = LocalDateTime.now()
        )
    }
}