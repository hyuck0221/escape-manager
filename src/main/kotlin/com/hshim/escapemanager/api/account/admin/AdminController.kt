package com.hshim.escapemanager.api.account.admin

import com.hshim.escapemanager.annotation.role.Role
import com.hshim.escapemanager.common.token.context.ContextUtil.getAccountId
import com.hshim.escapemanager.database.account.enums.Role.ADMIN
import com.hshim.escapemanager.database.account.enums.Role.SUPER_ADMIN
import com.hshim.escapemanager.model.account.admin.AdminRequest
import com.hshim.escapemanager.model.account.admin.AdminResponse
import com.hshim.escapemanager.service.account.admin.AdminCommandService
import com.hshim.escapemanager.service.account.admin.AdminQueryService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/account/admin")
class AdminController(
    private val adminQueryService: AdminQueryService,
    private val adminCommandService: AdminCommandService,
) {
    @Role([SUPER_ADMIN])
    @GetMapping("/{id}")
    fun findById(@PathVariable id: String): AdminResponse {
        return adminQueryService.findById(id)
    }

    @Role([ADMIN])
    @GetMapping
    fun findById(): AdminResponse {
        return adminQueryService.findById(getAccountId())
    }

    @Role([SUPER_ADMIN])
    @GetMapping("/list")
    fun findAllPageBy(
        @RequestParam(required = true) centerId: String,
        @RequestParam(required = false) search: String?,
        pageable: Pageable,
    ): Page<AdminResponse> {
        return adminQueryService.findAllPageBy(centerId, search, pageable)
    }

    @Role([SUPER_ADMIN])
    @PostMapping
    fun init(
        @RequestParam(required = true) centerId: String,
        @RequestBody request: AdminRequest,
    ): AdminResponse {
        return adminCommandService.init(centerId, request)
    }

    @Role([SUPER_ADMIN])
    @PutMapping("/{id}")
    fun update(
        @PathVariable id: String,
        @RequestBody request: AdminRequest,
    ): AdminResponse {
        return adminCommandService.update(id, request)
    }

    @Role([SUPER_ADMIN])
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String) = adminCommandService.delete(id)
}