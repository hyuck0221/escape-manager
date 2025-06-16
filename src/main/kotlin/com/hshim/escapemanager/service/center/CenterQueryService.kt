package com.hshim.escapemanager.service.center

import com.hshim.escapemanager.database.center.repository.CenterRepository
import com.hshim.escapemanager.exception.GlobalException
import com.hshim.escapemanager.model.center.CenterResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CenterQueryService(private val centerRepository: CenterRepository) {
    fun findById(id: String): CenterResponse {
        return centerRepository.findByIdOrNull(id)?.let { CenterResponse(it) }
            ?: throw GlobalException.NOT_FOUND_CENTER.exception
    }

    fun findAllPageBy(search: String?, pageable: Pageable): Page<CenterResponse> {
        return when (search == null) {
            true -> centerRepository.findAllByIsDeletedFalse(pageable)
            false -> centerRepository.findAllBySearch(search, pageable)
        }.map { CenterResponse(it) }
    }
}