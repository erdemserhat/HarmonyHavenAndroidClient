package com.erdemserhat.harmonyhaven

import androidx.lifecycle.ViewModel
import com.erdemserhat.harmonyhaven.data.local.repository.JwtTokenRepository
import com.erdemserhat.harmonyhaven.di.network.JwtInterceptor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val jwtTokenRepository: JwtTokenRepository,
    private val jwtInterceptor: JwtInterceptor
) : ViewModel() {

    fun prepareJwt() {
    }
}
