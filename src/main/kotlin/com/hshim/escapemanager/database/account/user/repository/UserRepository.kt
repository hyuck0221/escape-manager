package com.hshim.escapemanager.database.account.user.repository

import com.hshim.escapemanager.database.account.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, String> {
}