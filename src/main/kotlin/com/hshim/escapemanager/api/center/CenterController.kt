package com.hshim.escapemanager.api.center

import com.hshim.escapemanager.annotation.PublicEndpoint
import com.hshim.escapemanager.model.center.CenterRequest
import com.hshim.escapemanager.model.center.CenterResponse
import com.hshim.escapemanager.service.center.CenterCommandService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/center")
class CenterController(private val centerCommandService: CenterCommandService) {

    @PublicEndpoint
    @PostMapping
    fun init(@RequestBody request: CenterRequest): CenterResponse {
        return centerCommandService.init(request)
    }
}