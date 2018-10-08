package com.demo.reactive

import com.demo.reactive.model.Team
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.BodyInserters


@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReactiveTeamDemoAppTests {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun `Create a new team and add players to it , should result in 201 status `() {

        val team: Team = Team(name = "Chelsea FC")

        val x = webTestClient
                .post()
                .uri("/api/v1/teams")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(team))
                .exchange()
                .expectStatus()
                .isCreated
                .expectBody(Team::class.java)
                .returnResult()

    }

}
