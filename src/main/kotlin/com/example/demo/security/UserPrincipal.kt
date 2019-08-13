package com.example.demo.security

import com.example.demo.model.User
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.ArrayList
import java.util.Objects

class UserPrincipal private constructor(
        val id: String,
        val name: String,
        @field:JsonIgnore
        val email: String,
        private val username: String,
        @field:JsonIgnore
        private val password: String,
        private val authorities: Collection<GrantedAuthority>) : UserDetails {

    override fun getUsername(): String {
        return username
    }

    override fun getPassword(): String {
        return password
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return authorities
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as UserPrincipal?
        return id == that!!.id
    }

    override fun hashCode(): Int {

        return Objects.hash(id)
    }

    companion object {

        fun create(user: User): UserPrincipal {
            return UserPrincipal(
                    user.id,
                    user.name,
                    user.email,
                    user.publicAddress,
                    "",
                    ArrayList()
            )
        }
    }
}
