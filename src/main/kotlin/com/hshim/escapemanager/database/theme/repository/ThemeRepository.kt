package com.hshim.escapemanager.database.theme.repository

import com.hshim.escapemanager.database.theme.Theme
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ThemeRepository : JpaRepository<Theme, String> {
    fun findAllByCenterId(centerId: String, pageable: Pageable): Page<Theme>

    @Query(
        """
            select t from Theme t 
            where t.center.id = :centerId 
            and t.name like concat('%', :search, '%') 
            and t.description like concat('%', :search, '%') 
        """
    )
    fun findAllByCenterIdAndSearch(centerId: String, search: String, pageable: Pageable): Page<Theme>
}