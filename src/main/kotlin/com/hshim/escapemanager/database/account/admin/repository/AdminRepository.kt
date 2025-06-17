package com.hshim.escapemanager.database.account.admin.repository

import com.hshim.escapemanager.database.account.admin.Admin
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AdminRepository : JpaRepository<Admin, String> {
    fun findAllByCenterId(centerId: String, pageable: Pageable): Page<Admin>

    @Query(
        """
            select a from Admin a 
            where a.center.id = :centerId
            and a.name like concat('%', :search, '%') 
            and a.description like concat('%', :search, '%') 
        """
    )
    fun findAllByCenterIdAndSearch(centerId: String, search: String, pageable: Pageable): Page<Admin>
}