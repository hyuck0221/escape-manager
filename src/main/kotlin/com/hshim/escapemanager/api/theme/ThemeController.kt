package com.hshim.escapemanager.api.theme

import com.hshim.escapemanager.annotation.role.Public
import com.hshim.escapemanager.annotation.role.Role
import com.hshim.escapemanager.common.token.context.ContextUtil.getCenterId
import com.hshim.escapemanager.database.account.enums.Role.ADMIN
import com.hshim.escapemanager.model.theme.ThemeRequest
import com.hshim.escapemanager.model.theme.ThemeResponse
import com.hshim.escapemanager.service.theme.ThemeCommandService
import com.hshim.escapemanager.service.theme.ThemeQueryService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/theme")
class ThemeController(
    private val themeQueryService: ThemeQueryService,
    private val themeCommandService: ThemeCommandService,
) {
    @Public
    @GetMapping("/{id}")
    fun findById(@PathVariable id: String): ThemeResponse {
        return themeQueryService.findById(id)
    }

    @Public
    @GetMapping("/list")
    fun findAllPageBy(
        @RequestParam(required = true) centerId: String,
        @RequestParam(required = false) search: String?,
        pageable: Pageable,
    ): Page<ThemeResponse> {
        return themeQueryService.findAllPageBy(centerId, search, pageable)
    }

    @Role([ADMIN])
    @PostMapping
    fun init(@RequestBody request: ThemeRequest): ThemeResponse {
        return themeCommandService.init(getCenterId(), request)
    }

    @Role([ADMIN])
    @PutMapping("/{id}")
    fun update(
        @PathVariable id: String,
        @RequestBody request: ThemeRequest,
    ): ThemeResponse {
        return themeCommandService.update(id, request)
    }

    @Role([ADMIN])
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String) = themeCommandService.delete(id)
}