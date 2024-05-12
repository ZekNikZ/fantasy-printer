@file:Suppress("PropertyName")

package io.zkz.fantasyfootball.sleeper.models

import kotlinx.serialization.Serializable

@Serializable
data class Draft(
    val type: String,
    val status: String,
    val start_time: Long,
    val sport: String,
    val settings: DraftSettings,
    val season_type: String,
    val season: String,
    val metadata: DraftMetadata,
    val league_id: String,
    val last_picked: Long,
    val last_message_time: Long,
    val last_message_id: String,
    val draft_order: Map<String, Int>,
    val slot_to_roster_id: Map<String, Int>,
    val draft_id: String,
    val created: Long,
    val creators: List<String>,
)

@Serializable
data class DraftSettings(
    val teams: Int,
    val slots_wr: Int?,
    val slots_te: Int?,
    val slots_rb: Int?,
    val slots_qb: Int?,
    val slots_k: Int?,
    val slots_flex: Int?,
    val slots_def: Int?,
    val slots_bn: Int?,
    val slots_idp_flex: Int?,
    val rounds: Int,
    val reversal_round: Int,
    val player_type: Int,
    val pick_timer: Int,
    val nomination_timer: Int,
    val enforce_position_limits: Int,
    val cpu_autopick: Int,
    val autostart: Int,
    val autopause_start_time: Int,
    val autopause_end_time: Int,
    val autopause_enabled: Int,
    val alpha_sort: Int,
)

@Serializable
data class DraftMetadata(
    val scoring_type: String,
    val name: String,
    val description: String,
)