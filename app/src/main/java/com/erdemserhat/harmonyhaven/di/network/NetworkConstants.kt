package com.erdemserhat.harmonyhaven.di.network

/**
 * A singleton object containing network-related constants.
 *
 * This object defines constants used for network configurations, such as the base URL for the API.
 * The constants are used throughout the application to ensure consistency and to avoid hardcoding
 * URLs in multiple places.
 */
object NetworkConstants {

    /**
     * The base URL for the API.
     *
     * This constant represents the base URL used for making network requests to the API. It includes
     * the protocol (http), the domain, and the port number, but does not include the endpoint paths.
     * This URL is used as a starting point for constructing full API endpoint URLs.
     */
    const val BASE_URL = "http://51.20.136.184:5000/api/v1/"
}