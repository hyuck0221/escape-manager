package com.hshim.escapemanager.database.center.repository

import com.hshim.escapemanager.database.center.Center
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CenterRepository : JpaRepository<Center, String> {
    @Query(
        """
            select c from Center c 
            where c.isDeleted = false 
            and c.name like concat('%', :search, '%') 
            and c.description like concat('%', :search, '%') 
            and c.address like concat('%', :search, '%') 
            and c.phoneNo like concat('%', :search, '%') 
            and c.email like concat('%', :search, '%') 
        """
    )
    fun findAllBySearch(search: String, pageable: Pageable): Page<Center>

    fun findAllByIsDeletedFalse(pageable: Pageable): Page<Center>
}