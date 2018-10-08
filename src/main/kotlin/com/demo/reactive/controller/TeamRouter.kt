package com.demo.reactive.controller

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.router

@Configuration
class TeamRouter(private val teamHandler: TeamHandler) {


    @Bean
    fun router() = router {
        accept(APPLICATION_JSON).nest {
            "/api/v1".nest {
                "/teams".nest {
                    POST("",  teamHandler::save)
                }
            }
        }
    }
}