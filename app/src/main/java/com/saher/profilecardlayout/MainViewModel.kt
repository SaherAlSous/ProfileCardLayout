package com.saher.profilecardlayout

import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    val userProfileList = arrayListOf<UserProfile>(
        UserProfile(name = "Hala Atamleh", status = true, drawableId = R.drawable.hala),
        UserProfile(name = "Sarah Tarifi", status = false, drawableId = R.drawable.sarah),
        UserProfile(name = "Hala Atamleh", status = true, drawableId = R.drawable.hala),
        UserProfile(name = "Sarah Tarifi", status = false, drawableId = R.drawable.sarah),
        UserProfile(name = "Hala Atamleh", status = true, drawableId = R.drawable.hala),
        UserProfile(name = "Sarah Tarifi", status = false, drawableId = R.drawable.sarah),
        UserProfile(name = "Hala Atamleh", status = true, drawableId = R.drawable.hala),
        UserProfile(name = "Sarah Tarifi", status = false, drawableId = R.drawable.sarah)
    )
}