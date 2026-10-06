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

    @Query("SELECT * FROM users ORDER BY id DESC LIMIT 1")
    suspend fun getLatestUser(): UserEntity?

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    suspend fun getUserById(id: Int): UserEntity?

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