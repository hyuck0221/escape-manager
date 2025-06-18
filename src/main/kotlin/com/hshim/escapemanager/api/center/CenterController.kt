package com.hshim.escapemanager.api.center

import com.hshim.escapemanager.annotation.role.Public
import com.hshim.escapemanager.annotation.role.Role
import com.hshim.escapemanager.common.token.context.ContextUtil.getCenterId
import com.hshim.escapemanager.database.account.enums.Role.ADMIN
import com.hshim.escapemanager.database.account.enums.Role.SUPER_ADMIN
import com.hshim.escapemanager.model.center.CenterRequest
import com.hshim.escapemanager.model.center.CenterResponse
import com.hshim.escapemanager.service.center.CenterCommandService
import com.hshim.escapemanager.service.center.CenterQueryService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/center")
class CenterController(
    private val centerQueryService: CenterQueryService,
    private val centerCommandService: CenterCommandService,
) {
    @Public
    @GetMapping("/{id}")
    fun findById(@PathVariable id: String): CenterResponse {
        return centerQueryService.findById(id)
    }

    @Public
    @GetMapping("/list")
    fun findAllPageBy(
        @RequestParam(required = true) search: String?,
        pageable: Pageable,
    ): Page<CenterResponse> {
        return centerQueryService.findAllPageBy(search, pageable)
    }

    @Role([SUPER_ADMIN])
    @PostMapping
    fun init(@RequestBody request: CenterRequest): CenterResponse {
        return centerCommandService.init(request)
    }

    @Role([SUPER_ADMIN])
    @PutMapping("/{id}")
    fun update(
        @PathVariable id: String,
        @RequestBody request: CenterRequest,
    ): CenterResponse {
        return centerCommandService.update(id, request)
    }

    @Role([ADMIN])
    @PutMapping
    fun update(@RequestBody request: CenterRequest): CenterResponse {
        return centerCommandService.update(getCenterId(), request)
    }

    @Role([SUPER_ADMIN])
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String) = centerCommandService.delete(id)
}