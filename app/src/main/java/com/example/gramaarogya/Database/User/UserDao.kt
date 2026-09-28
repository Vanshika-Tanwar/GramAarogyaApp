package com.example.gramaarogya.Database.User

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Query("""
        SELECT * FROM users 
        WHERE email = :email 
        AND passwordHash = :passwordHash
        LIMIT 1
    """)
    suspend fun login(
        email: String,
        passwordHash: String
    ): UserEntity?
}