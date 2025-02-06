package com.erdemserhat.harmonyhaven.di

import javax.inject.Inject

class SessionManager @Inject constructor() {
    private var seed: Int? = 12

    fun getSeed(): Int {
        if (seed == null) {
            seed = generateRandomSeed()
        }
        return seed!!
    }

    fun resetSeed() {
        seed = null
    }

    private fun generateRandomSeed(): Int {
        return (1..Int.MAX_VALUE).random()
    }
}
