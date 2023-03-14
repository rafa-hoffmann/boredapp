package com.sonder.boredapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sonder.boredapp.database.dao.ActivityDao
import com.sonder.boredapp.database.model.ActivityEntity
import com.sonder.boredapp.database.util.InstantConverter
import com.sonder.boredapp.database.util.ActivityTypeConverter

@Database(
    entities = [
        ActivityEntity::class
    ],
    version = 1,
    exportSchema = true,
)
@TypeConverters(
    InstantConverter::class,
    ActivityTypeConverter::class
)
abstract class BoredDatabase : RoomDatabase() {
    abstract fun activityDao(): ActivityDao
}
