package com.hshim.escapemanager.database.reservation.repository

import com.hshim.escapemanager.database.reservation.Reservation
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

interface ReservationRepository : JpaRepository<Reservation, String> {
    @Query(
        """
            select r from Reservation r 
            inner join fetch r.theme t
            where t.id = :themeId 
            and r.name like concat('%', :search, '%') 
            and date(r.datetime) = :date  
            and r.code like concat('%', :search, '%') 
            and r.phoneNo like concat('%', :search, '%') 
        """
    )
    fun findAllByThemeIdAndDateAndSearch(
        themeId: String,
        date: LocalDate,
        search: String,
        pageable: Pageable
    ): Page<Reservation>

    @Query("select r from Reservation r where r.theme.id = :themeId and date(r.datetime) = :date")
    fun findAllByThemeIdAndDate(themeId: String, date: LocalDate, pageable: Pageable): Page<Reservation>
}