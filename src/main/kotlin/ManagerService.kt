package io.zkz.fantasyfootball

import io.zkz.fantasyfootball.printing.LabelData
import io.zkz.fantasyfootball.printing.PrintingService
import io.zkz.fantasyfootball.sleeper.SleeperService
import io.zkz.fantasyfootball.ui.UIService

object ManagerService : IService {
    private val trackedDraftPicks: MutableSet<Pair<Int, Int>> = mutableSetOf()

    fun onSleeperUpdates(print: Boolean = true) {
        val newPicks = SleeperService.draftPicks.keys.minus(trackedDraftPicks)
        if (print) {
            newPicks.forEach {
                PrintingService.print(LabelData.fromDraftPick(SleeperService.draftPicks[it]!!))
            }
        }
        trackedDraftPicks += newPicks
        UIService.rebuildDraftPane()
    }
}