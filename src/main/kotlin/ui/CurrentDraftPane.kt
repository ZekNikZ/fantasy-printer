package io.zkz.fantasyfootball.ui

import io.zkz.fantasyfootball.printing.LabelData
import io.zkz.fantasyfootball.printing.PrintingService
import io.zkz.fantasyfootball.sleeper.SleeperService
import java.awt.Dimension
import java.awt.GridLayout
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.SwingConstants

object CurrentDraftPane : JScrollPane() {
    private val panel: JPanel = JPanel().apply {
        addLabel("No draft connected yet")
    }

    init {
        setViewportView(panel)

        border = null
    }

    fun rebuild() {
        val draft = SleeperService.draft ?: return
        val draftPicks = SleeperService.draftPicks

        val rounds = draft.settings.rounds
        val teams = draft.settings.teams
        val isSnake = draft.type == "snake"

        panel.apply {
            removeAll()

            add(JPanel(GridLayout(rounds + 1, teams)).apply {
                for (i in 0..<teams) {
                    addLabel("Team ${i + 1}", textAlign = SwingConstants.CENTER)
                }

                for (round in 1..rounds) {
                    for (team in 1..teams) {
                        val pick = if (isSnake && round % 2 == 0) teams - team + 1 else team
                        val draftPick = draftPicks[Pair(round, team)]

                        addButton("${round}.${pick}", setup = {
                            preferredSize = Dimension(100, 40)
                            if (draftPick != null) {
                                text =
                                    "<html>${draftPick.metadata.first_name.first()}. ${draftPick.metadata.last_name}<br />${draftPick.metadata.position} - ${draftPick.metadata.team}</html>"
                            } else {
                                isEnabled = false
                            }
                        }) {
                            if (draftPick != null) {
                                PrintingService.print(LabelData.fromDraftPick(draftPick))
                            }
                        }
                    }
                }
            })

            repaint()
            revalidate()
        }
    }
}