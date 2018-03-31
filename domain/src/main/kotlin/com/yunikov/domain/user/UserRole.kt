package com.yunikov.domain.user

enum class UserRole constructor(val roleName: String) {
    NEW_USER(Name.NEW_USER),
    USER(Name.USER);

    interface Name {
        companion object {
            val NEW_USER = "ROLE_NEW_USER"
            val USER = "ROLE_USER"
        }
    }
}