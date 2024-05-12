package io.zkz.fantasyfootball.printing

enum class LabelSize(
    val label: String,
    val width: Float,
    val height: Float,
) {
    FOUR_BY_ONE("4 x 1 in", 4f, 1f),
    FOUR_BY_ONE_AND_A_HALF("4 x 1.5 in", 4f, 1.5f),
}