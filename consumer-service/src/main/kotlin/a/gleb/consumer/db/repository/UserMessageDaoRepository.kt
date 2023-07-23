package a.gleb.consumer.db.repository

import a.gleb.consumer.db.entity.UserMessageDao
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.util.*

interface UserMessageDaoRepository: CoroutineCrudRepository<UserMessageDao, UUID> {

    @Query("select * from user_message um order by um.last_update limit :limiting")
    suspend fun findLastUserMessageByLastUpdateAndLimit(limiting: Int): Flow<UserMessageDao?>
}