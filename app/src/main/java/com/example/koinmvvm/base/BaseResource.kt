package com.example.koinmvvm.base

import com.example.koinmvvm.utils.enums.Status

class BaseResource<T>(
    var status: Status,
    var totalResult: Int = 0,
    var data: T? = null,
    var message: String? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other == null || javaClass != other.javaClass)
            return false

        val resource = other as BaseResource<*>

        if (status !== resource.status)
            return false

        if (if (message != null) message != resource.message else resource.message != null)
            return false

        return if (data != null) data == resource.data else resource.data == null
    }

    override fun hashCode(): Int {
        var result = status.hashCode()
        result = 31 * result + (message?.hashCode() ?: 0)
        result = 31 * result + (data?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "BaseResource(" +
                "status = $status" +
                "message = $message" +
                "data = $data" +
                ")"
    }

    companion object {
        fun <T> loading(): BaseResource<T> {
            return BaseResource(
                Status.LOADING, 0, null, null
            )
        }

        fun <T> success(data: T?, totalResult: Int, message: String?): BaseResource<T> {
            return BaseResource(
                Status.SUCCESS, totalResult, data, message
            )
        }

        fun <T> error(message: String?): BaseResource<T> {
            return BaseResource(
                Status.ERROR, 0, null, message
            )
        }

        fun <T> badRequest(message: String?): BaseResource<T> {
            return BaseResource(
                Status.BAD_REQUEST, 0, null, message
            )
        }

        fun <T> unauthorizedAccess(message: String?): BaseResource<T> {
            return BaseResource(
                Status.UNAUTHORIZED_ACCESS, 0, null, message
            )
        }

        fun <T> tooManyRequest(message: String?): BaseResource<T> {
            return BaseResource(
                Status.TOO_MANY_REQUEST, 0, null, message
            )
        }

        fun <T> serverError(message: String?): BaseResource<T> {
            return BaseResource(
                Status.SERVER_ERROR, 0, null, message
            )
        }
    }
}