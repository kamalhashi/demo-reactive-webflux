package com.demo.reactive

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@SpringBootApplication
@EnableReactiveMongoRepositories
class ReactiveTeamDemoApp

fun main(args: Array<String>) {
    runApplication<ReactiveTeamDemoApp>(*args)
}