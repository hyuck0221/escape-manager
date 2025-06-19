package com.hshim.escapemanager.service.account.admin

import com.hshim.escapemanager.database.account.admin.repository.AdminRepository
import com.hshim.escapemanager.model.account.admin.AdminRequest
import com.hshim.escapemanager.model.account.admin.AdminResponse
import com.hshim.escapemanager.service.account.AccountQueryService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AdminCommandService(
    private val adminRepository: AdminRepository,
    private val accountQueryService: AccountQueryService,
) {
    fun init(centerId: String, request: AdminRequest): AdminResponse {
        accountQueryService.validateLoginId(request.loginId)
        return AdminResponse(adminRepository.save(request.toEntity(centerId)))
    }

    fun delete(id: String) {
        adminRepository.deleteById(id)
    }
}