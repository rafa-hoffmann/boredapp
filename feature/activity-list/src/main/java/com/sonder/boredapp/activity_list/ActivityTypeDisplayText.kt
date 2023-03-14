package com.sonder.boredapp.activity_list

import com.sonder.boredapp.feature.activity_list.R
import com.sonder.boredapp.model.data.ActivityType

fun ActivityType.getDisplayText() = when (this) {
    ActivityType.EDUCATION -> R.string.activity_type_education
    ActivityType.RECREATIONAL -> R.string.activity_type_recreational
    ActivityType.SOCIAL -> R.string.activity_type_social
    ActivityType.DIY -> R.string.activity_type_diy
    ActivityType.CHARITY -> R.string.activity_type_charity
    ActivityType.COOKING -> R.string.activity_type_cooking
    ActivityType.RELAXATION -> R.string.activity_type_relaxation
    ActivityType.MUSIC -> R.string.activity_type_music
    ActivityType.BUSYWORK -> R.string.activity_type_busywork
}
