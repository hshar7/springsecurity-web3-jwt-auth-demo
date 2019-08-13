package com.example.demo.repository

import com.example.demo.model.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.CrudRepository

interface UserRepository : MongoRepository<User, String> {
    fun findByPublicAddress(publicAddress: String): User?
}

fun <T, String> CrudRepository<T, String>.findOne(id: String): T? = findById(id).orElse(null)
