package com.erdemserhat.harmonyhaven.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface VersionCheckingApiService {
    @GET("/check-android-version/{version}")
    suspend fun checkVersionStability(
        @Path("version") versionCode: Int

    ): Response<VersionCheckResult>
}

data class VersionCheckResult(
    val result:Int
)