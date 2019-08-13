package com.example.demo.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.index.Indexed
import java.util.*

data class User(
        val id: String,
        @Indexed(unique = true) val publicAddress: String,
        var name: String = "",
        var email: String = "",
        @CreatedDate val createdAt: Date,
        @LastModifiedDate var updatedAt: Date
)
