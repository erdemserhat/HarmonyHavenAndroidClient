package com.erdemserhat.harmonyhaven.presentation.post_authentication.quote_main.dynamic_card

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class VolumeControlViewModel @Inject constructor():ViewModel() {
    private var _isMuted = MutableStateFlow(false)
    val isMuted:StateFlow<Boolean> = _isMuted
    private var lastCondition = false

    fun setLastCondition(){
        _isMuted.value = lastCondition

    }

    fun saveLastCondition(){
        lastCondition =_isMuted.value
    }


    fun mute(){
        _isMuted.value=true
    }

    fun unMute(){
        _isMuted.value=false
    }

    fun toggleMute(){
        _isMuted.value=!_isMuted.value
    }
}