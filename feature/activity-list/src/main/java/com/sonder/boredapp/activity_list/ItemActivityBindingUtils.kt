package com.sonder.boredapp.activity_list

import com.sonder.boredapp.feature.activity_list.R
import com.sonder.boredapp.feature.activity_list.databinding.ItemActivityBinding
import com.sonder.boredapp.model.data.ActivityResource

fun ItemActivityBinding.setup(activity: ActivityResource) {
    activityName.text = activity.activity
    activityType.text =
        root.context.getString(activity.type.getDisplayText())

    activityPrice.text =
        root.context.getString(R.string.activity_price, activity.price)
    activityAccessibility.text =
        root.context.getString(
            R.string.activity_accessibility,
            activity.accessibility
        )
    activityParticipants.text =
        root.context.getString(
            R.string.activity_participants,
            activity.participants
        )
}
