package com.sonder.boredapp.activity_list.user.adapter

import com.sonder.boredapp.model.data.ActivityResource

interface UserActivityListener {
    fun onPlay(activityResource: ActivityResource)

    fun onStop(activityResource: ActivityResource)

    fun onWithdraw(activityResource: ActivityResource)
}
