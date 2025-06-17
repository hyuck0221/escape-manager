package com.hshim.escapemanager.service.account.admin

import com.hshim.escapemanager.database.account.admin.repository.AdminRepository
import com.hshim.escapemanager.exception.GlobalException
import com.hshim.escapemanager.model.account.admin.AdminResponse
import com.hshim.escapemanager.model.center.CenterResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AdminQueryService(private val adminRepository: AdminRepository) {
    fun findById(id: String): AdminResponse {
        return adminRepository.findByIdOrNull(id)?.let { AdminResponse(it) }
            ?: throw GlobalException.NOT_FOUND_ADMIN.exception
    }

    fun findAllPageBy(centerId: String, search: String?, pageable: Pageable): Page<AdminResponse> {
        return when (search == null) {
            true -> adminRepository.findAllByCenterId(centerId, pageable)
            false -> adminRepository.findAllByCenterIdAndSearch(centerId, search, pageable)
        }.map { AdminResponse(it) }
    }
}