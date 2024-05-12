package io.zkz.fantasyfootball

import io.zkz.fantasyfootball.printing.PrintingService
import io.zkz.fantasyfootball.sleeper.SleeperService
import io.zkz.fantasyfootball.ui.UIService

fun main() {
    listOf(
        ManagerService,
        PrintingService,
        SleeperService,
        UIService,
    ).forEach(IService::initialize)

    UIService.thread.join()

    listOf(
        ManagerService,
        PrintingService,
        SleeperService,
        UIService,
    ).forEach(IService::dispose)
}