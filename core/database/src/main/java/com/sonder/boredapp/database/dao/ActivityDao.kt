package com.sonder.boredapp.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.sonder.boredapp.database.model.ActivityEntity
import com.sonder.boredapp.model.data.ActivityType

@Dao
interface ActivityDao {
    @Query(value = "SELECT * FROM activities")
    fun getAllUserActivityEntities(): List<ActivityEntity>
    @Query(value = "SELECT * FROM activities WHERE type LIKE :type")
    fun getUserActivityEntitiesByType(type: ActivityType): List<ActivityEntity>

    @Update
    suspend fun updateActivity(activity: ActivityEntity)

    @Upsert
    suspend fun upsertActivity(activity: ActivityEntity)
}
