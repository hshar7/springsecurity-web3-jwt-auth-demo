package com.example.demo.model

data class JwtAuthenticationResponse (
        var accessToken: String,
        var tokenType: String = "Bearer",
        var userName: String = "",
        var publicAddress: String = "",
        var userAvatar: String = "",
        var userId: String = ""
)
