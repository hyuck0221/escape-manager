package com.hshim.escapemanager.service.center

import com.hshim.escapemanager.database.center.repository.CenterRepository
import com.hshim.escapemanager.exception.GlobalException
import com.hshim.escapemanager.model.center.CenterRequest
import com.hshim.escapemanager.model.center.CenterResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CenterCommandService(private val centerRepository: CenterRepository) {
    fun init(request: CenterRequest): CenterResponse {
        return CenterResponse(centerRepository.save(request.toEntity()))
    }

    fun update(id: String, request: CenterRequest): CenterResponse {
        return centerRepository.findByIdOrNull(id)
            ?.apply { request.updateTo(this) }
            ?.let { CenterResponse(it) }
            ?: throw GlobalException.NOT_FOUND_CENTER.exception
    }

    fun delete(id: String) {
        centerRepository.findByIdOrNull(id)
            ?.apply { isDeleted = true }
    }
}