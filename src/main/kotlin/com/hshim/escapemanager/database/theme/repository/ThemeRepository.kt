package com.hshim.escapemanager.database.theme.repository

import com.hshim.escapemanager.database.theme.Theme
import org.springframework.data.jpa.repository.JpaRepository

interface ThemeRepository : JpaRepository<Theme, String> {
}