package com.demo.reactive.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "teams")
data class Team(@Id val id: String? =null, val name: String, val players: List<Player>? = null){

    companion object {
        /**
         * add the group given to this object mission
         */
        fun mergeGroupToMission(players: List<Player>, team: Team) = Team(
                name = team.name,
                players = players
        )
    }
}