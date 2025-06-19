package com.hshim.escapemanager.database.theme.reservation.repository

import com.hshim.escapemanager.database.theme.reservation.Reservation
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime
import java.time.YearMonth

interface ReservationRepository : JpaRepository<Reservation, String> {
    fun findByThemeIdAndId(themeId: String, id: String): Reservation?
    fun findByThemeIdAndCode(themeId: String, code: String): Reservation?

    @Query(
        """
            select r from Reservation r 
            inner join fetch r.theme t
            where t.id = :themeId 
            and r.name like concat('%', :search, '%') 
            and year(r.datetime) = year(:yearMonth) and month(r.datetime) = month(:yearMonth) 
            and r.code like concat('%', :search, '%') 
            and r.phoneNo like concat('%', :search, '%') 
        """
    )
    fun findAllByThemeIdAndDateAndSearch(
        themeId: String,
        yearMonth: YearMonth,
        search: String,
        pageable: Pageable
    ): Page<Reservation>

    @Query(
        """
            select r from Reservation r 
            where r.theme.id = :themeId 
            and year(r.datetime) = year(:yearMonth) and month(r.datetime) = month(:yearMonth)
        """
    )
    fun findAllByThemeIdAndYearMonth(themeId: String, yearMonth: YearMonth, pageable: Pageable): Page<Reservation>

    fun deleteByThemeIdAndId(themeId: String, id: String)
    fun findAllByThemeIdAndDatetime(themeId: String, datetime: LocalDateTime): List<Reservation>
}