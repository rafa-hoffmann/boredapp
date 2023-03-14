package com.sonder.boredapp.database.util

import androidx.room.TypeConverter
import com.sonder.boredapp.model.data.ActivityType
import com.sonder.boredapp.model.data.asActivityResourceType
import kotlinx.datetime.Instant

class InstantConverter {
    @TypeConverter
    fun longToInstant(value: Long?): Instant? =
        value?.let(Instant::fromEpochMilliseconds)

    @TypeConverter
    fun instantToLong(instant: Instant?): Long? =
        instant?.toEpochMilliseconds()
}

class ActivityTypeConverter {
    @TypeConverter
    fun activityTypeToString(value: ActivityType): String =
        value.let(ActivityType::apiName)

    @TypeConverter
    fun stringToActivityType(serializedName: String): ActivityType =
        serializedName.asActivityResourceType()
}
