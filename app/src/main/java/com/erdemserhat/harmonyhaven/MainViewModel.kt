package com.erdemserhat.harmonyhaven

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.data.room.JwtTokenRepository
import com.erdemserhat.harmonyhaven.di.JwtInterceptor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val jwtTokenRepository: JwtTokenRepository,
    private val jwtInterceptor: JwtInterceptor
) : ViewModel() {

    fun prepareJwt() {
    }
}
