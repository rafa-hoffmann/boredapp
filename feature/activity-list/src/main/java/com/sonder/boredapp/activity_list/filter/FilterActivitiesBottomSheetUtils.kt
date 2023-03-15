package com.sonder.boredapp.activity_list.filter

import androidx.fragment.app.Fragment

fun Fragment.showFilterList() {
    val modalBottomSheet = FilterActivitiesBottomSheet()
    modalBottomSheet.show(childFragmentManager, FilterActivitiesBottomSheet.TAG)
}
