package com.sonder.boredapp.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.sonder.boredapp.database.model.ActivityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityDao {
    @Query(value = "SELECT * FROM activities")
    fun getUserActivityEntities(): Flow<List<ActivityEntity>>

    @Update
    suspend fun updateActivity(activity: ActivityEntity)

    @Upsert
    suspend fun upsertActivity(activity: ActivityEntity)
}
