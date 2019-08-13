package com.example.demo.controller

import com.example.demo.exception.ResourceNotFoundException
import com.example.demo.model.JwtAuthenticationResponse
import com.example.demo.model.User
import com.example.demo.repository.UserRepository
import com.example.demo.security.JwtTokenProvider
import com.example.demo.security.UserPrincipal
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import org.apache.commons.codec.binary.Hex
import org.web3j.crypto.Keys
import org.web3j.crypto.Hash
import org.web3j.crypto.Sign
import org.web3j.crypto.Sign.SignatureData
import org.web3j.utils.Numeric
import java.util.*
import com.example.demo.repository.findOne

@RestController
@RequestMapping("/api")
class UserController @Autowired constructor(
        val userRepository: UserRepository,
        val authenticationManager: AuthenticationManager,
        val jwtTokenProvider: JwtTokenProvider
) {
    @PostMapping("/user")
    fun createOrLoginUser(@RequestBody requestBody: String): ResponseEntity<JwtAuthenticationResponse> {
        val signUpRequest = Gson().fromJson<JsonObject>(requestBody)
        if (!verifyAddressFromSignature(signUpRequest["publicAddress"].asString, signUpRequest["signature"].asString)) {
            return ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
        val user = userRepository.findByPublicAddress(signUpRequest["publicAddress"].asString)
                ?: userRepository.insert(User(
                        id = UUID.randomUUID().toString(),
                        publicAddress = signUpRequest["publicAddress"].asString,
                        createdAt = Date(),
                        updatedAt = Date()
                ))

        val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                        signUpRequest["publicAddress"].asString, ""
                )
        )

        SecurityContextHolder.getContext().authentication = authentication
        val jwtResponse = JwtAuthenticationResponse(jwtTokenProvider.generateToken(authentication))
        jwtResponse.userId = user.id
        jwtResponse.publicAddress = user.publicAddress
        jwtResponse.userName = user.name
        return ResponseEntity.ok(jwtResponse)
    }

    @GetMapping("/userDetails")
    fun getDetails(): ResponseEntity<User> {
        val userPrincipal = getCurrentUser()
        val user = userRepository.findOne(userPrincipal.id)
                ?: throw ResourceNotFoundException("User ${userPrincipal.id} not found.", "id", userPrincipal.id)
        return ResponseEntity(user, HttpStatus.OK)
    }

    private fun verifyAddressFromSignature(address: String, signature: String): Boolean {
        val messageHashed = Hash.sha3(Hex.encodeHexString("hello world".toByteArray()))
        val messageHashBytes = Numeric.hexStringToByteArray(messageHashed)
        val signPrefix = ("\u0019Ethereum Signed Message:\n32").toByteArray()
        val r = signature.substring(0, 66)
        val s = signature.substring(66, 130)
        val v = "0x" + signature.substring(130, 132)

        val msgBytes = ByteArray(signPrefix.size + messageHashBytes.size)
        val prefixBytes = signPrefix

        System.arraycopy(prefixBytes, 0, msgBytes, 0, prefixBytes.size)
        System.arraycopy(messageHashBytes, 0, msgBytes, prefixBytes.size, messageHashBytes.size)

        val pubkey = Sign.signedMessageToKey(msgBytes,
                SignatureData(Numeric.hexStringToByteArray(v)[0],
                        Numeric.hexStringToByteArray(r),
                        Numeric.hexStringToByteArray(s)))
                .toString(16)

        val recoveredAddress = "0x" + Keys.getAddress(pubkey)
        return address == recoveredAddress
    }

    private fun getCurrentUser(): UserPrincipal {
        return SecurityContextHolder.getContext().authentication.principal as UserPrincipal
    }
}
