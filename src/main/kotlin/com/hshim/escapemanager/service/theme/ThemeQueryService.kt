package com.hshim.escapemanager.service.theme

import com.hshim.escapemanager.database.theme.repository.ThemeRepository
import com.hshim.escapemanager.exception.GlobalException
import com.hshim.escapemanager.model.theme.ThemeResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ThemeQueryService(private val themeRepository: ThemeRepository) {
    fun findById(id: String): ThemeResponse {
        return themeRepository.findByIdOrNull(id)?.let { ThemeResponse(it) }
            ?: throw GlobalException.NOT_FOUND_THEME.exception
    }

    fun findAllPageBy(centerId: String, search: String?, pageable: Pageable): Page<ThemeResponse> {
        return when (search == null) {
            true -> themeRepository.findAllByCenterId(centerId, pageable)
            false -> themeRepository.findAllByCenterIdAndSearch(centerId, search, pageable)
        }.map { ThemeResponse(it) }
    }
}