package com.erdemserhat.harmonyhaven.domain.model

 class RequestResultClient(
     result: Boolean,
     message: String,
     val isLoading:Boolean = false,


 ) :RequestResult(result, message)
