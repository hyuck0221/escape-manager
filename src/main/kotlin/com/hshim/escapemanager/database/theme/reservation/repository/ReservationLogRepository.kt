package com.hshim.escapemanager.database.theme.reservation.repository

import com.hshim.escapemanager.database.theme.reservation.ReservationLog
import org.springframework.data.jpa.repository.JpaRepository

interface ReservationLogRepository : JpaRepository<ReservationLog, String> {

}