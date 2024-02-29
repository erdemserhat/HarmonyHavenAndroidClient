package com.erdemserhat.harmonyhaven.example

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ExampleViewModel @Inject constructor(private val engine: Engine):ViewModel() {
    fun A(){
        engine.A()
    }

}