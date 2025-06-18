package com.hshim.escapemanager.model.reservation

import com.hshim.escapemanager.common.sse.model.SSEBaseModel

class ReservationSSEResponse {
    class Success(
        val code: String,
    ): SSEBaseModel("reservation_success")

    class Fail(
        val reason: String,
    ): SSEBaseModel("reservation_fail")
}