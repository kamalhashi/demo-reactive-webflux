package com.demo.reactive.service

import com.demo.reactive.model.Player
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux

@Service
class APIService {
    fun getPlayers(): Flux<Player> = get("/players").bodyToFlux(Player::class.java)

    fun get(path: String): WebClient.ResponseSpec {
        val client = WebClient.create("http://my-json-server.typicode.com/kweheliye/players/")
        return client.get().uri(path).retrieve()
    }
}