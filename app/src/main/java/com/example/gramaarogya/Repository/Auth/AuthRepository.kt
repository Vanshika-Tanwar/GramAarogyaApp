package com.example.gramaarogya.Repository.Auth

import com.example.gramaarogya.Database.User.UserDao
import com.example.gramaarogya.Database.User.UserEntity
import com.example.gramaarogya.Widgets.PasswordHasher

class AuthRepository(
    private val userDao: UserDao
) {

    suspend fun registerUser(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): Boolean {

        val existingUser = userDao.getUserByEmail(email)

        if (existingUser != null) {
            return false
        }

        val hashedPassword = PasswordHasher.hash(password)

        val user = UserEntity(
            firstName = firstName,
            lastName = lastName,
            email = email,
            passwordHash = hashedPassword
        )

        userDao.insertUser(user)

        return true
    }

    suspend fun login(
        email: String,
        password: String
    ): UserEntity? {

        val hashedPassword = PasswordHasher.hash(password)

        return userDao.login(
            email = email,
            passwordHash = hashedPassword
        )
    }
}