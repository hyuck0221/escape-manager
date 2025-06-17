package com.hshim.escapemanager.database.account.admin.repository

import com.hshim.escapemanager.database.account.admin.SuperAdmin
import org.springframework.data.jpa.repository.JpaRepository

interface SuperAdminRepository : JpaRepository<SuperAdmin, String> {
}