package com.konradpekala.blefik.data.model

data class User(var nick: String = "",
                var id: String = "",
                var email: String = "",
                var password: String = "",
                var gamesWon: Int = 0,
                var imageUrl: String = "")