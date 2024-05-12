package io.zkz.fantasyfootball.printing

import io.zkz.fantasyfootball.sleeper.models.DraftPick

data class LabelData(
    val title: String,
    val leftInfo: String?,
    val rightInfo: String?,
    val type: LabelType,
) {
    companion object {
        fun fromDraftPick(pick: DraftPick): LabelData {
            return LabelData(
                pick.metadata.last_name,
                pick.metadata.first_name,
                "${pick.metadata.position} - ${pick.metadata.team}",
                when (pick.metadata.position) {
                    "QB" -> LabelType.ALL_BLACK
                    else -> LabelType.THICK_BORDER
                }
            )
        }
    }
}
