package com.example.demo.security

import com.example.demo.exception.ResourceNotFoundException
import com.example.demo.repository.UserRepository
import com.example.demo.repository.findOne
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CustomUserDetailsService @Autowired constructor(
        val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByPublicAddress(username)
                ?: throw ResourceNotFoundException("User", "publicAddress", username)
        return UserPrincipal.create(user)
    }

    @Transactional
    fun loadUserById(id: String): UserDetails {
        val user = userRepository.findOne(id) ?: throw UsernameNotFoundException("User not found with id $id")
        return UserPrincipal.create(user)
    }
}
