package com.sonder.boredapp.activity_list.user.adapter

import android.os.SystemClock
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Chronometer
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.sonder.boredapp.activity_list.setup
import com.sonder.boredapp.feature.activity_list.R
import com.sonder.boredapp.feature.activity_list.databinding.ItemActivityUserBinding
import com.sonder.boredapp.model.data.ActivityResource
import com.sonder.boredapp.model.data.ActivityStatus
import kotlinx.datetime.Clock

class UserActivityAdapter(private val listener: UserActivityListener) :
    ListAdapter<ActivityResource, UserActivityAdapter.UserActivityViewHolder>(UserActivityAdapter) {

    inner class UserActivityViewHolder(private val binding: ItemActivityUserBinding) :
        ViewHolder(binding.root) {

        fun bind(activity: ActivityResource) {
            with(binding) {
                itemActivity.setup(activity)

                setupButtonsVisibility(activity.status)
                setupButtonsListener(activity)
                activityStatus.setupActivityStatus(activity.status)
                activityInProgressTimeSpent.setupActivityStatus(activity.status)
            }
        }

        private fun Chronometer.setupActivityStatus(status: ActivityStatus?) {
            when (status) {
                is ActivityStatus.InProgress -> {
                    val timeElapsed = Clock.System.now().toEpochMilliseconds() -
                        status.startTime.toEpochMilliseconds()

                    base = SystemClock.elapsedRealtime() - timeElapsed
                    start()
                }
                else -> {
                    isVisible = false
                    stop()
                }
            }
        }

        private fun TextView.setupActivityStatus(status: ActivityStatus?) {
            when (status) {
                is ActivityStatus.Finished -> {
                    isVisible = true
                    text = context.getString(
                        R.string.activity_status_completed,
                        status.durationTimeString()
                    )
                }
                is ActivityStatus.InProgress -> {
                    isVisible = false
                }
                ActivityStatus.Withdrawal -> {
                    isVisible = true
                    text = context.getString(R.string.activity_status_withdrawed)
                }
                null -> {
                    isVisible = false
                }
            }
        }

        private fun ActivityStatus.Finished.durationTimeString() =
            (finishTime - startTime).toString()

        private fun ItemActivityUserBinding.setupButtonsListener(activity: ActivityResource) {
            activityPlay.setOnClickListener { listener.onPlay(activity) }
            activityStop.setOnClickListener { listener.onStop(activity) }
            activityWithdraw.setOnClickListener { listener.onWithdraw(activity) }
        }

        private fun ItemActivityUserBinding.setupButtonsVisibility(status: ActivityStatus?) {
            when (status) {
                is ActivityStatus.Finished -> {
                    activityPlay.isVisible = false
                    activityStop.isVisible = false
                    activityWithdraw.isVisible = false
                }
                is ActivityStatus.InProgress -> {
                    activityPlay.isVisible = false
                    activityStop.isVisible = true
                    activityWithdraw.isVisible = true
                }
                ActivityStatus.Withdrawal -> {
                    activityPlay.isVisible = false
                    activityStop.isVisible = false
                    activityWithdraw.isVisible = false
                }
                null -> {
                    activityPlay.isVisible = true
                    activityStop.isVisible = false
                    activityWithdraw.isVisible = true
                }
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): UserActivityViewHolder {
        val itemBinding =
            ItemActivityUserBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        return UserActivityViewHolder(itemBinding)
    }

    override fun onBindViewHolder(viewHolder: UserActivityViewHolder, position: Int) {
        viewHolder.bind(getItem(position))
    }

    private companion object : DiffUtil.ItemCallback<ActivityResource>() {
        override fun areItemsTheSame(oldItem: ActivityResource, newItem: ActivityResource) =
            oldItem.key == newItem.key

        override fun areContentsTheSame(
            oldItem: ActivityResource,
            newItem: ActivityResource
        ) = oldItem == newItem
    }
}
