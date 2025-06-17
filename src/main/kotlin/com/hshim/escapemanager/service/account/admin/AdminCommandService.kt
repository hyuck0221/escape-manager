package com.hshim.escapemanager.service.account.admin

import com.hshim.escapemanager.database.account.admin.repository.AdminRepository
import com.hshim.escapemanager.exception.GlobalException
import com.hshim.escapemanager.model.account.admin.AdminRequest
import com.hshim.escapemanager.model.account.admin.AdminResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AdminCommandService(private val adminRepository: AdminRepository) {
    fun init(centerId: String, request: AdminRequest): AdminResponse {
        return AdminResponse(adminRepository.save(request.toEntity(centerId)))
    }

    fun update(id: String, request: AdminRequest): AdminResponse {
        return adminRepository.findByIdOrNull(id)
            ?.apply { request.updateTo(this) }
            ?.let { AdminResponse(it) }
            ?: throw GlobalException.NOT_FOUND_ADMIN.exception
    }

    fun delete(id: String) {
        adminRepository.deleteById(id)
    }
}