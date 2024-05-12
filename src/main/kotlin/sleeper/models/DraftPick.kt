@file:Suppress("PropertyName")

package io.zkz.fantasyfootball.sleeper.models

import kotlinx.serialization.Serializable

@Serializable
data class DraftPick(
    val player_id: String,
    val picked_by: String,
    val roster_id: String,
    val round: Int,
    val draft_slot: Int,
    val pick_no: Int,
    val metadata: DraftPickMetadata,
    val draft_id: String,
    val is_keeper: Boolean?,
)

@Serializable
data class DraftPickMetadata(
    val team: String,
    val status: String,
    val sport: String,
    val position: String,
    val player_id: String,
    val number: String,
    val news_updated: String,
    val last_name: String,
    val injury_status: String,
    val first_name: String,
    val years_exp: Int,
)