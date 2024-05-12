package io.zkz.fantasyfootball.sleeper

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import io.zkz.fantasyfootball.sleeper.models.Draft
import io.zkz.fantasyfootball.sleeper.models.DraftPick
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

class SleeperApi {
    @OptIn(ExperimentalSerializationApi::class)
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                explicitNulls = false
                isLenient = true
            })
        }
    }

    suspend fun getDraft(draftId: String): Draft {
        return client.get("https://api.sleeper.app/v1/draft/$draftId").body()
    }

    suspend fun getDraftPicks(draftId: String): List<DraftPick> {
        return client.get("https://api.sleeper.app/v1/draft/$draftId/picks").body()
    }
}