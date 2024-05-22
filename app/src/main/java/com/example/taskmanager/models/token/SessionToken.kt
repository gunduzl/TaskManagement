package com.example.taskmanager.models.token


class SessionToken {
    companion object{
        var currentlyLoggedUser: User? = null
            private set

        fun initToken(user: User) {
            currentlyLoggedUser = user
        }
    }
}