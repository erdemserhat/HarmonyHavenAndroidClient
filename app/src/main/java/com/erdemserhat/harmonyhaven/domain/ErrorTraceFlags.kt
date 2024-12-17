package com.erdemserhat.harmonyhaven.domain

enum class ErrorTraceFlags(val flagName:String) {
    GENERAL_TRACE("harmonyHavenGeneralErrorTrace"),
    CLOUD_MESSAGE_TRACE("harmonyHavenCloudMessageErrorTrace"),
    LOGIN_TRACE("harmonyHavenLoginErrorTrace"),
    GOOGLE_SIGN_IN_TRACE("harmonyHavenGoogleSignInErrorTrace"),
    REGISTER_TRACE("harmonyHavenRegisterErrorTrace"),
    PASSWORD_RESET_TRACE("harmonyHavenPasswordResetErrorTrace"),
    POST_FLOW_TRACE("harmonyHavenPostFlowErrorTrace"),
    POST_DETAIL_TRACE("harmonyHavenPostDetailErrorTrace"),

}