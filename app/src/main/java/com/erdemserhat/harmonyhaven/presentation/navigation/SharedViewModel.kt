package com.erdemserhat.harmonyhaven.presentation.navigation

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemserhat.harmonyhaven.data.local.repository.JwtTokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named


@HiltViewModel
class SharedViewModel @Inject constructor(
    private val jwtRepository: JwtTokenRepository,
    @Named("FirstInstallingExperience")
    private val firstInstallingExperiencePreferences: SharedPreferences
): ViewModel() {

     fun logout(){
         viewModelScope.launch {
             jwtRepository.deleteJwtToken()
             firstInstallingExperiencePreferences.edit().putBoolean("isJwtExists", false).apply()


         }

    }
}