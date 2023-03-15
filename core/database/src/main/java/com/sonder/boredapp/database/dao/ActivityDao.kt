package com.sonder.boredapp.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.sonder.boredapp.database.model.ActivityEntity

@Dao
interface ActivityDao {
    @Query(value = "SELECT * FROM activities")
    fun getUserActivityEntities(): List<ActivityEntity>

    @Update
    suspend fun updateActivity(activity: ActivityEntity)

    @Upsert
    suspend fun upsertActivity(activity: ActivityEntity)
}
