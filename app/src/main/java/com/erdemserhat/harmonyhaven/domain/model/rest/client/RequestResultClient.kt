package com.erdemserhat.harmonyhaven.domain.model.rest.client

import com.erdemserhat.harmonyhaven.domain.model.rest.server.RequestResult

class RequestResultClient(
     result: Boolean,
     message: String,
     val isLoading:Boolean = false,


 ) : RequestResult(result, message)
