package io.zkz.fantasyfootball

import io.zkz.fantasyfootball.printing.LabelData
import io.zkz.fantasyfootball.printing.PrintingService
import io.zkz.fantasyfootball.sleeper.SleeperService
import io.zkz.fantasyfootball.ui.UIService
import javax.swing.JOptionPane

object ManagerService : IService {
    private val trackedDraftPicks: MutableSet<Pair<Int, Int>> = mutableSetOf()

    fun onSleeperUpdates(confirmPrint: Boolean = false) {
        val newPicks = SleeperService.draftPicks.keys.minus(trackedDraftPicks)
        if (PrintingService.enabled && (!confirmPrint || JOptionPane.showConfirmDialog(null, "The selected draft already has some picks. Would you like to print the labels for those prints?", "Confirm Print", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)) {
            newPicks.forEach {
                PrintingService.print(LabelData.fromDraftPick(SleeperService.draftPicks[it]!!))
            }
        }
        trackedDraftPicks += newPicks
        UIService.rebuildDraftPane()
    }
}