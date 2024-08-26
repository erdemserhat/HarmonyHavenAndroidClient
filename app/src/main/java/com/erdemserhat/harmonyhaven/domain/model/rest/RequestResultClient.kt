package com.erdemserhat.harmonyhaven.domain.model.rest

class RequestResultClient(
     result: Boolean,
     message: String,
     val isLoading:Boolean = false,


 ) : RequestResult(result, message)
