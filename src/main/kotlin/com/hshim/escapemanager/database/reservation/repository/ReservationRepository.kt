package com.hshim.escapemanager.database.reservation.repository

import com.hshim.escapemanager.database.reservation.Reservation
import org.springframework.data.jpa.repository.JpaRepository

interface ReservationRepository : JpaRepository<Reservation, String> {
}