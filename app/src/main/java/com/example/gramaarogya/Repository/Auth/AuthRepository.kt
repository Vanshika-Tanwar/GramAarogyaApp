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
    ): UserEntity? {

        val existingUser = userDao.getUserByEmail(email)

        if (existingUser != null) {
            return null
        }

        val hashedPassword = PasswordHasher.hash(password)

        val user = UserEntity(
            firstName = firstName,
            lastName = lastName,
            email = email,
            passwordHash = hashedPassword
        )

        userDao.insertUser(user)

        val registeredUser = userDao.getUserByEmail(email) ?: user
        SessionManager.login(registeredUser)

        return registeredUser
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