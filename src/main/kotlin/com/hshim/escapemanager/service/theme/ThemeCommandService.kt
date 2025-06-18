package com.hshim.escapemanager.service.theme

import com.hshim.escapemanager.database.theme.repository.ThemeRepository
import com.hshim.escapemanager.exception.GlobalException
import com.hshim.escapemanager.model.theme.ThemeRequest
import com.hshim.escapemanager.model.theme.ThemeResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ThemeCommandService(private val themeRepository: ThemeRepository) {
    fun init(centerId: String, request: ThemeRequest): ThemeResponse {
        return ThemeResponse(themeRepository.save(request.toEntity(centerId)))
    }

    fun update(id: String, request: ThemeRequest): ThemeResponse {
        return themeRepository.findByIdOrNull(id)
            ?.apply { request.updateTo(this) }
            ?.let { ThemeResponse(it) }
            ?: throw GlobalException.NOT_FOUND_THEME.exception
    }

    fun delete(id: String) {
        themeRepository.deleteById(id)
    }
}