package com.andalus.lifeachievements.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andalus.lifeachievements.models.Badge
import com.andalus.lifeachievements.models.Response

class BadgesViewModel : ViewModel(){

//    val response: LiveData<Response<Data>>
    val badgesList: LiveData<List<Badge>> = MutableLiveData<List<Badge>>()
}