package com.hshim.escapemanager.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import util.ClassUtil.classToMap

enum class GlobalException(
    val message: String,
    val status: HttpStatus = HttpStatus.BAD_REQUEST,
) {
    NOT_FOUND_CENTER("센터를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_RESERVATION("예약을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_THEME("테마를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_ADMIN("관리자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_ACCOUNT("계정을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    ACCOUNT_FORBIDDEN("접근 권한이 없습니다.", HttpStatus.FORBIDDEN),

    EXISTS_ACCOUNT_LOGIN_ID("사용중인 로그인 아이디 입니다.", HttpStatus.CONFLICT),

    NOT_FOUND_CENTER_ID("센터 고유값을 찾을 수 없습니다."),
    NOT_FOUND_ACCOUNT_ID("계정 고유값을 찾을 수 없습니다."),
    NOT_FOUND_ROLE("권한을 찾을 수 없습니다."),
    IS_NOT_RESERVE_TIME("시간표에 없는 예약 시간입니다."),

    IS_NOT_OPEN_RESERVE_TIME("아직 열리지 않은 예약입니다."),
    IS_ALREADY_RESERVE_TIME("이미 예약된 시간입니다."),

    CREATED("성공적으로 생성되었습니다.", HttpStatus.CREATED),

    ;

    val exception = exception(null)
    fun exception(responseBody: Any?): ResponseStatusException {
        val message = responseBody?.classToMap()?.toString() ?: this.message
        return ResponseStatusException(status, message)
    }
}