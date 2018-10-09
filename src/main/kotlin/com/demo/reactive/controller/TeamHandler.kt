package com.demo.reactive.controller

import com.demo.reactive.model.Team
import com.demo.reactive.service.APIService
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.toMono
import java.net.URI


@Component
class TeamHandler(val reactiveMongoTemplate: ReactiveMongoTemplate, val apiService: APIService) {
    fun save(request: ServerRequest) =
            request
                    .bodyToMono(Team::class.java)
                    .map { team ->
                        apiService.getPlayers()  //get players from another service
                                .filter{it-> it.id <= 3}  //Fitler and fetch only Chelsea FC Players
                                .collectList() //collect all elements into list
                                .zipWith(team.toMono()) // combine the players list into team, for furthure processing
                    }
                    .flatMap { it -> it }
                    .map { it ->
                        Team.mergeGroupToMission(it.t1, it.t2) //merging the players to their team, now  Hazard, Kante and Jorginho are Chelsea FC players
                    }
                    .flatMap { reactiveMongoTemplate.save(it) } // save date into database
                    .flatMap { it -> ServerResponse.created(URI.create("/team/" + it.id)).build() }
}