package com.example.koinmvvm.utils.enums

enum class ApiResponseStatusCode(val statusCode: Int) {
    SUCCESS(200),
    BAD_REQUEST(400),
    UNAUTHORIZED_ACCESS(401),
    TOO_MANY_REQUEST(429),
    SERVER_ERROR(500)
}

enum class Status {
    LOADING,
    SUCCESS,
    ERROR,
    BAD_REQUEST,
    UNAUTHORIZED_ACCESS,
    TOO_MANY_REQUEST,
    SERVER_ERROR
}