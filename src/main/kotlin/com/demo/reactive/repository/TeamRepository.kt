package com.demo.reactive.repository

import com.demo.reactive.model.Team
import org.springframework.data.mongodb.repository.MongoRepository

interface TeamRepository : MongoRepository<Team, String>
