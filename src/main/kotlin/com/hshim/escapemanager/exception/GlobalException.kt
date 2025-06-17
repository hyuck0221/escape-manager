package com.hshim.escapemanager.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import util.ClassUtil.classToMap

enum class GlobalException(
    val message: String,
    val status: HttpStatus = HttpStatus.BAD_REQUEST,
) {
    NOT_FOUND_CENTER("센터를 찾을 수 없습니다."),
    NOT_FOUND_RESERVATION("예약을 찾을 수 없습니다."),
    NOT_FOUND_THEME("테마를 찾을 수 없습니다."),
    NOT_FOUND_ADMIN("관리자를 찾을 수 없습니다."),
    NOT_FOUND_ACCOUNT("계정을 찾을 수 없습니다."),
    ;

    val exception = exception(null)
    fun exception(responseBody: Any?): ResponseStatusException {
        val message = responseBody?.classToMap()?.toString() ?: this.message
        return ResponseStatusException(status, message)
    }
}