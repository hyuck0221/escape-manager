package com.hshim.escapemanager.database.account.admin.repository

import com.hshim.escapemanager.database.account.admin.Admin
import org.springframework.data.jpa.repository.JpaRepository

interface AdminRepository : JpaRepository<Admin, String> {
}