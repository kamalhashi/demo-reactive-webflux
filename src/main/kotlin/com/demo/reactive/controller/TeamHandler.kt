package com.demo.reactive.controller

import com.demo.reactive.model.Team
import com.demo.reactive.repository.TeamRepository
import com.demo.reactive.service.APIService
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import java.net.URI


@Component
class TeamHandler(val reactiveMongoTemplate: ReactiveMongoTemplate, val apiService: APIService) {
    fun save(request: ServerRequest) =
            request
                    .bodyToMono(Team::class.java)
                    .map { team ->
                        apiService.getPlayers()
                                .collectList()
                                .zipWith(team.toMono())
                    }
                    .flatMap { it -> it }
                    .map { it ->
                        Team.mergeGroupToMission(it.t1, it.t2)
                    }
                    .flatMap { reactiveMongoTemplate.save(it) }
                    .flatMap { it -> ServerResponse.created(URI.create("/team/" + it.id)).build() }


}